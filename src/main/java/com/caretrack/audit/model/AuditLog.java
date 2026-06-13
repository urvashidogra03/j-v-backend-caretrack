package com.caretrack.audit.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_audit_entity", columnList = "entityType, entityId"),
        @Index(name = "idx_audit_actor", columnList = "actorEmail"),
        @Index(name = "idx_audit_time", columnList = "createdAt")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Who performed the action (email, if available)
     */
    @Column(length = 150)
    private String actorEmail;

    /**
     * Optional actor user id for joins
     */
    private Long actorUserId;

    /**
     * Action/event name, e.g., CASE_CREATED, CASE_ASSIGNED, STATUS_UPDATED
     */
    @Column(nullable = false, length = 100)
    private String action;

    /**
     * Entity type and id being acted upon, e.g., AssistanceCase and 123
     */
    @Column(nullable = false, length = 100)
    private String entityType;

    private Long entityId;

    /**
     * Free-form details, stored as text (JSON/string)
     */
    @Lob
    private String details;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public AuditLog() {}

    public AuditLog(String actorEmail, Long actorUserId, String action, String entityType, Long entityId, String details) {
        this.actorEmail = actorEmail;
        this.actorUserId = actorUserId;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
    }

    public Long getId() { return id; }
    public String getActorEmail() { return actorEmail; }
    public Long getActorUserId() { return actorUserId; }
    public String getAction() { return action; }
    public String getEntityType() { return entityType; }
    public Long getEntityId() { return entityId; }
    public String getDetails() { return details; }
    public Instant getCreatedAt() { return createdAt; }

    public void setActorEmail(String actorEmail) { this.actorEmail = actorEmail; }
    public void setActorUserId(Long actorUserId) { this.actorUserId = actorUserId; }
    public void setAction(String action) { this.action = action; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public void setDetails(String details) { this.details = details; }
}
