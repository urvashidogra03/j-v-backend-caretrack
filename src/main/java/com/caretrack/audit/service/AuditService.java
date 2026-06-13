package com.caretrack.audit.service;

import com.caretrack.audit.model.AuditLog;
import com.caretrack.audit.repo.AuditLogRepository;
import com.caretrack.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * AuditService records system actions for compliance and traceability.
 * It attempts to capture the authenticated actor (email, id) if available.
 */
@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    private final AuditLogRepository repo;

    public AuditService(AuditLogRepository repo) {
        this.repo = repo;
    }

    /**
     * Record an audit event, inferring the actor from the SecurityContext if present.
     */
    public void record(String action, String entityType, Long entityId, String details) {
        var actor = resolveActor();
        AuditLog entry = new AuditLog(actor.email, actor.userId, action, entityType, entityId, details);
        repo.save(entry);
        log.debug("Audit recorded: action={}, entityType={}, entityId={}, actor={}", action, entityType, entityId, actor.email);
    }

    private Actor resolveActor() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof User u) {
                return new Actor(u.getEmail(), u.getId());
            }
        } catch (Exception ignored) {
        }
        return new Actor("system", null);
    }

    private record Actor(String email, Long userId) {}
}
