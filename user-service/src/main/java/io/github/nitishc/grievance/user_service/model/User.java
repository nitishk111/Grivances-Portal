package io.github.nitishc.grievance.user_service.model;

import io.github.nitishc.grievance.user_service.util.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    private String fullName;


    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "ENUM('ADMIN','CITIZEN','OFFICER')")
    private Role role = Role.CITIZEN;


    public User(String fullName, String password, String email, String phone) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
