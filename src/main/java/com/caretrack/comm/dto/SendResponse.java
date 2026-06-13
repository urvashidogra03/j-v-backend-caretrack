package com.caretrack.comm.dto;

import com.caretrack.comm.model.CommunicationLog;
import com.caretrack.comm.model.CommunicationType;

import java.time.Instant;

/**
 * Response after attempting to send a communication.
 */
public class SendResponse {
    private Long id;
    private CommunicationType channel;
    private CommunicationLog.Status status;
    private String providerMessageId;
    private Long caseId;
    private String toRecipient;
    private Instant createdAt;

    public SendResponse() {}

    public SendResponse(Long id, CommunicationType channel, CommunicationLog.Status status, String providerMessageId,
                        Long caseId, String toRecipient, Instant createdAt) {
        this.id = id;
        this.channel = channel;
        this.status = status;
        this.providerMessageId = providerMessageId;
        this.caseId = caseId;
        this.toRecipient = toRecipient;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public CommunicationType getChannel() { return channel; }
    public CommunicationLog.Status getStatus() { return status; }
    public String getProviderMessageId() { return providerMessageId; }
    public Long getCaseId() { return caseId; }
    public String getToRecipient() { return toRecipient; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setChannel(CommunicationType channel) { this.channel = channel; }
    public void setStatus(CommunicationLog.Status status) { this.status = status; }
    public void setProviderMessageId(String providerMessageId) { this.providerMessageId = providerMessageId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
    public void setToRecipient(String toRecipient) { this.toRecipient = toRecipient; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
