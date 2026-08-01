package com.waypoint.api.reminder.dto;

import com.waypoint.api.reminder.ReminderType;

import java.time.LocalDate;
import java.util.UUID;

public record ReminderResponse(
        UUID id,
        UUID applicationId,
        String companyName,
        String position,
        ReminderType reminderType,
        LocalDate dueDate,
        boolean isCompleted,
        String notes
) {}
