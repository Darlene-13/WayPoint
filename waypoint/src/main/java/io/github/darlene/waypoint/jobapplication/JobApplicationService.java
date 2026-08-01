package io.github.darlene.waypoint.jobapplication;

import io.github.darlene.waypoint.common.exception.ResourceNotFoundException;
import io.github.darlene.waypoint.company.Company;
import io.github.darlene.waypoint.company.CompanyRepository;
import io.github.darlene.waypoint.resume.Resume;
import io.github.darlene.waypoint.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.darlene.waypoint.jobapplication.dto.JobApplicationRequest;
import io.github.darlene.waypoint.jobapplication.dto.JobApplicationResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final StageHistoryRepository stageHistoryRepository;
    private final CompanyRepository companyRepository;
    private final ResumeRepository resumeRepository;

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
                .dateApplied(applicationRequest.dateApplied())
                .applicationDeadline(applicationRequest.applicationDeadline())
                .notes(applicationRequest.notes())
                .build();
        return toResponse(jobApplicationRepository.save(application));
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
            UUID id, ApplicationStage newStage, String notes) {
        JobApplication application = getOrThrow(id);
        application.setCurrentStage(newStage);

        stageHistoryRepository.save(StageHistory.builder()
                .application(application)
                .stage(newStage)
                .notes(notes)
                .build());

        return toResponse(jobApplicationRepository.save(application));
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
