package com.waypoint.api.company.dto;

import java.time.Instant;
import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String name,
        String website,
        String industry,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
