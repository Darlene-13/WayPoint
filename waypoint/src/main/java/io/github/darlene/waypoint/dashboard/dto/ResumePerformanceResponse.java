package com.waypoint.api.dashboard.dto;

public record ResumePerformanceResponse(
        String resumeLabel,
        long totalSent,
        long interviews,
        double interviewRatePct
) {}
