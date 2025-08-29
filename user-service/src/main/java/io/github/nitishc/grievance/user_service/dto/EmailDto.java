package io.github.nitishc.grievance.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDto {

    String to;
    String subject;
    String body;
}
