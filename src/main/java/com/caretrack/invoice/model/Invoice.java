package com.caretrack.invoice.model;

import com.caretrack.cases.model.AssistanceCase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "invoices", indexes = {
        @Index(name = "idx_invoice_case", columnList = "case_id"),
        @Index(name = "idx_invoice_status", columnList = "status"),
        @Index(name = "idx_invoice_created", columnList = "createdAt")
})
public class Invoice {

    public enum Status {
        DRAFT,
        SENT,
        PAID,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Optional link to a case for case-wise billing
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private AssistanceCase assistanceCase;

    /**
     * Human-readable invoice number
     */
    @Column(length = 50)
    private String invoiceNumber;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(nullable = false, length = 10)
    private String currency = "USD";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.DRAFT;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Invoice() {}

    public Long getId() { return id; }
    public AssistanceCase getAssistanceCase() { return assistanceCase; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public Status getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    public void setAssistanceCase(AssistanceCase assistanceCase) { this.assistanceCase = assistanceCase; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(Status status) { this.status = status; }
}
