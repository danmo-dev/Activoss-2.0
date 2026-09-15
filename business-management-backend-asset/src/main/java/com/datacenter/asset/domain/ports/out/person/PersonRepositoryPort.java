package com.datacenter.asset.domain.ports.out.person;

import com.datacenter.asset.domain.models.person.Person;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepositoryPort {
    Person save(Person person);
    Optional<Person> findById(UUID id);
    List<Person> findAll();
    boolean existsByDocumentNumber(String documentNumber);
    void deleteById(UUID id);
}