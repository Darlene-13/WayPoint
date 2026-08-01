package io.github.darlene.waypoint.jobapplication.dto;

import io.github.darlene.waypoint.jobapplication.WorkMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record JobApplicationRequest(
        @NotNull UUID companyId,
        UUID resumeId,
        @NotBlank String position,
        String location,
        WorkMode workMode,
        BigDecimal salaryMin,
        BigDecimal salaryMax,
        String salaryCurrency,
        String jobUrl,
        LocalDate dateApplied,
        LocalDate applicationDeadline,
        String notes
) {}
