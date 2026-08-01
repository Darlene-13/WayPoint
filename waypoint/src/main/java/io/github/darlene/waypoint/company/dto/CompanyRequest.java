package io.github.darlene.waypoint.company.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
        @NotBlank String name,
        String website,
        String industry,
        String notes
) {}
