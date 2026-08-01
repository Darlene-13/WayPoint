package io.github.darlene.waypoint.dashboard.dto;

import java.time.LocalDate;

public record WeeklyCountResponse(
        LocalDate weekStarting,
        long applicationsSent
) {}
