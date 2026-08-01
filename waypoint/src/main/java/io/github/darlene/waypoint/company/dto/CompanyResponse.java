package io.github.darlene.waypoint.company.dto;

import java.time.Instant;
import java.util.UUID;

// What is gives me back as a user or FE
public record CompanyResponse(
        UUID id,
        String name,
        String website,
        String industry,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
