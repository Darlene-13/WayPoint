package io.github.darlene.waypoint.resume;

import io.github.darlene.waypoint.common.exception.ResourceNotFoundException;
import io.github.darlene.waypoint.resume.dto.ResumeRequest;
import io.github.darlene.waypoint.resume.dto.ResumeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeResponse create(ResumeRequest request) {
        Resume resume = Resume.builder()
                .label(request.label())
                .targetRole(request.targetRole())
                .fileUrl(request.fileUrl())
                .build();
        return toResponse(resumeRepository.save(resume));
    }

    public List<ResumeResponse> findAll() {
        return resumeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ResumeResponse findById(UUID id) {
        return toResponse(getOrThrow(id));
    }

    public void delete(UUID id) {
        resumeRepository.delete(getOrThrow(id));
    }

    private Resume getOrThrow(UUID id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found: " + id));
    }

    private ResumeResponse toResponse(Resume r) {
        return new ResumeResponse(r.getId(), r.getLabel(), r.getTargetRole(), r.getFileUrl(), r.getCreatedAt());
    }
}
