package io.github.darlene.waypoint.jobapplication;

import io.github.darlene.waypoint.jobapplication.dto.JobApplicationRequest;
import io.github.darlene.waypoint.jobapplication.dto.JobApplicationResponse;
import io.github.darlene.waypoint.jobapplication.dto.StageChangeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController{

    private final JobApplicationService jobApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse create(
            @Valid @RequestBody JobApplicationRequest jobApplicationRequest) {
        return jobApplicationService.create(jobApplicationRequest);
    }

    @GetMapping
    public List<JobApplicationResponse> findAll(
            @RequestParam(required = false) ApplicationStage stage) {
        return jobApplicationService.findAll(stage);
    }

    @GetMapping("/{id}")
    public JobApplicationResponse findById(@PathVariable UUID id) {
        return jobApplicationService.findById(id);
    }

    @PatchMapping("/{id}/stage")
    public JobApplicationResponse changeStage(
            @PathVariable UUID id,
            @Valid @RequestBody StageChangeRequest request) {
        return jobApplicationService.changeStage(id, request.newStage(), request.notes());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        jobApplicationService.delete(id);
    }
}
