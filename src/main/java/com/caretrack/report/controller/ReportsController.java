package com.caretrack.report.controller;

import com.caretrack.report.dto.CasesReportResponse;
import com.caretrack.report.dto.DashboardMetricsResponse;
import com.caretrack.report.service.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Reporting & MIS APIs:
 * - GET /api/reports/cases
 * - GET /api/dashboard/metrics
 */
@RestController
@RequestMapping("/api")
public class ReportsController {

    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    /**
     * GET /api/reports/cases
     * Aggregated case counts and status breakdown.
     * Roles: VIEWER, MANAGER, ADMIN
     */
    @GetMapping("/reports/cases")
    @PreAuthorize("hasAnyRole('VIEWER','MANAGER','ADMIN')")
    public ResponseEntity<CasesReportResponse> casesReport() {
        return ResponseEntity.ok(reportsService.getCasesReport());
    }

    /**
     * GET /api/dashboard/metrics
     * High-level dashboard metrics for ops overview.
     * Roles: VIEWER, MANAGER, ADMIN
     */
    @GetMapping("/dashboard/metrics")
    @PreAuthorize("hasAnyRole('VIEWER','MANAGER','ADMIN')")
    public ResponseEntity<DashboardMetricsResponse> dashboardMetrics() {
        return ResponseEntity.ok(reportsService.getDashboardMetrics());
    }
}
