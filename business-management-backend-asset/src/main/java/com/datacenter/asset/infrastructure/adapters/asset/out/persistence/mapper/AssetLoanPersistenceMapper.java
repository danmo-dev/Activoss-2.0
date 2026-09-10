package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.mapper;

import com.datacenter.asset.domain.loan.AssetLoan;
import com.datacenter.asset.domain.loan.LoanStatus;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetLoanEntity;

import org.springframework.stereotype.Component;

@Component
public class AssetLoanPersistenceMapper {

    public AssetLoanEntity toEntity(AssetLoan domain) {
        if (domain == null) return null;

        AssetLoanEntity entity = new AssetLoanEntity();
        entity.setId(domain.getId());
        entity.setAssetId(domain.getAssetId());
        entity.setOriginCompanyId(domain.getOriginCompanyId());
        entity.setDestinationCompanyId(domain.getDestinationCompanyId());
        entity.setLoanDate(domain.getLoanDate());
        entity.setReturnDate(domain.getReturnDate());
        entity.setStatus(domain.getStatus() != null ? domain.getStatus().name() : null);
        entity.setObservation(domain.getObservation());
        return entity;
    }

    public AssetLoan toDomain(AssetLoanEntity entity) {
        if (entity == null) return null;

        AssetLoan domain = new AssetLoan();
        domain.setId(entity.getId());
        domain.setAssetId(entity.getAssetId());
        domain.setOriginCompanyId(entity.getOriginCompanyId());
        domain.setDestinationCompanyId(entity.getDestinationCompanyId());
        domain.setLoanDate(entity.getLoanDate());
        domain.setReturnDate(entity.getReturnDate());
        domain.setStatus(entity.getStatus() != null ? LoanStatus.valueOf(entity.getStatus()) : null);
        domain.setObservation(entity.getObservation());
        return domain;
    }
}
