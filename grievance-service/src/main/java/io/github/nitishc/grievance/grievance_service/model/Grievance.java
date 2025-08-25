package io.github.nitishc.grievance.grievance_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grievance_register")
public class Grievance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long grievanceId;

    @Column(nullable = false)
    private long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department grievanceType;

    @Column(nullable = false)
    private String complaintTitle;

    @Column(nullable = false)
    private String complaintDescription;

    @JsonIgnore
    @OneToOne(mappedBy = "grievance")
    @JoinColumn(nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "ENUM('NEW', 'IN_PROGRESS', 'RESOLVED'," +
            " 'REJECTED', 'ESCALATED')")
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "ENUM('LOW', 'MEDIUM', 'HIGH')")
    private Priority priority = Priority.LOW;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private final LocalDate createdAt = LocalDate.now();

    private LocalDate lastUpdate = LocalDate.now();

    @JsonIgnore
    @OneToMany(mappedBy = "grievance")
    private List<Comment> comments = new ArrayList<>();


    public Grievance(Department department, String complaintTitle, String complaintDescription, Address address) {

        this.grievanceType = department;
        this.complaintTitle= complaintTitle;
        this.complaintDescription=complaintDescription;
        this.address=address;
    }
}
