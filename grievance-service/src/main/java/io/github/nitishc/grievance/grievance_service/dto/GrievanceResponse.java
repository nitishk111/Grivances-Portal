package io.github.nitishc.grievance.grievance_service.dto;

import io.github.nitishc.grievance.grievance_service.model.Address;
import io.github.nitishc.grievance.grievance_service.model.Comment;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class GrievanceResponse {

    private long grievanceId;

    private String complaintTitle;

    private String complaintDescription;

    private Address address;

    private Status status;

    private Priority priority;

    private LocalDate createdAt;

    private LocalDate lastUpdate;

    private List<Comment> comments;
}
