package com.caretrack.comm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request to send an Email, optionally linked to a case.
 */
public class EmailSendRequest {

    /**
     * Optional case id to link this communication to.
     */
    private Long caseId;

    @Email
    @NotBlank
    private String toEmail;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;

    public EmailSendRequest() {}

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
