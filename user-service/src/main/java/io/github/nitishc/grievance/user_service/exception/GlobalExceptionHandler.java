package io.github.nitishc.grievance.user_service.exception;

import io.github.nitishc.grievance.user_service.util.ErrorInfo;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
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
        ErrorInfo rinfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), errors.toString(),
                request.getRequestURI());
        return new ResponseEntity<>(rinfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotSavedException.class)
    public ResponseEntity<ErrorInfo> exceptionHandlerNotSaved(Exception e, HttpServletRequest request) {
        ErrorInfo rinfo = new ErrorInfo(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(rinfo, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> exceptionHandlerNotFound(Exception e, HttpServletRequest request) {
        ErrorInfo rinfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(rinfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception e, HttpServletRequest request) {
        ErrorInfo rinfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(rinfo, HttpStatus.BAD_REQUEST);
    }
}
