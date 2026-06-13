package com.caretrack.cases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body for creating a new assistance case.
 */
public class CaseCreateRequest {

    @NotBlank
    @Size(max = 180)
    private String title;

    @Size(max = 120)
    private String patientName;

    /**
     * Optional provider id to associate at creation time.
     */
    private Long providerId;

    /**
     * Free-form notes.
     */
    private String notes;

    public CaseCreateRequest() {}

    public String getTitle() {
        return title;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getNotes() {
        return notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
