package io.github.nitishc.grievance.user_service.model;

import io.github.nitishc.grievance.user_service.util.Department;
import io.github.nitishc.grievance.user_service.util.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is Mandatory")
    @Size(min = 3, max = 30, message = "Name must be between 3-30 character")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Password needed")
    @Size(min = 6, message = "Password should contain at least 6 character")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min=10, max=10, message = "Phone no should be of 10 digits only")
    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN','CITIZEN','OFFICER')")
    private Role role = Role.CITIZEN;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =  "ENUM('SANITATION', 'INFRA_STRUCTURE', 'MANAGEMENT')")
    private Department department = null;

    public User(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String name, String password, String email, String phone, Role role, Department department) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.department = department;
    }
}
