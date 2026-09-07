package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.person.Person;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonPersistenceMapper {

    public PersonEntity toEntity(Person domain) {
        if (domain == null) return null;
        PersonEntity entity = new PersonEntity();
        entity.setId(domain.getId());
        entity.setCompanyId(domain.getCompanyId());
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setEmail(domain.getEmail());
        entity.setDepartment(domain.getDepartment());
        entity.setActive(domain.isActive());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    public Person toDomain(PersonEntity entity) {
        if (entity == null) return null;
        return new Person(
                entity.getId(),
                entity.getCompanyId(),
                entity.getDocumentNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getDepartment(),
                entity.isActive(),
                entity.getCreatedAt()
        );
    }
}
