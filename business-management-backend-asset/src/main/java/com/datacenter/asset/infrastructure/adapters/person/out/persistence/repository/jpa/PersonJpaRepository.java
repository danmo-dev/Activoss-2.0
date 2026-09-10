package com.datacenter.asset.infrastructure.adapters.person.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.person.out.persistence.entity.PersonEntity;

import java.util.UUID;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, UUID> {

    boolean existsByDocumentNumber(String doc);

}
