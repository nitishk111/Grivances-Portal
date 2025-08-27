package io.github.nitishc.grievance.user_service.controller;

import io.github.nitishc.grievance.user_service.dto.OfficerResponse;
import io.github.nitishc.grievance.user_service.dto.OfficerSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.service.OfficerService;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private OfficerService officerService;

    @PostMapping("/add-officer")
    public ResponseEntity<ResponseInfo<OfficerResponse>> addOfficer(@Validated @RequestBody OfficerSignupRequest officerDto,
                                                           HttpServletRequest request) throws UserNotSavedException {
        log.info("Request Received to save new officer record");
        OfficerResponse officerResponse = officerService.signupOfficer(officerDto);
        ResponseInfo<OfficerResponse> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerResponse, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @GetMapping("/show-officer/{email}")
    public ResponseEntity<ResponseInfo<OfficerResponse>> getOfficerByEmail(@PathVariable("email") String email,
                                                                           HttpServletRequest request) throws UserNotFoundException {
        log.info("Request received to fetch record of via email: {}", email);
        OfficerResponse officerDto = officerService.OfficerProfile(email);
        ResponseInfo<OfficerResponse> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/update-officer/{email}")
    public ResponseEntity<ResponseInfo<OfficerResponse>> updateOfficer(@PathVariable("email") String email, @RequestBody OfficerSignupRequest officer,
                                                                       HttpServletRequest request) throws UserNotFoundException, UserNotSavedException {
        log.info("User with email: {}, requested to update record", email);
        OfficerResponse officerDto = officerService.updateOfficer(email, officer);
        ResponseInfo<OfficerResponse> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-officer/{email}")
    public ResponseEntity<ResponseInfo<String>> deleteOfficer(@PathVariable("email") String email,
                                                              HttpServletRequest request) throws UserNotFoundException, UserNotDeletedException {
        log.info("User with email: {}, requested to delete record", email);
        officerService.deleteOfficer(email);
        ResponseInfo<String> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "Record Deleted", request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }
}
