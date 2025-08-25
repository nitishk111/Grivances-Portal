package io.github.nitishc.grievance.grievance_service.util;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseInfo<T> {

    private int statusCode;

    private String status;

    private T response;

    private String path;


}
