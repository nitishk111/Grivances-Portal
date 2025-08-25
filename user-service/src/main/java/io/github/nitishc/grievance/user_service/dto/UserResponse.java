package io.github.nitishc.grievance.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private String name;

    private String email;

    private String phone;
}
