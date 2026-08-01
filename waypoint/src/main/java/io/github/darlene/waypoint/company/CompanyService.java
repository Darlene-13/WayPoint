package com.waypoint.api.company;

import com.waypoint.api.common.exception.ResourceNotFoundException;
import com.waypoint.api.company.dto.CompanyRequest;
import com.waypoint.api.company.dto.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse create(CompanyRequest request) {
        Company company = Company.builder()
                .name(request.name())
                .website(request.website())
                .industry(request.industry())
                .notes(request.notes())
                .build();
        return toResponse(companyRepository.save(company));
    }

    public List<CompanyResponse> findAll() {
        return companyRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CompanyResponse findById(UUID id) {
        return toResponse(getOrThrow(id));
    }

    public CompanyResponse update(UUID id, CompanyRequest request) {
        Company company = getOrThrow(id);
        company.setName(request.name());
        company.setWebsite(request.website());
        company.setIndustry(request.industry());
        company.setNotes(request.notes());
        return toResponse(companyRepository.save(company));
    }

    public void delete(UUID id) {
        companyRepository.delete(getOrThrow(id));
    }

    private Company getOrThrow(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + id));
    }

    private CompanyResponse toResponse(Company c) {
        return new CompanyResponse(c.getId(), c.getName(), c.getWebsite(),
                c.getIndustry(), c.getNotes(), c.getCreatedAt(), c.getUpdatedAt());
    }
}
