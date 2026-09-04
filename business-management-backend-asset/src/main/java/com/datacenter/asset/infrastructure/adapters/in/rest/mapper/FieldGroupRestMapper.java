package com.datacenter.asset.infrastructure.adapters.in.rest.mapper;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldGroupResponse;
import org.springframework.stereotype.Component;

@Component
public class FieldGroupRestMapper {

    public FieldGroupResponse toResponse(FieldGroup domain) {

        return new FieldGroupResponse(
                domain.getId(),
                domain.getSubAssetTypeId(),
                domain.getName(),
                domain.getDisplayOrder(),
                domain.getActive()
        );
    }
}