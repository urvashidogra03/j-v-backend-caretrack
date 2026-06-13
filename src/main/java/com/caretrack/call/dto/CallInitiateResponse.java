package com.caretrack.call.dto;

import com.caretrack.call.model.CallLog;

import java.time.Instant;

/**
 * Response after initiating a call.
 */
public class CallInitiateResponse {
    private Long id;
    private Long caseId;
    private String toPhone;
    private String fromExtension;
    private CallLog.Status status;
    private Integer durationSeconds;
    private String recordingUrl;
    private Instant createdAt;

    public CallInitiateResponse() {}

    public CallInitiateResponse(Long id, Long caseId, String toPhone, String fromExtension,
                                CallLog.Status status, Integer durationSeconds, String recordingUrl, Instant createdAt) {
        this.id = id;
        this.caseId = caseId;
        this.toPhone = toPhone;
        this.fromExtension = fromExtension;
        this.status = status;
        this.durationSeconds = durationSeconds;
        this.recordingUrl = recordingUrl;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getCaseId() { return caseId; }
    public String getToPhone() { return toPhone; }
    public String getFromExtension() { return fromExtension; }
    public CallLog.Status getStatus() { return status; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public String getRecordingUrl() { return recordingUrl; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
    public void setToPhone(String toPhone) { this.toPhone = toPhone; }
    public void setFromExtension(String fromExtension) { this.fromExtension = fromExtension; }
    public void setStatus(CallLog.Status status) { this.status = status; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }
    public void setRecordingUrl(String recordingUrl) { this.recordingUrl = recordingUrl; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
