package com.caretrack.audit.controller;

import com.caretrack.audit.dto.AuditLogResponse;
import com.caretrack.audit.model.AuditLog;
import com.caretrack.audit.repo.AuditLogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Audit & Compliance APIs:
 * - GET /api/audit-logs
 *
 * Returns recent audit log entries, newest first.
 * Optional query params:
 *   - size (default 50)
 *   - page (default 0)
 *   - entityType (optional filter)
 *   - entityId (optional filter)
 *   - action (optional filter)
 */
@RestController
@RequestMapping("/api")
public class AuditLogController {

    private final AuditLogRepository auditRepo; //auditlog store on db

    public AuditLogController(AuditLogRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    @GetMapping("/audit-logs")
    @PreAuthorize("hasAnyRole('VIEWER','MANAGER','ADMIN')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogs(
            @RequestParam(value = "size", defaultValue = "50") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "entityType", required = false) String entityType,
            @RequestParam(value = "entityId", required = false) Long entityId,
            @RequestParam(value = "action", required = false) String action
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<AuditLog> raw = auditRepo.findAll(pageable).getContent();

        // Basic in-memory filtering (sufficient for demo). For production, implement proper query methods/specs.
        List<AuditLogResponse> results = raw.stream()
                .filter(a -> entityType == null || entityType.equalsIgnoreCase(a.getEntityType()))
                .filter(a -> entityId == null || entityId.equals(a.getEntityId()))
                .filter(a -> action == null || action.equalsIgnoreCase(a.getAction()))
                .map(a -> new AuditLogResponse(
                        a.getId(),
                        a.getActorEmail(),
                        a.getActorUserId(),
                        a.getAction(),
                        a.getEntityType(),
                        a.getEntityId(),
                        a.getDetails(),
                        a.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(results);
    }
}
