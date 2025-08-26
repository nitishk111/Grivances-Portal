package io.github.nitishc.grievance.user_service.controller;

import io.github.nitishc.grievance.user_service.dto.UserResponse;
import io.github.nitishc.grievance.user_service.dto.UserSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.service.UserService;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("show-profile/{email}")
    public ResponseEntity<ResponseInfo<UserResponse>> getUserByEmail(@PathVariable("email") String email, HttpServletRequest request)
            throws UserNotFoundException {
        log.info("Request received to fetch record of via email: {}", email);
        UserResponse userDto = userService.userProfile(email);
        ResponseInfo<UserResponse> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }


    @PatchMapping("/update/{email}")
    public ResponseEntity<ResponseInfo<UserResponse>> updateUser(
            @PathVariable("email") String email, @RequestBody UserSignupRequest user, HttpServletRequest request)
            throws UserNotFoundException, UserNotSavedException {
        log.info("User with email: {}, requested to update record", email);
        UserResponse userDto = userService.updateUser(email, user);
        ResponseInfo<UserResponse> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ResponseInfo<String>> deleteUser(@PathVariable("email") String email, HttpServletRequest request) throws UserNotFoundException, UserNotDeletedException {
        log.info("User with email: {}, requested to delete record", email);
        userService.deleteUser(email);
        ResponseInfo<String> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "Record Deleted", request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }
}
