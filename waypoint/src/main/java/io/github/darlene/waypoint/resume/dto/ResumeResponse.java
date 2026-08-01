package io.github.darlene.waypoint.resume.dto;

import java.time.Instant;
import java.util.UUID;

public record ResumeResponse(
        UUID id,
        String label,
        String targetRole,
        String fileUrl,
        Instant createdAt
) {}
