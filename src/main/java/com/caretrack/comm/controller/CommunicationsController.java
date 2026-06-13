package com.caretrack.comm.controller;

import com.caretrack.comm.dto.EmailSendRequest;
import com.caretrack.comm.dto.SendResponse;
import com.caretrack.comm.dto.WhatsAppSendRequest;
import com.caretrack.comm.service.CommunicationsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Communications APIs:
 * - POST /api/communications/whatsapp/send
 * - POST /api/communications/email/send
 *
 * These endpoints persist communication logs and record audit events.
 * External provider integrations can be plugged into CommunicationsService later.
 */
@RestController
@RequestMapping("/api/communications")
public class CommunicationsController {

    private final CommunicationsService communicationsService;

    public CommunicationsController(CommunicationsService communicationsService) {
        this.communicationsService = communicationsService;
    }

    /**
     * POST /api/communications/whatsapp/send
     * Send a WhatsApp message (integration stub) and persist audit/log entries.
     * Roles: OPS, MANAGER, ADMIN
     */
    @PostMapping("/whatsapp/send")
    @PreAuthorize("hasAnyRole('OPS','MANAGER','ADMIN')")
    public ResponseEntity<SendResponse> sendWhatsApp(@Valid @RequestBody WhatsAppSendRequest req) {
        SendResponse response = communicationsService.sendWhatsApp(req);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/communications/email/send
     * Send an email (integration stub) and persist audit/log entries.
     * Roles: OPS, MANAGER, ADMIN
     */
    @PostMapping("/email/send")
    @PreAuthorize("hasAnyRole('OPS','MANAGER','ADMIN')")
    public ResponseEntity<SendResponse> sendEmail(@Valid @RequestBody EmailSendRequest req) {
        SendResponse response = communicationsService.sendEmail(req);
        return ResponseEntity.ok(response);
    }
}
