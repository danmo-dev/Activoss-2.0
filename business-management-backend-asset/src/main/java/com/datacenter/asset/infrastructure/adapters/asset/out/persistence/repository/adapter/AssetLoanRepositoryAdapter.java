package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.loan.AssetLoan;
import com.datacenter.asset.domain.loan.LoanStatus;
import com.datacenter.asset.domain.ports.asset.out.AssetLoanRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetLoanEntity;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetLoanJpaRepository;

import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Component
public class AssetLoanRepositoryAdapter implements AssetLoanRepositoryPort {

    private final AssetLoanJpaRepository repository;

    public AssetLoanRepositoryAdapter(AssetLoanJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public AssetLoan save(AssetLoan loan) {
        AssetLoanEntity entity = new AssetLoanEntity();
        entity.setId(loan.getId());
        entity.setAssetId(loan.getAssetId());
        entity.setOriginCompanyId(loan.getOriginCompanyId());
        entity.setDestinationCompanyId(loan.getDestinationCompanyId());
        entity.setLoanDate(loan.getLoanDate());
        entity.setReturnDate(loan.getReturnDate());
        entity.setStatus(loan.getStatus().name());
        entity.setObservation(loan.getObservation());
        
        AssetLoanEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<AssetLoan> findById(UUID id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<AssetLoan> findByAssetId(UUID assetId) {
        return repository.findByAssetId(assetId).stream()
                .map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public boolean hasActiveLoan(UUID assetId) {
        return repository.existsByAssetIdAndStatus(assetId, LoanStatus.ACTIVE.name());
    }

    private AssetLoan mapToDomain(AssetLoanEntity entity) {
        AssetLoan domain = new AssetLoan();
        domain.setId(entity.getId());
        domain.setAssetId(entity.getAssetId());
        domain.setOriginCompanyId(entity.getOriginCompanyId());
        domain.setDestinationCompanyId(entity.getDestinationCompanyId());
        domain.setLoanDate(entity.getLoanDate());
        domain.setReturnDate(entity.getReturnDate());
        domain.setStatus(LoanStatus.valueOf(entity.getStatus()));
        domain.setObservation(entity.getObservation());
        return domain;
    }
}