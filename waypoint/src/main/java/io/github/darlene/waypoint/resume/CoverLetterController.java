package io.github.darlene.waypoint.resume;

import io.github.darlene.waypoint.resume.dto.CoverLetterRequest;
import io.github.darlene.waypoint.resume.dto.CoverLetterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes/{resumeId}/cover-letters")
@RequiredArgsConstructor
public class CoverLetterController {
    private final CoverLetterService coverLetterService;

    @PostMapping
    public CoverLetterResponse generate(@PathVariable UUID resumeId, @Valid @RequestBody CoverLetterRequest request) {
        return coverLetterService.generate(resumeId, request);
    }
    @GetMapping
    public List<CoverLetterResponse> findByResume(@PathVariable UUID resumeId) {
        return coverLetterService.findByResume(resumeId);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { coverLetterService.delete(id); }
}
