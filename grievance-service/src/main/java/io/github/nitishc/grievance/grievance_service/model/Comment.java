package io.github.nitishc.grievance.grievance_service.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String commentText;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private final LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Grievance grievance;

    public Comment(long userId, Grievance grievance, String commentText) {
        this.userId= userId;
        this.grievance = grievance;
        this.commentText= commentText;
    }
}
