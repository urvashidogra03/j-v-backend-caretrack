package com.caretrack.cases.service;

import com.caretrack.audit.service.AuditService;
import com.caretrack.cases.dto.CaseCreateRequest;
import com.caretrack.cases.dto.CaseResponse;
import com.caretrack.cases.model.AssistanceCase;
import com.caretrack.cases.model.CaseStatus;
import com.caretrack.cases.repo.AssistanceCaseRepository;
import com.caretrack.provider.model.Provider;
import com.caretrack.provider.repo.ProviderRepository;
import com.caretrack.user.model.User;
import com.caretrack.user.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CaseService {

    private final AssistanceCaseRepository caseRepo;
    private final ProviderRepository providerRepo;
    private final UserRepository userRepo;
    private final AuditService audit;

    public CaseService(AssistanceCaseRepository caseRepo,
                       ProviderRepository providerRepo,
                       UserRepository userRepo,
                       AuditService audit) {
        this.caseRepo = caseRepo;
        this.providerRepo = providerRepo;
        this.userRepo = userRepo;
        this.audit = audit;
    }

    @Transactional
    public CaseResponse createCase(CaseCreateRequest req) {
        User actor = requireCurrentUser();

        AssistanceCase c = new AssistanceCase();
        c.setTitle(req.getTitle());
        c.setPatientName(req.getPatientName());
        c.setNotes(req.getNotes());
        c.setStatus(CaseStatus.OPEN);
        c.setCreatedBy(actor);

        if (req.getProviderId() != null) {
            Provider p = providerRepo.findById(req.getProviderId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
            c.setProvider(p);
        }

        AssistanceCase saved = caseRepo.save(c);

        // Generate human-readable reference if absent
        if (StringUtils.isBlank(saved.getReference())) {
            saved.setReference(generateReference(saved));
            saved = caseRepo.save(saved);
        }

        audit.record("CASE_CREATED", "AssistanceCase", saved.getId(),
                "title=" + safe(saved.getTitle()));

        return toResponse(saved);
    }

    @Transactional
    public CaseResponse assignCase(Long caseId, Long assigneeUserId) {
        User actor = requireCurrentUser();

        AssistanceCase c = caseRepo.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        User assignee = userRepo.findById(assigneeUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignee user not found"));

        c.setAssignedTo(assignee);
        if (c.getStatus() == CaseStatus.OPEN) {
            c.setStatus(CaseStatus.ASSIGNED);
        }
        AssistanceCase saved = caseRepo.save(c);

        audit.record("CASE_ASSIGNED", "AssistanceCase", saved.getId(),
                "assignedTo=" + safe(assignee.getEmail()) + ", by=" + safe(actor.getEmail()));

        return toResponse(saved);
    }

    @Transactional
    public CaseResponse updateStatus(Long caseId, CaseStatus newStatus, String note) {
        requireCurrentUser();

        AssistanceCase c = caseRepo.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        CaseStatus old = c.getStatus();
        c.setStatus(newStatus);
        if (StringUtils.isNotBlank(note)) {
            // Append note with a simple delimiter; a production system might use a separate timeline entity
            String merged = (StringUtils.trimToEmpty(c.getNotes()) + System.lineSeparator()
                    + "[" + newStatus + "] " + note).trim();
            c.setNotes(merged);
        }

        AssistanceCase saved = caseRepo.save(c);

        audit.record("CASE_STATUS_UPDATED", "AssistanceCase", saved.getId(),
                "from=" + old + ", to=" + newStatus);

        return toResponse(saved);
    }

    private User requireCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User u) || !u.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        // Reload user to ensure managed entity context if needed
        Optional<User> reloaded = userRepo.findById(u.getId());
        return reloaded.orElse(u);
    }

    private CaseResponse toResponse(AssistanceCase c) {
        Long assignedId = c.getAssignedTo() != null ? c.getAssignedTo().getId() : null;
        String assignedName = c.getAssignedTo() != null ? c.getAssignedTo().getFullName() : null;
        Long providerId = c.getProvider() != null ? c.getProvider().getId() : null;
        String providerName = c.getProvider() != null ? c.getProvider().getName() : null;

        return new CaseResponse(
                c.getId(),
                c.getReference(),
                c.getTitle(),
                c.getPatientName(),
                c.getStatus(),
                assignedId,
                assignedName,
                providerId,
                providerName,
                c.getNotes(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

    private String generateReference(AssistanceCase c) {
        String date = LocalDate.now().toString().replace("-", "");
        String idPart = String.format("%06d", c.getId() == null ? 0 : c.getId());
        return "CASE-" + date + "-" + idPart;
    }

    private String safe(String v) {
        return v == null ? "" : v;
    }
}
