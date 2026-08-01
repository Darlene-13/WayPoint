package io.github.darlene.waypoint.contact.dto;

import io.github.darlene.waypoint.contact.ContactRole;

import java.time.Instant;
import java.util.UUID;

public record ContactResponse(
        UUID id,
        UUID companyId,
        String companyName,
        String name,
        ContactRole role,
        String email,
        String linkedinUrl,
        String notes,
        Instant createdAt
) {}
