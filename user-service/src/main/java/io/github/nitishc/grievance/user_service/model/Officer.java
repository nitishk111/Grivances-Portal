package io.github.nitishc.grievance.user_service.model;

import io.github.nitishc.grievance.user_service.util.Department;
import io.github.nitishc.grievance.user_service.util.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Data
@Entity
@NoArgsConstructor
public class Officer extends User{

//    @Setter(AccessLevel.NONE)
//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "ENUM('ADMIN','CITIZEN','OFFICER')", nullable = false)
//    private Role role = Role.CITIZEN;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =  "ENUM('SANITATION', 'INFRA_STRUCTURE', 'MANAGEMENT')")
    private Department department = null;

    public Officer(@Validated @NotBlank(message = "Name is Mandatory") @Size(min = 3, max = 30, message = "Name must be between 3-30 character") String fullName,
                   @Validated @NotBlank(message = "Password needed") @Size(min = 6, message = "Password should contain at least 6 character") String password,
                   @Validated @NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String email,
                   @Validated @Size(min=10, max=10, message = "Phone no should be of 10 digits only") String phone,
                   @Validated @NotBlank(message= "Role is needed to insert new officer record") Role role,
                   @Validated @NotBlank(message= "Department is needed to insert new officer record") Department department) {

        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
        this.setRole(role);
        this.department= department;

    }
}
