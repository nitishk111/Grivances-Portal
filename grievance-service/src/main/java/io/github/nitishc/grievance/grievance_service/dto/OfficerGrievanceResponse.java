package io.github.nitishc.grievance.grievance_service.dto;

import io.github.nitishc.grievance.grievance_service.model.Address;
import io.github.nitishc.grievance.grievance_service.model.Comment;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OfficerGrievanceResponse {

    private long grievanceId;

    private String userEmail;

    private Department grievanceType;

    private String complaintTitle;

    private String complaintDescription;

    private Address address;

    private Status status;

    private Priority priority;

    private LocalDate createdAt;

    private LocalDate lastUpdate;

    private List<Comment> comments;
}
