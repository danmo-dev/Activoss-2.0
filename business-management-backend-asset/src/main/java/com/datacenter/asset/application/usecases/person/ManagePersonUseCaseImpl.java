package com.datacenter.asset.application.usecases.person;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.person.Person;
import com.datacenter.asset.domain.ports.in.person.ManagePersonUseCase;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManagePersonUseCaseImpl implements ManagePersonUseCase {

    private final PersonRepositoryPort personRepositoryPort;
    private final CompanyRepositoryPort companyRepositoryPort;

    @Override
    @Transactional
    public Person createPerson(Person person) {
        if (personRepositoryPort.existsByDocumentNumber(person.getDocumentNumber())) {
            throw new BusinessException("Ya existe una persona con este documento.");
        }
        if (companyRepositoryPort.findById(person.getCompanyId()).isEmpty()) {
            throw new BusinessException("La empresa asociada no existe.");
        }
        if (person.getCreatedAt() == null) {
            person.setCreatedAt(LocalDateTime.now());
            person.setActive(true);
        }
        return personRepositoryPort.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPersonById(UUID id) {
        return personRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public Person update(UUID id, Person updatedData) {
        Person existing = getPersonById(id);

        if (!existing.getDocumentNumber().equals(updatedData.getDocumentNumber()) &&
            personRepositoryPort.existsByDocumentNumber(updatedData.getDocumentNumber())) {
            throw new BusinessException("Ya existe otra persona con este documento.");
        }
        if (!existing.getCompanyId().equals(updatedData.getCompanyId()) &&
            companyRepositoryPort.findById(updatedData.getCompanyId()).isEmpty()) {
            throw new BusinessException("La empresa asociada no existe.");
        }

        existing.setCompanyId(updatedData.getCompanyId());
        existing.setDocumentNumber(updatedData.getDocumentNumber());
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setDepartment(updatedData.getDepartment());

        return personRepositoryPort.save(existing);
    }

    @Override
    @Transactional
    public Person activate(UUID id) {
        Person person = getPersonById(id);
        person.setActive(true);
        return personRepositoryPort.save(person);
    }

    @Override
    @Transactional
    public Person deactivate(UUID id) {
        Person person = getPersonById(id);
        person.setActive(false);
        return personRepositoryPort.save(person);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        personRepositoryPort.deleteById(getPersonById(id).getId());
    }
}