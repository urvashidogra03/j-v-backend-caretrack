package com.caretrack.cases.controller;

import com.caretrack.cases.dto.CaseAssignRequest;
import com.caretrack.cases.dto.CaseCreateRequest;
import com.caretrack.cases.dto.CaseResponse;
import com.caretrack.cases.dto.CaseStatusUpdateRequest;
import com.caretrack.cases.service.CaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Case Management APIs:
 * - POST /api/cases
 * - PATCH /api/cases/{id}/assign
 * - PATCH /api/cases/{id}/status
 */
@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * POST /api/cases
     * Create a new assistance case.
     * Roles: OPS, MANAGER, ADMIN
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('OPS','MANAGER','ADMIN')")
    public ResponseEntity<CaseResponse> create(@Valid @RequestBody CaseCreateRequest req) {
        CaseResponse created = caseService.createCase(req);
        return ResponseEntity.ok(created);
    }

    /**
     * PATCH /api/cases/{id}/assign
     * Assign a case to a specific user (operations executive).
     * Roles: MANAGER, ADMIN
     */
    @PatchMapping("/{id}/assign")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<CaseResponse> assign(
            @PathVariable("id") Long id,
            @Valid @RequestBody CaseAssignRequest req
    ) {
        CaseResponse updated = caseService.assignCase(id, req.getAssigneeUserId());
        return ResponseEntity.ok(updated);
    }

    /**
     * PATCH /api/cases/{id}/status
     * Update the case lifecycle status with optional note.
     * Roles: OPS, MANAGER, ADMIN
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('OPS','MANAGER','ADMIN')")
    public ResponseEntity<CaseResponse> updateStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody CaseStatusUpdateRequest req
    ) {
        CaseResponse updated = caseService.updateStatus(id, req.getStatus(), req.getNote());
        return ResponseEntity.ok(updated);
    }
}
