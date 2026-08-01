package io.github.darlene.waypoint.jobapplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

    List<JobApplication> findByCurrentStage(ApplicationStage stage);

    List<JobApplication> findByDateAppliedGreaterThanEqual(LocalDate since);

    @Query("select a.currentStage as stage, count(a) as total " +
           "from JobApplication a group by a.currentStage")
    List<StageCountProjection> countByStage();

    interface StageCountProjection {
        ApplicationStage getStage();
        Long getTotal();
    }
}
