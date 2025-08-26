package io.github.nitishc.grievance.user_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_details")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "ENUM('ROLE_ADMIN','ROLE_CITIZEN','ROLE_OFFICER')")
    private Role role = Role.ROLE_CITIZEN;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =  "ENUM('DEPT_SANITATION', 'DEPT_INFRA_STRUCTURE', 'DEPT_MANAGEMENT', 'DEPT_PWD')")
    private Department department = null;


}
