package com.datacenter.asset.domain.ports.in.person;

import com.datacenter.asset.domain.models.person.Person;
import java.util.List;
import java.util.UUID;

public interface ManagePersonUseCase {
    Person createPerson(Person person);
    Person getPersonById(UUID id);
    List<Person> getAllPersons();
    Person update(UUID id, Person person); // <-- Nuevo
    Person activate(UUID id);              // <-- Nuevo
    Person deactivate(UUID id);            // <-- Nuevo
    void delete(UUID id);
}