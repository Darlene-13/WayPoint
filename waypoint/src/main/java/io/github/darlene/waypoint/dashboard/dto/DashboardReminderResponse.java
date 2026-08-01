package io.github.darlene.waypoint.dashboard.dto;

import io.github.darlene.waypoint.reminder.ReminderType;
import java.time.LocalDate;
import java.util.UUID;

public record DashboardReminderResponse(
        UUID id,
        UUID applicationId,
        String companyName,
        String position,
        ReminderType type,
        LocalDate dueDate,
        boolean completed,
        String notes
) {}
