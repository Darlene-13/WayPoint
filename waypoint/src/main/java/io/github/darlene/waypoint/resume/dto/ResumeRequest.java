package io.github.darlene.waypoint.resume.dto;

import jakarta.validation.constraints.NotBlank;

public record ResumeRequest(
        @NotBlank String label,
        String targetRole,
        String fileUrl
) {}
