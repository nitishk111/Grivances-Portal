package io.github.nitishc.grievance.user_service.dto;

import io.github.nitishc.grievance.user_service.util.Department;
import io.github.nitishc.grievance.user_service.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseOfficerDto {

    private String name;

    private String email;

    private String phone;

    private Role role;

    private Department department;
}
