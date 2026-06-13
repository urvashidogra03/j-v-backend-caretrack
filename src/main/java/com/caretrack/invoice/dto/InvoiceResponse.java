package com.caretrack.invoice.dto;

import com.caretrack.invoice.model.Invoice;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for Invoice.
 */
public class InvoiceResponse {
    private Long id;
    private Long caseId;
    private String invoiceNumber;
    private BigDecimal amount;
    private String currency;
    private Invoice.Status status;
    private Instant createdAt;

    public InvoiceResponse() {}

    public InvoiceResponse(Long id, Long caseId, String invoiceNumber, BigDecimal amount, String currency,
                           Invoice.Status status, Instant createdAt) {
        this.id = id;
        this.caseId = caseId;
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getCaseId() { return caseId; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public Invoice.Status getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(Invoice.Status status) { this.status = status; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
