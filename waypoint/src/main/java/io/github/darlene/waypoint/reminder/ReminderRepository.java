package com.waypoint.api.reminder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, UUID> {
    List<Reminder> findByDueDateAndIsCompletedFalse(LocalDate dueDate);
    List<Reminder> findByApplicationId(UUID applicationId);
}
