package io.github.darlene.waypoint.reminder;

import io.github.darlene.waypoint.common.exception.ResourceNotFoundException;
import io.github.darlene.waypoint.jobapplication.ApplicationStage;
import io.github.darlene.waypoint.jobapplication.JobApplication;
import io.github.darlene.waypoint.reminder.dto.ReminderResponse;
import io.github.darlene.waypoint.reminder.dto.ReminderStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;

    // Create a reminder scheduler
    public void scheduleFollowUp(JobApplication application) {
        reminderRepository.save(Reminder.builder()
                .application(application)
                .reminderType(ReminderType.FOLLOW_UP)
                .dueDate(application.getDateApplied().plusDays(7))
                .build());
    }

    public void onStageEntered(
            JobApplication application,
            ApplicationStage stage,
            LocalDateTime reminderDate) {
        ReminderType reminderType = switch (stage) {
            case OA -> ReminderType.OA_EXPIRY;
            case INTERVIEW -> ReminderType.INTERVIEW;
            default -> null;
        };

        if (reminderType == null) {
            return;
        }

        reminderRepository.save(Reminder.builder()
                .application(application)
                .reminderType(reminderType)
                .dueDate(reminderDate.toLocalDate())
                .build());
    }

    @Transactional
    public void cancelOpenReminders(UUID applicationId) {
        List<Reminder> reminders = reminderRepository.findByApplicationId(applicationId);
        reminders.stream()
                .filter(reminder -> !reminder.isCompleted())
                .forEach(reminder -> reminder.setCompleted(true));
        reminderRepository.saveAll(reminders);
    }

    public List<ReminderResponse> findDueToday() {
        return reminderRepository.findByDueDateAndIsCompletedFalse(LocalDate.now()).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ReminderResponse> findAll() {
        return reminderRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<ReminderResponse> findUpcoming() {
        return reminderRepository.findByDueDateGreaterThanAndIsCompletedFalse(LocalDate.now())
                .stream().map(this::toResponse).toList();
    }

    public List<ReminderResponse> findOverdue() {
        return reminderRepository.findByDueDateLessThanAndIsCompletedFalse(LocalDate.now())
                .stream().map(this::toResponse).toList();
    }

    public ReminderStatsResponse stats() {
        List<Reminder> reminders = reminderRepository.findAll();
        LocalDate today = LocalDate.now();
        return new ReminderStatsResponse(
                reminders.size(),
                reminders.stream().filter(r -> !r.isCompleted() && r.getDueDate().equals(today)).count(),
                reminders.stream().filter(r -> !r.isCompleted() && r.getDueDate().isAfter(today)).count(),
                reminders.stream().filter(r -> !r.isCompleted() && r.getDueDate().isBefore(today)).count(),
                reminders.stream().filter(Reminder::isCompleted).count());
    }

    public ReminderResponse markComplete(UUID reminderId) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reminder not found: " + reminderId));
        reminder.setCompleted(true);
        return toResponse(reminderRepository.save(reminder));
    }

    private ReminderResponse toResponse(Reminder reminder) {
        JobApplication application = reminder.getApplication();
        return new ReminderResponse(
                reminder.getId(),
                application.getId(),
                application.getCompany().getName(),
                application.getPosition(),
                reminder.getReminderType(),
                reminder.getDueDate(),
                reminder.isCompleted(),
                reminder.getNotes());
    }
}
