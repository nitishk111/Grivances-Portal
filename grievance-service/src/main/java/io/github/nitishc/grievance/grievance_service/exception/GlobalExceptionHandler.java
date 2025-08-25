package io.github.nitishc.grievance.grievance_service.exception;

import io.github.nitishc.grievance.grievance_service.util.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        Map<String, String> errors= new HashMap<>();
        for(FieldError error: exception.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorInfo einfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), errors.toString(),
                request.getRequestURI());
        return new ResponseEntity<>(einfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GrievanceNotSavedException.class)
    public ResponseEntity<ErrorInfo> exceptionHandlerNotSaved(Exception e, HttpServletRequest request) {
        ErrorInfo einfo = new ErrorInfo(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(einfo, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(GrievanceNotFoundException.class)
    public ResponseEntity<ErrorInfo> exceptionHandlerNotFound(Exception e, HttpServletRequest request) {
        ErrorInfo einfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(einfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception e, HttpServletRequest request) {
        ErrorInfo einfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(einfo, HttpStatus.BAD_REQUEST);
    }
}
