package com.waypoint.api.jobapplication.dto;

import com.waypoint.api.jobapplication.ApplicationStage;
import jakarta.validation.constraints.NotNull;

public record StageChangeRequest(
        @NotNull ApplicationStage newStage,
        String notes
) {}
