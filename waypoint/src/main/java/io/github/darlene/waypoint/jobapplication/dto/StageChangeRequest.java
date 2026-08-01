package io.github.darlene.waypoint.jobapplication.dto;

import io.github.darlene.waypoint.jobapplication.ApplicationStage;
import jakarta.validation.constraints.NotNull;

public record StageChangeRequest(
        @NotNull ApplicationStage newStage,
        String notes
) {}
