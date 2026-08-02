package io.github.darlene.waypoint.jobapplication;

import io.github.darlene.waypoint.common.exception.ResourceNotFoundException;
import io.github.darlene.waypoint.common.exception.InvalidStageTransitionException;
import io.github.darlene.waypoint.company.Company;
import io.github.darlene.waypoint.company.CompanyRepository;
import io.github.darlene.waypoint.resume.Resume;
import io.github.darlene.waypoint.resume.ResumeRepository;
import io.github.darlene.waypoint.reminder.ReminderService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.darlene.waypoint.jobapplication.dto.JobApplicationRequest;
import io.github.darlene.waypoint.jobapplication.dto.JobApplicationResponse;
import io.github.darlene.waypoint.jobapplication.dto.StageChangeRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final StageHistoryRepository stageHistoryRepository;
    private final CompanyRepository companyRepository;
    private final ResumeRepository resumeRepository;
    private final ReminderService reminderService;

    @Transactional
    public JobApplicationResponse create(JobApplicationRequest applicationRequest) {
        Company company = companyRepository.findById(applicationRequest.companyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found: " + applicationRequest.companyId()));

        Resume resume = applicationRequest.resumeId() == null
                ? null
                : resumeRepository.findById(applicationRequest.resumeId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Resume not found: " + applicationRequest.resumeId()));

        JobApplication application = JobApplication.builder()
                .company(company)
                .resume(resume)
                .position(applicationRequest.position())
                .location(applicationRequest.location())
                .workMode(applicationRequest.workMode())
                .salaryMin(applicationRequest.salaryMin())
                .salaryMax(applicationRequest.salaryMax())
                .salaryCurrency(applicationRequest.salaryCurrency())
                .jobUrl(applicationRequest.jobUrl())
                .dateApplied(applicationRequest.dateApplied() == null
                        ? LocalDate.now()
                        : applicationRequest.dateApplied())
                .applicationDeadline(applicationRequest.applicationDeadline())
                .notes(applicationRequest.notes())
                .build();
        JobApplication savedApplication = jobApplicationRepository.save(application);
        reminderService.scheduleFollowUp(savedApplication);
        return toResponse(savedApplication);
    }

    /**
     * Returns every application when stage is absent, or only applications in
     * the requested stage when it is supplied as a query parameter.
     */
    public List<JobApplicationResponse> findAll(ApplicationStage stage) {
        List<JobApplication> applications = stage == null
                ? jobApplicationRepository.findAll()
                : jobApplicationRepository.findByCurrentStage(stage);

        return applications.stream().map(this::toResponse).toList();
    }

    public JobApplicationResponse findById(UUID id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public JobApplicationResponse changeStage(
            UUID id, StageChangeRequest request) {
        JobApplication application = getOrThrow(id);
        // Get the current stage of the application
        ApplicationStage newStage = getNewStage(request, application);

        application.setCurrentStage(newStage);

        stageHistoryRepository.save(StageHistory.builder()
                .application(application)
                .stage(newStage)
                .notes(request.notes())
                .build());

        if (newStage == ApplicationStage.REJECTED
                || newStage == ApplicationStage.WITHDRAWN
                || newStage == ApplicationStage.GHOSTED) {
            reminderService.cancelOpenReminders(id);
        } else if (newStage == ApplicationStage.OA
                || newStage == ApplicationStage.INTERVIEW) {
            reminderService.onStageEntered(application, newStage, request.reminderDate());
        }

        return toResponse(jobApplicationRepository.save(application));
    }

    private @NonNull ApplicationStage getNewStage(StageChangeRequest request, JobApplication application) {
        ApplicationStage currentStage = application.getCurrentStage();
        ApplicationStage newStage = request.newStage();

        if (!isAllowedTransition(currentStage, newStage)) {
            throw new InvalidStageTransitionException(
                    "Cannot move from " + currentStage + " to " + newStage);
        }
        if ((newStage == ApplicationStage.OA || newStage == ApplicationStage.INTERVIEW)
                && request.reminderDate() == null) {
            throw new IllegalArgumentException(
                    "reminderDate is required when moving to " + newStage);
        }
        return newStage;
    }


    // Take care of stage transition
    private boolean isAllowedTransition(ApplicationStage currentStage, ApplicationStage newStage) {
        if (currentStage == null || newStage == null || currentStage == newStage) {
            return false;
        }
        // Terminal outcomes may be selected from any active stage, but cannot be changed again.
        if (newStage == ApplicationStage.REJECTED
                || newStage == ApplicationStage.WITHDRAWN
                || newStage == ApplicationStage.GHOSTED) {
            return currentStage != ApplicationStage.REJECTED
                    && currentStage != ApplicationStage.WITHDRAWN
                    && currentStage != ApplicationStage.GHOSTED;
        }
        return switch (currentStage) {
            case APPLIED -> newStage == ApplicationStage.OA;
            case OA -> newStage == ApplicationStage.INTERVIEW;
            case INTERVIEW -> newStage == ApplicationStage.OFFER;
            case OFFER, REJECTED, WITHDRAWN, GHOSTED -> false;
        };
    }

    public void delete(UUID id) {
        jobApplicationRepository.delete(getOrThrow(id));
    }

    private JobApplication getOrThrow(UUID id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job application not found: " + id));
    }

    private JobApplicationResponse toResponse(JobApplication application) {
        Resume resume = application.getResume();
        return new JobApplicationResponse(
                application.getId(),
                application.getCompany().getId(),
                application.getCompany().getName(),
                resume == null ? null : resume.getId(),
                resume == null ? null : resume.getLabel(),
                application.getPosition(),
                application.getLocation(),
                application.getWorkMode(),
                application.getSalaryMin(),
                application.getSalaryMax(),
                application.getSalaryCurrency(),
                application.getJobUrl(),
                application.getDateApplied(),
                application.getApplicationDeadline(),
                application.getCurrentStage(),
                application.getNotes(),
                application.getCreatedAt(),
                application.getUpdatedAt());
    }
}
