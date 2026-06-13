package com.caretrack.cases.dto;

import com.caretrack.cases.model.CaseStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Request to update case status.
 */
public class CaseStatusUpdateRequest {

    @NotNull
    private CaseStatus status;

    /**
     * Optional note describing the reason/context for the status change.
     */
    private String note;

    public CaseStatusUpdateRequest() {}

    public CaseStatusUpdateRequest(CaseStatus status, String note) {
        this.status = status;
        this.note = note;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
