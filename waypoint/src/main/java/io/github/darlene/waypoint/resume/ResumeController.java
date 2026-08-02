package io.github.darlene.waypoint.resume;

import io.github.darlene.waypoint.resume.dto.ResumeRequest;
import io.github.darlene.waypoint.resume.dto.ResumeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ResumeResponse upload(@RequestParam String label, @RequestParam(required = false) String targetRole,
                                 @RequestPart MultipartFile file) { return resumeService.upload(label, targetRole, file); }

    @GetMapping("/files/{storedName:.+}")
    public ResponseEntity<Resource> file(@PathVariable String storedName) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resumeService.file(storedName));
    }

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
