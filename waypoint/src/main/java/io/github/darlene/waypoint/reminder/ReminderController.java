package io.github.darlene.waypoint.reminder;

import io.github.darlene.waypoint.reminder.dto.ReminderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping("/due-today")
    public List<ReminderResponse> findDueToday() {
        return reminderService.findDueToday();
    }

    @PatchMapping("/{id}/complete")
    public ReminderResponse markComplete(@PathVariable UUID id) {
        return reminderService.markComplete(id);
    }
}
