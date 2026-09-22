package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentActItem;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetAssignmentUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetHistoryRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetStatusRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetValueRepositoryPort;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.out.external.EmailNotificationPort;
import com.datacenter.asset.domain.ports.out.external.PdfGeneratorPort;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldDefinitionRepositoryPort;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetLoanRepositoryPort;
import com.datacenter.asset.domain.models.person.Person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetAssignmentUseCaseImpl implements ManageAssetAssignmentUseCase {

    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final AssetHistoryRepositoryPort historyRepository;

    private final PdfGeneratorPort pdfGenerator;
    private final EmailNotificationPort emailNotification;
    private final AssetRepositoryPort assetRepository;
    private final PersonRepositoryPort personRepository;
    private final LocationRepositoryPort locationRepository;
    private final CompanyRepositoryPort companyRepository;
    private final AssetStatusRepositoryPort assetStatusRepository;
    private final AssetValueRepositoryPort assetValueRepository;
    private final FieldDefinitionRepositoryPort fieldDefinitionRepository;
    private final AssetLoanRepositoryPort loanRepository;

    private static final String BATCH_PREFIX = "[BATCH:";

    // ============================================================
    // ASIGNAR: N filas (una por activo) con el mismo batchId en notes
    // ============================================================
    @Override
    @Transactional
    public AssetAssignment assignAsset(
            List<UUID> assetIds,
            UUID personId,
            UUID relationshipTypeId,
            String notes,
            UUID createdById) {

        if (assetIds == null || assetIds.isEmpty()) {
            throw new BusinessException("Debe indicar al menos un activo");
        }
        if (personId == null) {
            throw new BusinessException("La persona es obligatoria");
        }
        if (createdById == null) {
            throw new BusinessException("El usuario que crea la asignación es obligatorio");
        }

        // Un solo batchId para todas las filas creadas en esta llamada
        UUID batchId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        String actorStr = createdById.toString();
        List<UUID> uniqueAssetIds = assetIds.stream().distinct().toList();

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new BusinessException("La persona a asignar no existe"));
        if (!person.isActive()) {
            throw new BusinessException("No se puede asignar un activo a un colaborador inactivo");
        }

        AssetAssignment firstSaved = null;
        for (UUID assetId : uniqueAssetIds) {
            if (assignmentRepository.hasActiveAssignment(assetId)) {
                throw new BusinessException("El activo " + assetId + " ya se encuentra asignado.");
            }
            if (loanRepository.hasActiveLoan(assetId)) {
                throw new BusinessException("El activo " + assetId + " se encuentra prestado a otra empresa.");
            }

            AssetAssignment assignment = AssetAssignment.builder()
                    .assetId(assetId)
                    .personId(personId)
                    .relationshipTypeId(relationshipTypeId)
                    .batchId(batchId)          // <- clave: mismo batch para todas
                    .startDate(now)
                    .notes(notes)
                    .isActive(true)
                    .state(AssignmentState.PENDING)
                    .build();

            AssetAssignment saved = assignmentRepository.save(assignment);
            if (firstSaved == null) firstSaved = saved;

            registerHistory(
                    assetId,
                    "ASSIGNMENT_CREATED",
                    String.format("Activo asignado a %s por %s", personId, actorStr),
                    actorStr
            );
        }
        return firstSaved;
    }

    // ============================================================
    // ACEPTAR: acepta todas las filas del mismo batchId
    // ============================================================
    @Override
    @Transactional
    public AssetAssignment acceptAssignment(UUID assignmentId, UUID deliveredById, String observaciones) {
        AssetAssignment reference = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));

        List<AssetAssignment> batch = findBatch(reference);
        if (batch.isEmpty()) batch = List.of(reference);

        String obsStr = (observaciones != null && !observaciones.isEmpty())
                ? ". Observaciones: " + observaciones : "";

        AssetAssignment primary = null;
        for (AssetAssignment a : batch) {
            a.accept(null, observaciones); // Use domain method to validate state
            assignmentRepository.save(a);

            registerHistory(
                    a.getAssetId(),
                    "ASSIGNMENT_ACCEPTED",
                    String.format("Asignación aceptada por %s. Entregado por: %s%s",
                            a.getPersonId(), deliveredById, obsStr),
                    a.getPersonId().toString()
            );

            if (a.getId().equals(assignmentId)) primary = a;
        }
        return primary != null ? primary : reference;
    }

    // ============================================================
    // RECHAZAR: rechaza todas las filas del mismo batchId
    // ============================================================
    @Override
    @Transactional
    public AssetAssignment rejectAssignment(UUID assignmentId) {
        AssetAssignment reference = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));

        List<AssetAssignment> batch = findBatch(reference);
        if (batch.isEmpty()) batch = List.of(reference);

        AssetAssignment primary = null;
        for (AssetAssignment a : batch) {
            a.reject(null); // Use domain method to validate state
            assignmentRepository.save(a);

            registerHistory(
                    a.getAssetId(),
                    "ASSIGNMENT_REJECTED",
                    String.format("Asignación rechazada por %s", a.getPersonId()),
                    a.getPersonId().toString()
            );

            if (a.getId().equals(assignmentId)) primary = a;
        }
        return primary != null ? primary : reference;
    }

    // ============================================================
    // GENERAR ACTA: un solo PDF con todos los activos del batch
    // ============================================================
    @Override
    @Transactional
    public String generarActa(UUID assignmentId, UUID deliveredById, String observaciones, byte[] imagenObservacion) {
        AssetAssignment reference = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        var person = personRepository.findById(reference.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona que recibe no encontrada"));
        var deliverer = personRepository.findById(deliveredById)
                .orElseThrow(() -> new ResourceNotFoundException("Persona que entrega no encontrada"));

        List<AssetAssignment> batch = findBatch(reference);
        if (batch.isEmpty()) batch = List.of(reference);

        String fechaHoraAct = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<AssignmentActItem> items = new ArrayList<>();
        String companyTaxId = "";
        String companyName = "";

        for (AssetAssignment a : batch) {
            var asset = assetRepository.findById(a.getAssetId())
                    .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado: " + a.getAssetId()));
            var location = locationRepository.findById(asset.getLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));
            var company = companyRepository.findById(asset.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
            var status = assetStatusRepository.findById(asset.getAssetStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

            if (companyTaxId.isEmpty()) {
                companyTaxId = company.getTaxId();
                companyName = company.getName();
            }

            List<AssetValue> assetValues = assetValueRepository.findByAssetId(asset.getId().value());
            String marca = "", modelo = "", procesador = "", placa = "", atributo = "", serial = "";
            for (AssetValue val : assetValues) {
                var defOpt = fieldDefinitionRepository.findById(val.getFieldDefinitionId());
                if (defOpt.isPresent()) {
                    String labelName = defOpt.get().getName().toLowerCase();
                    if (labelName.contains("marca")) marca = val.getValue();
                    else if (labelName.contains("modelo")) modelo = val.getValue();
                    else if (labelName.contains("procesador")) procesador = val.getValue();
                    else if (labelName.contains("placa") || labelName.contains("numer placa")) placa = val.getValue();
                    else if (labelName.contains("atributo")) atributo = val.getValue();
                    else if (labelName.contains("serial")) serial = val.getValue();
                }
            }

            items.add(AssignmentActItem.builder()
                    .assetCode(asset.getCode().value())
                    .assetName(asset.getName())
                    .locationCode(location.getCode())
                    .companyTaxId(company.getTaxId())
                    .companyName(company.getName())
                    .assetSerial(serial)
                    .assetMarca(marca)
                    .assetModelo(modelo)
                    .assetProcesador(procesador)
                    .assetEstado(status.getName())
                    .assetPlaca(placa)
                    .assetAtributo(atributo)
                    .build());
        }

        String pdfUrl = pdfGenerator.generateAssignmentAct(
                reference,
                person.getFirstName(),
                person.getLastName(),
                person.getDocumentNumber(),
                person.getEmail(),
                deliverer.getFirstName(),
                deliverer.getLastName(),
                deliverer.getDocumentNumber(),
                deliverer.getEmail(),
                items,
                companyTaxId,
                companyName,
                observaciones,
                fechaHoraAct,
                imagenObservacion
        );

        for (AssetAssignment a : batch) {
            a.setPdfPath(pdfUrl);
            if (observaciones != null && !observaciones.isEmpty()) a.setNotes(observaciones);
            assignmentRepository.save(a);
        }

        emailNotification.sendAssignmentAcceptedNotification(person.getEmail(), reference, pdfUrl);
        return "http://localhost:8080" + pdfUrl;
    }

    // ============================================================
    // TRANSFERIR / DEVOLVER / CONSULTAS
    // ============================================================
    @Override
    @Transactional
    public AssetAssignment transferAsset(UUID assignmentId, UUID deliveredById, UUID newAssigneeId, String transferReason) {
        AssetAssignment currentAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));

        if (!currentAssignment.getState().equals(AssignmentState.ACCEPTED) &&
                !currentAssignment.getState().equals(AssignmentState.REJECTED)) {
            throw new BusinessException("Solo se pueden transferir asignaciones en estado ACCEPTED o REJECTED");
        }

        currentAssignment.setState(AssignmentState.TRANSFERRED);
        assignmentRepository.save(currentAssignment);

        String transferDescription = String.format("Transferencia de %s a %s. Motivo: %s",
                deliveredById, newAssigneeId,
                transferReason != null && !transferReason.isEmpty() ? transferReason : "Sin motivo");

        registerHistory(currentAssignment.getAssetId(), "ASSIGNMENT_TRANSFERRED", transferDescription, deliveredById.toString());

        UUID newBatchId = UUID.randomUUID();
        AssetAssignment newAssignment = AssetAssignment.builder()
                .assetId(currentAssignment.getAssetId())
                .personId(newAssigneeId)
                .batchId(newBatchId)
                .state(AssignmentState.PENDING)
                .startDate(LocalDateTime.now())
                .isActive(true)
                .build();

        AssetAssignment saved = assignmentRepository.save(newAssignment);
        registerHistory(saved.getAssetId(), "ASSIGNMENT_CREATED",
                String.format("Activo transferido a %s desde %s", newAssigneeId, deliveredById),
                deliveredById.toString());
        return saved;
    }

    @Override
    @Transactional
    public AssetAssignment returnAsset(UUID assignmentId, UUID returnedById, String returnReason) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));

        if (!assignment.getState().equals(AssignmentState.ACCEPTED)) {
            throw new BusinessException("Solo se pueden devolver activos en estado ACCEPTED");
        }

        assignment.setState(AssignmentState.RETURNED);
        assignment.setIsActive(false);
        AssetAssignment updated = assignmentRepository.save(assignment);

        String description = String.format("Activo devuelto al almacén por %s. Motivo: %s",
                returnedById, returnReason != null && !returnReason.isEmpty() ? returnReason : "Sin motivo");

        registerHistory(assignment.getAssetId(), "ASSIGNMENT_RETURNED", description, returnedById.toString());
        return updated;
    }

    private void registerHistory(UUID assetId, String eventType, String description, String actor) {
        AssetHistory history = AssetHistory.builder()
                .assetId(assetId)
                .eventDate(LocalDateTime.now())
                .eventType(eventType)
                .executedBy(actor)
                .description(description)
                .build();
        historyRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetAssignment> getAssignmentsByState(AssignmentState state) {
        return assignmentRepository.findByState(state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetHistory> getAssetHistory(UUID assetId) {
        return historyRepository.findByAssetId(assetId);
    }

    // ============================================================
    // HELPER: obtiene todo el lote de una asignación
    // ============================================================
    private List<AssetAssignment> findBatch(AssetAssignment reference) {
        if (reference.getBatchId() == null) {
            return List.of(reference);
        }
        String prefix = BATCH_PREFIX + reference.getBatchId() + "]";
        return assignmentRepository.findByNotesStartingWith(prefix);
    }
}