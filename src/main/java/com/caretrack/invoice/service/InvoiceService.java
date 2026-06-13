package com.caretrack.invoice.service;

import com.caretrack.audit.service.AuditService;
import com.caretrack.cases.model.AssistanceCase;
import com.caretrack.cases.repo.AssistanceCaseRepository;
import com.caretrack.invoice.dto.InvoiceCreateRequest;
import com.caretrack.invoice.dto.InvoiceResponse;
import com.caretrack.invoice.model.Invoice;
import com.caretrack.invoice.repo.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final AssistanceCaseRepository caseRepository;
    private final AuditService audit;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          AssistanceCaseRepository caseRepository,
                          AuditService audit) {
        this.invoiceRepository = invoiceRepository;
        this.caseRepository = caseRepository;
        this.audit = audit;
    }

    @Transactional
    public InvoiceResponse create(InvoiceCreateRequest req) {
        AssistanceCase linkedCase = null;
        if (req.getCaseId() != null) {
            linkedCase = caseRepository.findById(req.getCaseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        }

        Invoice inv = new Invoice();
        inv.setAssistanceCase(linkedCase);
        inv.setAmount(req.getAmount());
        if (StringUtils.isNotBlank(req.getCurrency())) {
            inv.setCurrency(req.getCurrency().trim());
        }

        Invoice saved = invoiceRepository.save(inv);

        // Generate invoice number on first save to get db id
        if (StringUtils.isBlank(saved.getInvoiceNumber())) {
            saved.setInvoiceNumber(generateInvoiceNumber(saved.getId()));
            saved = invoiceRepository.save(saved);
        }

        audit.record("INVOICE_CREATED", "Invoice", saved.getId(),
                "amount=" + saved.getAmount() + " " + saved.getCurrency() + ", caseId=" + (saved.getAssistanceCase() != null ? saved.getAssistanceCase().getId() : ""));

        return toResponse(saved);
    }

    private String generateInvoiceNumber(Long id) {
        String date = LocalDate.now().toString().replace("-", "");
        String idPart = String.format("%06d", id == null ? 0 : id);
        return "INV-" + date + "-" + idPart;
    }

    private InvoiceResponse toResponse(Invoice inv) {
        Long caseId = inv.getAssistanceCase() != null ? inv.getAssistanceCase().getId() : null;
        return new InvoiceResponse(
                inv.getId(),
                caseId,
                inv.getInvoiceNumber(),
                inv.getAmount(),
                inv.getCurrency(),
                inv.getStatus(),
                inv.getCreatedAt()
        );
    }
}
