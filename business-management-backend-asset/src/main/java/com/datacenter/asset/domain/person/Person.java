package com.datacenter.asset.domain.person;

import java.time.LocalDateTime;
import java.util.UUID;

public class Person {
    private UUID id;
    private UUID companyId;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private boolean isActive;
    private LocalDateTime createdAt;

    // Constructores, Getters y Setters
    public Person(UUID id, UUID companyId, String documentNumber, String firstName, String lastName, String email, String department, boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public String getDocumentNumber() { return documentNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}