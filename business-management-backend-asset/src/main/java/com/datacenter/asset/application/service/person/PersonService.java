package com.datacenter.asset.application.service.person;

import com.datacenter.asset.domain.models.person.Person;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepositoryPort personRepositoryPort;
    private final CompanyRepositoryPort companyRepositoryPort;

    public Person create(Person person) {
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

    public Person findById(UUID id) {
        return personRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    public List<Person> findAll() {
        return personRepositoryPort.findAll();
    }

    public Person update(UUID id, Person updatedData) {
        Person existing = findById(id);

        if (!existing.getDocumentNumber().equals(updatedData.getDocumentNumber()) &&
            personRepositoryPort.existsByDocumentNumber(updatedData.getDocumentNumber())) {
            throw new IllegalArgumentException("Ya existe otra persona con este documento.");
        }

        if (!existing.getCompanyId().equals(updatedData.getCompanyId()) &&
            companyRepositoryPort.findById(updatedData.getCompanyId()).isEmpty()) {
            throw new IllegalArgumentException("La empresa asociada no existe.");
        }

        existing.setCompanyId(updatedData.getCompanyId());
        existing.setDocumentNumber(updatedData.getDocumentNumber());
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setDepartment(updatedData.getDepartment());

        return personRepositoryPort.save(existing);
    }

    public Person activate(UUID id) {
        Person person = findById(id);
        person.setActive(true);
        return personRepositoryPort.save(person);
    }

    public Person deactivate(UUID id) {
        Person person = findById(id);
        person.setActive(false);
        return personRepositoryPort.save(person);
    }

    public void delete(UUID id) {
        Person existing = findById(id);
        System.out.println("Eliminando persona con documento: " + existing.getDocumentNumber());
        personRepositoryPort.deleteById(id);
    }
}