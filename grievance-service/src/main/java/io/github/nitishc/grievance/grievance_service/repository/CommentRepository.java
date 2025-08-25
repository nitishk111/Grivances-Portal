package io.github.nitishc.grievance.grievance_service.repository;

import io.github.nitishc.grievance.grievance_service.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
