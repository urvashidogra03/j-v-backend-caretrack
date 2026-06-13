package com.caretrack.comm.service;

import com.caretrack.audit.service.AuditService;
import com.caretrack.cases.model.AssistanceCase;
import com.caretrack.cases.repo.AssistanceCaseRepository;
import com.caretrack.comm.dto.EmailSendRequest;
import com.caretrack.comm.dto.SendResponse;
import com.caretrack.comm.dto.WhatsAppSendRequest;
import com.caretrack.comm.model.CommunicationLog;
import com.caretrack.comm.model.CommunicationType;
import com.caretrack.comm.repo.CommunicationLogRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * CommunicationsService persists communication attempts and is integration-ready
 * to plug in external providers (WhatsApp Business/Twilio, SMTP, etc.).
 */
//CommunicationsService is a Spring Service responsible for:

//Sending WhatsApp messages

//Sending Emails

//Saving every communication attempt into CommunicationLog table

//Linking communications to a Case (AssistanceCase)...Recording each activity into Audit logs
@Service
public class CommunicationsService {

    private final CommunicationLogRepository repo;
    private final AssistanceCaseRepository caseRepo;
    private final AuditService audit;

    public CommunicationsService(CommunicationLogRepository repo,
                                 AssistanceCaseRepository caseRepo,
                                 AuditService audit) {
        this.repo = repo;//Used to save communication logs:message content,receiver,type (WhatsApp/Email),timestamp,provider IDs
        this.caseRepo = caseRepo;//✔ caseRepo:::Used to fetch the AssistanceCase using caseId.
        this.audit = audit;// audit:::Used to record an audit entry for every communication sent.
    }

    @Transactional//This ensures:If any error happens mid-way → communication log will not be saved and Saves both CommunicationLog + Audit atomically// It ensures data integrity.
    public SendResponse sendWhatsApp(WhatsAppSendRequest req) {
        AssistanceCase linkedCase = null;
        if (req.getCaseId() != null) {
            linkedCase = caseRepo.findById(req.getCaseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));//If case ID exists → fetch case.If not found → throw 404
        }

        CommunicationLog log = new CommunicationLog();
        log.setAssistanceCase(linkedCase);
        log.setType(CommunicationType.WHATSAPP);
        log.setToRecipient(req.getToPhone());
        log.setFromSender("system-whatsapp");
        log.setContent(req.getMessage());

        // Integration stub: here we'd call WhatsApp provider and set providerMessageId/status
        log.setProviderMessageId(simulateProviderMsgId());
        log.setStatus(CommunicationLog.Status.SENT);

        CommunicationLog saved = repo.save(log);//Save in DB

        audit.record("COMM_WHATSAPP_SENT", "CommunicationLog", saved.getId(),
                "to=" + safe(req.getToPhone()) + ", caseId=" + (req.getCaseId() == null ? "" : req.getCaseId()));//Create audit record
        // This writes a line into the audit table showing:What happened,On which table,On which row,With what meta info
//— Return Response DTO
        return new SendResponse(
                saved.getId(),
                saved.getType(),
                saved.getStatus(),
                saved.getProviderMessageId(),
                saved.getAssistanceCase() != null ? saved.getAssistanceCase().getId() : null,
                saved.getToRecipient(),
                saved.getCreatedAt()
        );
    }

    @Transactional
    public SendResponse sendEmail(EmailSendRequest req) {
        AssistanceCase linkedCase = null;
        if (req.getCaseId() != null) {
            linkedCase = caseRepo.findById(req.getCaseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        }

        CommunicationLog log = new CommunicationLog();
        log.setAssistanceCase(linkedCase);
        log.setType(CommunicationType.EMAIL);
        log.setToRecipient(req.getToEmail());
        log.setFromSender("system-email");

        // For demo, combine subject + body/////Build email content:
        String content = "Subject: " + StringUtils.trimToEmpty(req.getSubject())
                + System.lineSeparator()
                + System.lineSeparator()
                + StringUtils.trimToEmpty(req.getBody());
        log.setContent(content);

        // Integration stub: here we'd call SMTP provider and set providerMessageId/status
        log.setProviderMessageId(simulateProviderMsgId());
        log.setStatus(CommunicationLog.Status.SENT);

        CommunicationLog saved = repo.save(log);

        audit.record("COMM_EMAIL_SENT", "CommunicationLog", saved.getId(),
                "to=" + safe(req.getToEmail()) + ", caseId=" + (req.getCaseId() == null ? "" : req.getCaseId()));

        return new SendResponse(
                saved.getId(),
                saved.getType(),
                saved.getStatus(),
                saved.getProviderMessageId(),
                saved.getAssistanceCase() != null ? saved.getAssistanceCase().getId() : null,
                saved.getToRecipient(),
                saved.getCreatedAt()
        );
    }

    private String simulateProviderMsgId() {// Generates a fake message ID for demo/testing.
        long now = System.currentTimeMillis();
        return "MSG-" + now;
    }

    private String safe(String v) {
        return v == null ? "" : v;
    }
}
