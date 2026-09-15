package com.datacenter.asset.application.usecases.person;

import com.datacenter.asset.domain.models.person.Person;
import com.datacenter.asset.domain.ports.in.person.ManagePersonUseCase;
import com.datacenter.asset.application.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManagePersonUseCaseImpl implements ManagePersonUseCase {

    private final PersonService personService;

    @Override
    @Transactional
    public Person createPerson(Person person) {
        return personService.create(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPersonById(UUID id) {
        return personService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @Override
    @Transactional
    public Person update(UUID id, Person person) {
        return personService.update(id, person);
    }

    @Override
    @Transactional
    public Person activate(UUID id) {
        return personService.activate(id);
    }

    @Override
    @Transactional
    public Person deactivate(UUID id) {
        return personService.deactivate(id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        personService.delete(id);
    }
}