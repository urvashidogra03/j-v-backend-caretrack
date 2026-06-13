package com.caretrack.comm.model;

import com.caretrack.cases.model.AssistanceCase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "communication_logs", indexes = {
        @Index(name = "idx_comm_case", columnList = "case_id"),
        @Index(name = "idx_comm_type", columnList = "type"),
        @Index(name = "idx_comm_time", columnList = "createdAt")
})
//CommunicationLog is a database table that stores every external communication sent by our system
public class CommunicationLog {

    public enum Status {
        SENT,
        FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Optional link to a case if this communication is case-related
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private AssistanceCase assistanceCase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CommunicationType type;//Examples:WHATSAPP,EMAIL SMS,CALL

    /**
     * Recipient (phone or email depending on type)
     */
    @Column(length = 180)
    private String toRecipient;

    /**
     * Sender identifier (e.g., system number/mailbox)
     */
    @Column(length = 180)
    private String fromSender;

    /**
     * Message content (SMS/WhatsApp body or Email subject+body combined for simple demo)
     */
    @Lob
    private String content;

    /**
     * Upstream provider message id/reference if available
     */
    @Column(length = 180)
    private String providerMessageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.SENT;

    /**
     * Error detail when failed
     */
    @Lob
    private String error;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public CommunicationLog() {}

    public Long getId() { return id; }
    public AssistanceCase getAssistanceCase() { return assistanceCase; }
    public CommunicationType getType() { return type; }
    public String getToRecipient() { return toRecipient; }
    public String getFromSender() { return fromSender; }
    public String getContent() { return content; }
    public String getProviderMessageId() { return providerMessageId; }
    public Status getStatus() { return status; }
    public String getError() { return error; }
    public Instant getCreatedAt() { return createdAt; }

    public void setAssistanceCase(AssistanceCase assistanceCase) { this.assistanceCase = assistanceCase; }
    public void setType(CommunicationType type) { this.type = type; }
    public void setToRecipient(String toRecipient) { this.toRecipient = toRecipient; }
    public void setFromSender(String fromSender) { this.fromSender = fromSender; }
    public void setContent(String content) { this.content = content; }
    public void setProviderMessageId(String providerMessageId) { this.providerMessageId = providerMessageId; }
    public void setStatus(Status status) { this.status = status; }
    public void setError(String error) { this.error = error; }
}
