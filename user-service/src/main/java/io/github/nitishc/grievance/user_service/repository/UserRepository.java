package io.github.nitishc.grievance.user_service.repository;

import io.github.nitishc.grievance.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);
}
