package com.caretrack.comm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request to send a WhatsApp message, optionally linked to a case.
 */
public class WhatsAppSendRequest {

    /**
     * Optional case id to link this communication to.
     */
    private Long caseId;

    @NotBlank
    @Size(max = 30)
    private String toPhone;

    @NotBlank
    private String message;

    public WhatsAppSendRequest() {}

    public Long getCaseId() {
        return caseId;
    }

    public String getToPhone() {
        return toPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
