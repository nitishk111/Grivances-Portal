package io.github.nitishc.grievance.user_service.repository;

import io.github.nitishc.grievance.user_service.model.Officer;
import io.github.nitishc.grievance.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficerRepository extends JpaRepository<Officer, Long> {

    Officer findByEmail(String email);
}
