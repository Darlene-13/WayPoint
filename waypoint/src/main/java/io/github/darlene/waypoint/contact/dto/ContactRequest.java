package io.github.darlene.waypoint.contact.dto;

import io.github.darlene.waypoint.contact.ContactRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContactRequest(
        @NotNull UUID companyId,
        @NotBlank String name,
        @NotNull ContactRole role,
        String email,
        String linkedinUrl,
        String notes
) {}
