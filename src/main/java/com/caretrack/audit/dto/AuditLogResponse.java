package com.caretrack.audit.dto;

import java.time.Instant;

public class AuditLogResponse {
    private Long id;
    private String actorEmail;
    private Long actorUserId;
    private String action;
    private String entityType;
    private Long entityId;
    private String details;
    private Instant createdAt;

    public AuditLogResponse() {}

    public AuditLogResponse(Long id, String actorEmail, Long actorUserId, String action, String entityType, Long entityId, String details, Instant createdAt) {
        this.id = id;
        this.actorEmail = actorEmail;
        this.actorUserId = actorUserId;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getActorEmail() { return actorEmail; }
    public Long getActorUserId() { return actorUserId; }
    public String getAction() { return action; }
    public String getEntityType() { return entityType; }
    public Long getEntityId() { return entityId; }
    public String getDetails() { return details; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setActorEmail(String actorEmail) { this.actorEmail = actorEmail; }
    public void setActorUserId(Long actorUserId) { this.actorUserId = actorUserId; }
    public void setAction(String action) { this.action = action; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public void setDetails(String details) { this.details = details; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
