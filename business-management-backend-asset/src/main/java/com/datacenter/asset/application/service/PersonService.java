package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.person.Person;
import com.datacenter.asset.domain.ports.in.ManagePersonUseCase;
import com.datacenter.asset.domain.ports.out.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.out.PersonRepositoryPort;
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

        // 🔎 Aquí agregamos lo necesario:
        if (person.getCreatedAt() == null) {
            person = new Person(
                person.getId(),
                person.getCompanyId(),
                person.getDocumentNumber(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getDepartment(),
                person.isActive(),
                LocalDateTime.now()   // asigna fecha actual si viene nulo
            );
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
}
