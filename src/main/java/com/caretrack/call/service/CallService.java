package com.caretrack.call.service;

import com.caretrack.audit.service.AuditService;
import com.caretrack.call.dto.CallInitiateRequest;
import com.caretrack.call.dto.CallInitiateResponse;
import com.caretrack.call.model.CallLog;
import com.caretrack.call.repo.CallLogRepository;
import com.caretrack.cases.model.AssistanceCase;
import com.caretrack.cases.repo.AssistanceCaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * CallService provides a stub to initiate calls, persists call logs,
 * and records audit events. In production, integrate with a VoIP provider
 * (e.g., Twilio, SIP PBX) to actually place calls and update statuses.
 */
@Service
public class CallService {

    private final CallLogRepository callRepo;
    private final AssistanceCaseRepository caseRepo;
    private final AuditService audit;

    public CallService(CallLogRepository callRepo,
                       AssistanceCaseRepository caseRepo,
                       AuditService audit) {
        this.callRepo = callRepo;
        this.caseRepo = caseRepo;
        this.audit = audit;
    }

    @Transactional
    public CallInitiateResponse initiate(CallInitiateRequest req) {
        AssistanceCase linkedCase = null;
        if (req.getCaseId() != null) {
            linkedCase = caseRepo.findById(req.getCaseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        }

        CallLog log = new CallLog();
        log.setAssistanceCase(linkedCase);
        log.setToPhone(req.getToPhone());
        log.setFromExtension(req.getFromExtension());
        log.setStatus(CallLog.Status.INITIATED);

        // Integration stub: place call with provider, set status/recording/duration when webhook/callback arrives
        CallLog saved = callRepo.save(log);

        audit.record("CALL_INITIATED", "CallLog", saved.getId(),
                "to=" + safe(req.getToPhone()) + ", caseId=" + (req.getCaseId() == null ? "" : req.getCaseId()));

        return new CallInitiateResponse(
                saved.getId(),
                saved.getAssistanceCase() != null ? saved.getAssistanceCase().getId() : null,
                saved.getToPhone(),
                saved.getFromExtension(),
                saved.getStatus(),
                saved.getDurationSeconds(),
                saved.getRecordingUrl(),
                saved.getCreatedAt()
        );
    }

    private String safe(String v) {
        return v == null ? "" : v;
    }
}
