package io.github.nitishc.grievance.grievance_service.model;

import io.github.nitishc.grievance.grievance_service.dto.CommentRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String commentText;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private final LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Grievance grievance;

    public Comment(String userEmail, Grievance grievance, CommentRequest commentRequest) {

        this.userEmail=userEmail;
        this.grievance = grievance;
        this.commentText= commentRequest.getCommentText();
    }
}
