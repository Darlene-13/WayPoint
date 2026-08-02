package io.github.darlene.waypoint.resume.dto;

import java.time.Instant;
import java.util.UUID;

public record CoverLetterResponse(UUID id, UUID resumeId, UUID applicationId, String content, Instant createdAt) {}
