package com.waypoint.api.jobapplication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "application_stage_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageHistory {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private JobApplication application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStage stage;

    @Column(nullable = false)
    @Builder.Default
    private Instant changedAt = Instant.now();

    @Column(columnDefinition = "TEXT")
    private String notes;
}
