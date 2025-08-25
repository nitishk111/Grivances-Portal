package io.github.nitishc.grievance.user_service.controller;

import io.github.nitishc.grievance.user_service.dto.UserSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.service.UserService;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@Slf4j
public class AuthController {


    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/officer-login")
    public ResponseEntity<ResponseInfo<String>> OfficerLogin(HttpServletRequest request){


        ResponseInfo<String> loginSuccessful = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "Login Successful", request.getRequestURI());
        return new ResponseEntity<>(loginSuccessful, HttpStatus.ACCEPTED);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<ResponseInfo<String>> addUser(
            @Validated @RequestBody UserSignupRequest userSignup, HttpServletRequest request)
            throws UserNotSavedException {
        log.info("Request Received to save new user record");

        userService.userSignup(userSignup);

        ResponseInfo<String> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "You have signed up successfully, Pls Login now.", request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseInfo<String>> UserLogin(HttpServletRequest request){


        ResponseInfo<String> loginSuccessful = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "Login Successful", request.getRequestURI());
        return new ResponseEntity<>(loginSuccessful, HttpStatus.ACCEPTED);
    }
}
