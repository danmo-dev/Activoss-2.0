package com.datacenter.asset.infrastructure.adapters.out.database.adapters.person;

import com.datacenter.asset.domain.models.person.Person;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.person.PersonEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.person.PersonPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.person.PersonJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Component
@RequiredArgsConstructor
public class PersonRepositoryAdapter implements PersonRepositoryPort {

    private final PersonJpaRepository jpaRepository;
    private final PersonPersistenceMapper mapper;

    @Override
    public Person save(Person person) {
        PersonEntity entity = mapper.toEntity(person);
        PersonEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Person> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return jpaRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
