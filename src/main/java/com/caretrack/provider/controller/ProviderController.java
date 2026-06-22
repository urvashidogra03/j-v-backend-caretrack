package com.caretrack.provider.controller;


import com.caretrack.provider.dto.NearbyProviderResponse;
import com.caretrack.provider.dto.ProviderCreateRequest;
import com.caretrack.provider.dto.ProviderResponse;
import com.caretrack.provider.model.ProviderType;
import com.caretrack.provider.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * POST /api/providers
     * Create a provider record.
     * Roles: ADMIN, MANAGER, OPS
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPS')")
    public ResponseEntity<ProviderResponse> create(@Valid @RequestBody ProviderCreateRequest req) {
        ProviderResponse created = providerService.createProvider(req);
        return ResponseEntity.ok(created);
    }

    /**
     * GET /api/providers/nearby
     * Query nearby providers given coordinates, optional radius, and optional type filter.
     * Auth: any authenticated user
     *
     * Example:
     *   /api/providers/nearby?lat=19.0760&lon=72.8777&radiusKm=15&type=HOSPITAL
     */
    @GetMapping("/nearby")
    public ResponseEntity< List<NearbyProviderResponse>> nearby(
            @RequestParam("lat") BigDecimal lat,
            @RequestParam("lon") BigDecimal lon,
            @RequestParam(value = "radiusKm", required = false) Double radiusKm,
            @RequestParam(value = "type", required = false) ProviderType type
    ) {
        List<NearbyProviderResponse> results = providerService.findNearbyProviders(lat, lon, radiusKm, type);
        return ResponseEntity.ok(results);
      }

    }

