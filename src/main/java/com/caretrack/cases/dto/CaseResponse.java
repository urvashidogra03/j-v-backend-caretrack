package com.caretrack.cases.dto;

import com.caretrack.cases.model.CaseStatus;

import java.time.Instant;

/**
 * Response DTO for AssistanceCase.
 */
public class CaseResponse {
    private Long id;
    private String reference;
    private String title;
    private String patientName;
    private CaseStatus status;

    private Long assignedToUserId;
    private String assignedToName;

    private Long providerId;
    private String providerName;

    private String notes;

    private Instant createdAt;
    private Instant updatedAt;

    public CaseResponse() {}

    public CaseResponse(Long id, String reference, String title, String patientName, CaseStatus status,
                        Long assignedToUserId, String assignedToName,
                        Long providerId, String providerName,
                        String notes, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.reference = reference;
        this.title = title;
        this.patientName = patientName;
        this.status = status;
        this.assignedToUserId = assignedToUserId;
        this.assignedToName = assignedToName;
        this.providerId = providerId;
        this.providerName = providerName;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getReference() { return reference; }
    public String getTitle() { return title; }
    public String getPatientName() { return patientName; }
    public CaseStatus getStatus() { return status; }
    public Long getAssignedToUserId() { return assignedToUserId; }
    public String getAssignedToName() { return assignedToName; }
    public Long getProviderId() { return providerId; }
    public String getProviderName() { return providerName; }
    public String getNotes() { return notes; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setReference(String reference) { this.reference = reference; }
    public void setTitle(String title) { this.title = title; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setStatus(CaseStatus status) { this.status = status; }
    public void setAssignedToUserId(Long assignedToUserId) { this.assignedToUserId = assignedToUserId; }
    public void setAssignedToName(String assignedToName) { this.assignedToName = assignedToName; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
