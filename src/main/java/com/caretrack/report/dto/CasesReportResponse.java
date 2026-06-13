package com.caretrack.report.dto;

import java.util.Map;

/**
 * Aggregated case counts for MIS reporting.
 */
public class CasesReportResponse {
    private long total;
    private long openedLast7Days;
    private Map<String, Long> byStatus;

    public CasesReportResponse() {}

    public CasesReportResponse(long total, long openedLast7Days, Map<String, Long> byStatus) {
        this.total = total;
        this.openedLast7Days = openedLast7Days;
        this.byStatus = byStatus;
    }

    public long getTotal() {
        return total;
    }

    public long getOpenedLast7Days() {
        return openedLast7Days;
    }

    public Map<String, Long> getByStatus() {
        return byStatus;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setOpenedLast7Days(long openedLast7Days) {
        this.openedLast7Days = openedLast7Days;
    }

    public void setByStatus(Map<String, Long> byStatus) {
        this.byStatus = byStatus;
    }
}
