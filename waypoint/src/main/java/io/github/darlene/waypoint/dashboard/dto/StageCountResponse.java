package io.github.darlene.waypoint.dashboard.dto;

import io.github.darlene.waypoint.jobapplication.ApplicationStage;

/** A chart-friendly application count for one pipeline stage. */
public record StageCountResponse(ApplicationStage stage, long count) {}
