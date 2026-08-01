package com.waypoint.api.jobapplication.dto;

import com.waypoint.api.jobapplication.ApplicationStage;
import com.waypoint.api.jobapplication.WorkMode;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record JobApplicationResponse(
        UUID id,
        UUID companyId,
        String companyName,
        UUID resumeId,
        String resumeLabel,
        String position,
        String location,
        WorkMode workMode,
        BigDecimal salaryMin,
        BigDecimal salaryMax,
        String salaryCurrency,
        String jobUrl,
        LocalDate dateApplied,
        LocalDate applicationDeadline,
        ApplicationStage currentStage,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
