package com.caretrack.invoice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Request to create an invoice, optionally linked to a case.
 */
public class InvoiceCreateRequest {

    /**
     * Optional case id to link this invoice with a case.
     */
    private Long caseId;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal amount;

    @Size(max = 10)
    private String currency = "USD";

    public InvoiceCreateRequest() {}

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
