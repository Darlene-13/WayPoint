package io.github.darlene.waypoint.dashboard.dto;

import io.github.darlene.waypoint.jobapplication.ApplicationStage;
import io.github.darlene.waypoint.jobapplication.WorkMode;
import java.time.LocalDate;
import java.util.UUID;


/** Minimal application projection used by dashboard cards and tables. */
public record DashboardApplicationResponse(
        UUID id,
        UUID companyId,
        String companyName,
        String position,
        ApplicationStage stage,
        String location,
        WorkMode workMode,
        LocalDate dateApplied,
        LocalDate applicationDeadline,
        LocalDate nextFollowUpDate
) {}
