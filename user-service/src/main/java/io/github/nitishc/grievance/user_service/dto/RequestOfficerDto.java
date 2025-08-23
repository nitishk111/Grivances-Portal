package io.github.nitishc.grievance.user_service.dto;

import io.github.nitishc.grievance.user_service.util.Department;
import io.github.nitishc.grievance.user_service.util.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestOfficerDto {

    @NotBlank(message = "Name is Mandatory")
    @Size(min = 3, max = 30, message = "Name must be between 3-30 character")
    private String name;

    @NotBlank(message = "Password needed")
    @Size(min = 6, message = "Password should contain at least 6 character")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min=10, max=10, message = "Phone no should be of 10 digits only")
    private String phone;

//    @NotBlank(message= "Role is needed to insert new officer record")
    private Role role;

//    @NotBlank(message= "Department is needed to insert new officer record")
    private Department department;

}
