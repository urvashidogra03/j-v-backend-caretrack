package com.caretrack.cases.model;

import com.caretrack.provider.model.Provider;
import com.caretrack.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "cases", indexes = {
        @Index(name = "idx_case_status", columnList = "status"),
        @Index(name = "idx_case_assigned_to", columnList = "assigned_to_id"),
        @Index(name = "idx_case_provider", columnList = "provider_id")
})
public class AssistanceCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Human-readable reference for external communication (unique-ish in practice)
     */
    @Size(max = 50)
    @Column(length = 50)
    private String reference;

    /**
     * Short summary/title for quick listing
     */
    @NotBlank
    @Size(max = 180)
    @Column(nullable = false)
    private String title;

    /**
     * Patient info (minimal for demo). In production, PII handling would be governed by compliance.
     */
    @Size(max = 120)
    private String patientName;

    /**
     * Current lifecycle status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CaseStatus status = CaseStatus.OPEN;

    /**
     * Assigned operations executive (user)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    /**
     * Associated provider (hospital/clinic/etc.), optional at creation
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    /**
     * Creator of this case (user)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    /**
     * Free-form notes; production systems might store structured timelines/activities.
     */
    @Lob
    private String notes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public AssistanceCase() {}

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
