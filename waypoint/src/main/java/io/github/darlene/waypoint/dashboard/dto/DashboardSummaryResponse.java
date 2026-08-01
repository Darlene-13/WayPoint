package io.github.darlene.waypoint.dashboard.dto;

import io.github.darlene.waypoint.jobapplication.ApplicationStage;

import java.util.List;

public record DashboardSummaryResponse(
        long totalApplications,
        List<StageCountResponse> countsByStage,
        double responseRatePct,
        double successRatePct,
        long followUpsDueToday
) {}
