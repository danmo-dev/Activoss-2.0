package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.person.Person;
import java.util.List;
import java.util.UUID;

public interface ManagePersonUseCase {
    Person createPerson(Person person);
    Person getPersonById(UUID id);
    List<Person> getAllPersons();
}