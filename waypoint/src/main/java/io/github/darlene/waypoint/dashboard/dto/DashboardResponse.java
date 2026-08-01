package io.github.darlene.waypoint.dashboard.dto;

import java.util.List;

/** Complete dashboard payload; keeps the frontend to one request. */
public record DashboardResponse(
        DashboardSummaryResponse summary,
        List<WeeklyCountResponse> weeklyApplications,
        List<ResumePerformanceResponse> resumePerformance,
        List<DashboardApplicationResponse> recentApplications,
        List<DashboardReminderResponse> upcomingReminders
) {}
