package io.github.darlene.waypoint.resume;

import io.github.darlene.waypoint.resume.dto.CoverLetterRequest;
import io.github.darlene.waypoint.resume.dto.CoverLetterResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/** Skeleton for the future AI cover-letter workflow. */
@Service
public class CoverLetterService {
    public CoverLetterResponse generate(UUID resumeId, CoverLetterRequest request) {
        throw new UnsupportedOperationException("AI cover-letter generation is not implemented yet");
    }
    public List<CoverLetterResponse> findByResume(UUID resumeId) {
        throw new UnsupportedOperationException("Cover-letter persistence is not implemented yet");
    }
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Cover-letter persistence is not implemented yet");
    }
}
