package io.github.darlene.waypoint.resume.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CoverLetterRequest(@NotBlank String jobDescription, UUID resumeId, String tone) {}
