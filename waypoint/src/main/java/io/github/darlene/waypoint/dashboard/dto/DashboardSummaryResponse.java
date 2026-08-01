package com.waypoint.api.dashboard.dto;

import com.waypoint.api.jobapplication.ApplicationStage;

import java.util.Map;

public record DashboardSummaryResponse(
        long totalApplications,
        Map<ApplicationStage, Long> countsByStage,
        double responseRatePct,
        double successRatePct,
        long followUpsDueToday
) {}
