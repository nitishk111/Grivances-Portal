package io.github.nitishc.grievance.grievance_service.dto;

import io.github.nitishc.grievance.grievance_service.model.Address;
import io.github.nitishc.grievance.grievance_service.util.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrievanceRequest {

    private Department grievanceType;

    private String complaintTitle;

    private String complaintDescription;

    private Address address;

}
