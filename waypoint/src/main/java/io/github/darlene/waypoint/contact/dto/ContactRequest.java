package com.waypoint.api.contact.dto;

import com.waypoint.api.contact.ContactRole;
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
