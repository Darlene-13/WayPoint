package io.github.darlene.waypoint.contact;

import io.github.darlene.waypoint.contact.dto.ContactRequest;
import io.github.darlene.waypoint.contact.dto.ContactResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse create(@Valid @RequestBody ContactRequest request) {
        return contactService.create(request);
    }

    @GetMapping(params = "companyId")
    public List<ContactResponse> findByCompany(@RequestParam UUID companyId) {
        return contactService.findByCompany(companyId);
    }

    @GetMapping("/{id}")
    public ContactResponse findById(@PathVariable UUID id) {
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        contactService.delete(id);
    }
}
