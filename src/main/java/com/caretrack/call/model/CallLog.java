package com.caretrack.call.model;

import com.caretrack.cases.model.AssistanceCase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "call_logs", indexes = {
        @Index(name = "idx_call_case", columnList = "case_id"),
        @Index(name = "idx_call_time", columnList = "createdAt")
})
public class CallLog {

    public enum Status {
        INITIATED,
        CONNECTED,
        FAILED,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Optional link to a case if this call is case-related
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private AssistanceCase assistanceCase;

    /**
     * Phone number dialed (E.164 formatted ideally)
     */
    @Column(length = 30)
    private String toPhone;

    /**
     * Internal extension or number from which the call is placed
     */
    @Column(length = 30)
    private String fromExtension;

    /**
     * Duration in seconds (if completed)
     */
    private Integer durationSeconds;

    /**
     * URL to recording metadata if available
     */
    @Column(length = 500)
    private String recordingUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.INITIATED;

    /**
     * Error details on failed calls
     */
    @Lob
    private String error;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public CallLog() {}

    public Long getId() { return id; }
    public AssistanceCase getAssistanceCase() { return assistanceCase; }
    public String getToPhone() { return toPhone; }
    public String getFromExtension() { return fromExtension; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public String getRecordingUrl() { return recordingUrl; }
    public Status getStatus() { return status; }
    public String getError() { return error; }
    public Instant getCreatedAt() { return createdAt; }

    public void setAssistanceCase(AssistanceCase assistanceCase) { this.assistanceCase = assistanceCase; }
    public void setToPhone(String toPhone) { this.toPhone = toPhone; }
    public void setFromExtension(String fromExtension) { this.fromExtension = fromExtension; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }
    public void setRecordingUrl(String recordingUrl) { this.recordingUrl = recordingUrl; }
    public void setStatus(Status status) { this.status = status; }
    public void setError(String error) { this.error = error; }
}
