package com.caretrack.call.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request to initiate a VoIP call (stub). Optionally links to a case.
 */
public class CallInitiateRequest {

    /**
     * Optional case id to link this call to.
     */
    private Long caseId;

    @NotBlank
    @Size(max = 30)
    private String toPhone;

    /**
     * Optional internal extension or caller id
     */
    @Size(max = 30)
    private String fromExtension;

    public CallInitiateRequest() {}

    public Long getCaseId() {
        return caseId;
    }

    public String getToPhone() {
        return toPhone;
    }

    public String getFromExtension() {
        return fromExtension;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public void setFromExtension(String fromExtension) {
        this.fromExtension = fromExtension;
    }
}
