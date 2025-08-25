package io.github.nitishc.grievance.user_service.dto;

import io.github.nitishc.grievance.user_service.util.Department;
import io.github.nitishc.grievance.user_service.util.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfficerResponse {


    private String name;

    private String email;

    private String phone;

    private Role role;

    private Department department;
}
