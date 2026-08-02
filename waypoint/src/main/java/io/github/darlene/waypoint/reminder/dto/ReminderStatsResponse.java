package io.github.darlene.waypoint.reminder.dto;

public record ReminderStatsResponse(long total, long dueToday, long upcoming, long overdue, long completed) {}
