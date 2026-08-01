package io.github.darlene.waypoint.resume;

import io.github.darlene.waypoint.resume.dto.ResumeRequest;
import io.github.darlene.waypoint.resume.dto.ResumeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResumeResponse create(@Valid @RequestBody ResumeRequest request) {
        return resumeService.create(request);
    }

    @GetMapping
    public List<ResumeResponse> findAll() {
        return resumeService.findAll();
    }

    @GetMapping("/{id}")
    public ResumeResponse findById(@PathVariable UUID id) {
        return resumeService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        resumeService.delete(id);
    }
}
