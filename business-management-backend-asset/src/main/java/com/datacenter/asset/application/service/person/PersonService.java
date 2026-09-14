package com.datacenter.asset.application.service.person;

import com.datacenter.asset.domain.person.Person;
import com.datacenter.asset.domain.ports.company.out.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.person.in.ManagePersonUseCase;
import com.datacenter.asset.domain.ports.person.out.PersonRepositoryPort;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PersonService implements ManagePersonUseCase {
    private final PersonRepositoryPort personRepositoryPort;
    private final CompanyRepositoryPort companyRepositoryPort;

    public PersonService(PersonRepositoryPort personRepositoryPort, CompanyRepositoryPort companyRepositoryPort) {
        this.personRepositoryPort = personRepositoryPort;
        this.companyRepositoryPort = companyRepositoryPort;
    }

    @Override
    public Person createPerson(Person person) {
        if (personRepositoryPort.existsByDocumentNumber(person.getDocumentNumber())) {
            throw new IllegalArgumentException("Ya existe una persona con este documento.");
        }
        if (companyRepositoryPort.findById(person.getCompanyId()).isEmpty()) {
            throw new IllegalArgumentException("La empresa asociada no existe.");
        }

        if (person.getCreatedAt() == null) {
            person.setCreatedAt(LocalDateTime.now());
            person.setActive(true);
        }

        return personRepositoryPort.save(person);
    }

    @Override
    public Person getPersonById(UUID id) {
        return personRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepositoryPort.findAll();
    }

    // --- NUEVO: Editar ---
    @Override
    public Person update(UUID id, Person updatedData) {
        Person existing = personRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        // Validar si cambia el número de documento
        if (!existing.getDocumentNumber().equals(updatedData.getDocumentNumber()) &&
            personRepositoryPort.existsByDocumentNumber(updatedData.getDocumentNumber())) {
            throw new IllegalArgumentException("Ya existe otra persona con este documento.");
        }

        // Validar si cambia la empresa
        if (!existing.getCompanyId().equals(updatedData.getCompanyId())) {
            if (companyRepositoryPort.findById(updatedData.getCompanyId()).isEmpty()) {
                throw new IllegalArgumentException("La empresa asociada no existe.");
            }
        }

        // Actualizamos los datos
        existing.setCompanyId(updatedData.getCompanyId());
        existing.setDocumentNumber(updatedData.getDocumentNumber());
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setDepartment(updatedData.getDepartment());

        return personRepositoryPort.save(existing);
    }

    // --- NUEVO: Activar ---
    @Override
    public Person activate(UUID id) {
        Person person = personRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
        person.setActive(true);
        return personRepositoryPort.save(person);
    }

    // --- NUEVO: Desactivar ---
    @Override
    public Person deactivate(UUID id) {
        Person person = personRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
        person.setActive(false);
        return personRepositoryPort.save(person);
    }

    // --- NUEVO: Eliminar ---
    @Override
    public void delete(UUID id) {
        Person existing = personRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
                System.out.println("Eliminando estado con código: " + existing.getDocumentNumber());
        personRepositoryPort.deleteById(id);
    }
}