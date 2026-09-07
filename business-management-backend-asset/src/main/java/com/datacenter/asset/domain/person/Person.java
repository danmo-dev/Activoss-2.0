package com.datacenter.asset.domain.person;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class Person {
    private UUID id;
    private UUID companyId;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Boolean isActive;
}