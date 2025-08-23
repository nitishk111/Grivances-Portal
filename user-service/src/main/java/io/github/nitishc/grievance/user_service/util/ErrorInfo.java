package io.github.nitishc.grievance.user_service.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorInfo {

    private int statusCode;
    private String status;
    private String errors;
    private String path;
}
