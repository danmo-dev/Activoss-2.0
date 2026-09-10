package com.datacenter.asset.domain.ports.person.out;

import com.datacenter.asset.domain.person.Person;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepositoryPort {
    Person save(Person person);
    Optional<Person> findById(UUID id);
    List<Person> findAll();
    boolean existsByDocumentNumber(String documentNumber);
}