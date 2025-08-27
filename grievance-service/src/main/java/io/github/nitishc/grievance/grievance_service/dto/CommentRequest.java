package io.github.nitishc.grievance.grievance_service.dto;

import io.github.nitishc.grievance.grievance_service.model.Grievance;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {

    private String commentText;
}
