package com.datacenter.asset.infrastructure.adapters.person.in.rest.controller;

import com.datacenter.asset.domain.person.Person;
import com.datacenter.asset.domain.ports.person.in.ManagePersonUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    // --- NUEVOS ENDPOINTS ---

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok(managePersonUseCase.getPersonById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable UUID id, @RequestBody Person person) {
        return ResponseEntity.ok(managePersonUseCase.update(id, person));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Person> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(managePersonUseCase.activate(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Person> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(managePersonUseCase.deactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        managePersonUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}