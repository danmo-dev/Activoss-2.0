package com.datacenter.asset.infrastructure.adapters.person.in.rest.controller;

import com.datacenter.asset.domain.person.Person;
import com.datacenter.asset.domain.ports.person.in.ManagePersonUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {
    private final ManagePersonUseCase managePersonUseCase;

    public PersonController(ManagePersonUseCase managePersonUseCase) {
        this.managePersonUseCase = managePersonUseCase;
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.ok(managePersonUseCase.createPerson(person));
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(managePersonUseCase.getAllPersons());
    }
}