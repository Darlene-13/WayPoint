package com.waypoint.api.contact;

import com.waypoint.api.common.exception.ResourceNotFoundException;
import com.waypoint.api.company.Company;
import com.waypoint.api.company.CompanyRepository;
import com.waypoint.api.contact.dto.ContactRequest;
import com.waypoint.api.contact.dto.ContactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    public ContactResponse create(ContactRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + request.companyId()));
        Contact contact = Contact.builder()
                .company(company)
                .name(request.name())
                .role(request.role())
                .email(request.email())
                .linkedinUrl(request.linkedinUrl())
                .notes(request.notes())
                .build();
        return toResponse(contactRepository.save(contact));
    }

    public List<ContactResponse> findByCompany(UUID companyId) {
        return contactRepository.findByCompanyId(companyId).stream().map(this::toResponse).toList();
    }

    public ContactResponse findById(UUID id) {
        return toResponse(getOrThrow(id));
    }

    public void delete(UUID id) {
        contactRepository.delete(getOrThrow(id));
    }

    private Contact getOrThrow(UUID id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found: " + id));
    }

    private ContactResponse toResponse(Contact c) {
        return new ContactResponse(c.getId(), c.getCompany().getId(), c.getCompany().getName(),
                c.getName(), c.getRole(), c.getEmail(), c.getLinkedinUrl(), c.getNotes(), c.getCreatedAt());
    }
}
