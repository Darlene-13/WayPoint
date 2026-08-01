package com.waypoint.api.dashboard.dto;

import java.time.LocalDate;

public record WeeklyCountResponse(
        LocalDate weekStarting,
        long applicationsSent
) {}
