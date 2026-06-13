package com.caretrack.invoice.controller;

import com.caretrack.invoice.dto.InvoiceCreateRequest;
import com.caretrack.invoice.dto.InvoiceResponse;
import com.caretrack.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Invoicing APIs:
 * - POST /api/invoices
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * POST /api/invoices
     * Create a new invoice (optionally linked to a case).
     * Roles: MANAGER, ADMIN
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceCreateRequest req) {
        InvoiceResponse created = invoiceService.create(req);
        return ResponseEntity.ok(created);
    }
}
