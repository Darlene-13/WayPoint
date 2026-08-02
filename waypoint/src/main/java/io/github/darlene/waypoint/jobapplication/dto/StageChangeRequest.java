package io.github.darlene.waypoint.jobapplication.dto;

import io.github.darlene.waypoint.jobapplication.ApplicationStage;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record StageChangeRequest(
        @NotNull ApplicationStage newStage,
        LocalDateTime reminderDate,  // required when new Stage is OA, INTERVIEW
        String notes
) {}
