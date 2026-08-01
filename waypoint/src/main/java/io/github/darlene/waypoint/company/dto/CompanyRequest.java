package com.waypoint.api.company.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
        @NotBlank String name,
        String website,
        String industry,
        String notes
) {}
