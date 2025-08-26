package io.github.nitishc.grievance.user_service.dto;

import io.github.nitishc.grievance.user_service.model.Department;
import io.github.nitishc.grievance.user_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficerResponse {

    private String fullName;

    private String email;

    private String phone;

    private Role role;

    private Department department;
}
