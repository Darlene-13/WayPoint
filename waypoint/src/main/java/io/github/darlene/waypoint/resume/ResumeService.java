package io.github.darlene.waypoint.resume;

import io.github.darlene.waypoint.common.exception.ResourceNotFoundException;
import io.github.darlene.waypoint.resume.dto.ResumeRequest;
import io.github.darlene.waypoint.resume.dto.ResumeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final Path uploadDirectory = Paths.get("uploads", "resumes");

    public ResumeResponse upload(String label, String targetRole, MultipartFile file) {
        try {
            Files.createDirectories(uploadDirectory);
            String storedName = UUID.randomUUID() + "-" + Path.of(file.getOriginalFilename()).getFileName();
            Files.copy(file.getInputStream(), uploadDirectory.resolve(storedName), StandardCopyOption.REPLACE_EXISTING);
            Resume resume = Resume.builder().label(label).targetRole(targetRole).fileName(file.getOriginalFilename())
                    .contentType(file.getContentType()).fileUrl("/api/resumes/files/" + storedName).build();
            return toResponse(resumeRepository.save(resume));
        } catch (IOException ex) { throw new IllegalArgumentException("Resume file could not be stored", ex); }
    }

    public Resource file(String storedName) {
        try { Path path = uploadDirectory.toAbsolutePath().resolve(storedName).normalize();
            if (!path.startsWith(uploadDirectory.toAbsolutePath().normalize())) throw new IllegalArgumentException("Invalid file path");
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()) throw new ResourceNotFoundException("Resume file not found");
            return resource;
        } catch (Exception ex) { if (ex instanceof ResourceNotFoundException r) throw r; throw new IllegalArgumentException("Resume file could not be read", ex); }
    }

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
        return new ResumeResponse(r.getId(), r.getLabel(), r.getTargetRole(), r.getFileUrl(), r.getFileName(), r.getContentType(), r.getCreatedAt());
    }
}
