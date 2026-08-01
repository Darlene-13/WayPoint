package io.github.darlene.waypoint.dashboard.dto;

public record ResumePerformanceResponse(
        String resumeLabel,
        long totalSent,
        long interviews,
        double interviewRatePct
) {}
