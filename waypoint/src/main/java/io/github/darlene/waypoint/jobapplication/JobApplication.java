package io.github.darlene.waypoint.jobapplication;

import io.github.darlene.waypoint.company.Company;
import io.github.darlene.waypoint.resume.Resume;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(nullable = false)
    private String position;

    private String location;

    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    @Builder.Default
    private String salaryCurrency = "KES";

    private String jobUrl;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate dateApplied = LocalDate.now();

    private LocalDate applicationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ApplicationStage currentStage = ApplicationStage.APPLIED;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
