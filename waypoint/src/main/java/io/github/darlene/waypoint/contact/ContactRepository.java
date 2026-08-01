package io.github.darlene.waypoint.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findByCompanyId(UUID companyId);
}
