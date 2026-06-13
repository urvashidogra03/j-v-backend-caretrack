package com.caretrack.call.controller;

import com.caretrack.call.dto.CallInitiateRequest;
import com.caretrack.call.dto.CallInitiateResponse;
import com.caretrack.call.service.CallService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * VoIP Call APIs:
 * - POST /api/calls/initiate
 */
@RestController
@RequestMapping("/api/calls")
public class CallController {

    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    /**
     * POST /api/calls/initiate
     * Initiates a call (stub), logs it, and records an audit event.
     * Roles: OPS, MANAGER, ADMIN
     */
    @PostMapping("/initiate")
    @PreAuthorize("hasAnyRole('OPS','MANAGER','ADMIN')")
    public ResponseEntity<CallInitiateResponse> initiate(@Valid @RequestBody CallInitiateRequest req) {
        CallInitiateResponse resp = callService.initiate(req);
        return ResponseEntity.ok(resp);
    }
}
