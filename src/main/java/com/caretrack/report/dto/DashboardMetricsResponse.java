package com.caretrack.report.dto;

import java.math.BigDecimal;
import java.util.Map;

/**
 * High-level dashboard metrics for quick operational overview.
 */
public class DashboardMetricsResponse {
    // Cases
    private long totalCases;
    private Map<String, Long> casesByStatus;

    // Providers
    private long totalProviders;

    // Communications
    private long totalCommunicationsLast7Days;

    // Calls
    private long totalCallsLast7Days;

    // Invoices
    private long totalInvoices;
    private long invoicesPaid;
    private BigDecimal invoicesAmountSum;

    public DashboardMetricsResponse() {}

    public DashboardMetricsResponse(long totalCases, Map<String, Long> casesByStatus, long totalProviders,
                                    long totalCommunicationsLast7Days, long totalCallsLast7Days,
                                    long totalInvoices, long invoicesPaid, BigDecimal invoicesAmountSum) {
        this.totalCases = totalCases;
        this.casesByStatus = casesByStatus;
        this.totalProviders = totalProviders;
        this.totalCommunicationsLast7Days = totalCommunicationsLast7Days;
        this.totalCallsLast7Days = totalCallsLast7Days;
        this.totalInvoices = totalInvoices;
        this.invoicesPaid = invoicesPaid;
        this.invoicesAmountSum = invoicesAmountSum;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(long totalCases) {
        this.totalCases = totalCases;
    }

    public Map<String, Long> getCasesByStatus() {
        return casesByStatus;
    }

    public void setCasesByStatus(Map<String, Long> casesByStatus) {
        this.casesByStatus = casesByStatus;
    }

    public long getTotalProviders() {
        return totalProviders;
    }

    public void setTotalProviders(long totalProviders) {
        this.totalProviders = totalProviders;
    }

    public long getTotalCommunicationsLast7Days() {
        return totalCommunicationsLast7Days;
    }

    public void setTotalCommunicationsLast7Days(long totalCommunicationsLast7Days) {
        this.totalCommunicationsLast7Days = totalCommunicationsLast7Days;
    }

    public long getTotalCallsLast7Days() {
        return totalCallsLast7Days;
    }

    public void setTotalCallsLast7Days(long totalCallsLast7Days) {
        this.totalCallsLast7Days = totalCallsLast7Days;
    }

    public long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public long getInvoicesPaid() {
        return invoicesPaid;
    }

    public void setInvoicesPaid(long invoicesPaid) {
        this.invoicesPaid = invoicesPaid;
    }

    public BigDecimal getInvoicesAmountSum() {
        return invoicesAmountSum;
    }

    public void setInvoicesAmountSum(BigDecimal invoicesAmountSum) {
        this.invoicesAmountSum = invoicesAmountSum;
    }
}
