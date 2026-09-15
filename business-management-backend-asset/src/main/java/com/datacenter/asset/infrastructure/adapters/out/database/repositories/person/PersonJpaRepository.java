package com.datacenter.asset.infrastructure.adapters.out.database.repositories.person;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.person.PersonEntity;

import java.util.UUID;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, UUID> {

    boolean existsByDocumentNumber(String doc);

}
