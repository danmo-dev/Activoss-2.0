package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AssetSpecification {

    public static Specification<AssetEntity> withDynamicFilters(UUID typeId, UUID statusId, UUID locationId, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (typeId != null) {
                predicates.add(cb.equal(root.get("assetTypeId"), typeId));
            }
            if (statusId != null) {
                predicates.add(cb.equal(root.get("assetStatusId"), statusId));
            }
            if (locationId != null) {
                predicates.add(cb.equal(root.get("locationId"), locationId));
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                Predicate codeMatch = cb.like(cb.lower(root.get("code")), likePattern);
                Predicate nameMatch = cb.like(cb.lower(root.get("name")), likePattern);
                predicates.add(cb.or(codeMatch, nameMatch));
            }

            predicates.add(cb.isTrue(root.get("isActive")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}