package io.github.nitishc.grievance.user_service.controller;

import io.github.nitishc.grievance.user_service.dto.RequestOfficerDto;
import io.github.nitishc.grievance.user_service.dto.ResponseOfficerDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface OfficerController {
    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> addOfficer(RequestOfficerDto officerDto, HttpServletRequest request) throws UserNotSavedException;

    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> getOfficerByEmail(String email, HttpServletRequest request) throws UserNotFoundException;
    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> getOfficerByPhone(String phone, HttpServletRequest request) throws UserNotFoundException;

    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> updateOfficer(String email, RequestOfficerDto user, HttpServletRequest request) throws UserNotFoundException, UserNotSavedException;

    public ResponseEntity<ResponseInfo<String>> deleteOfficer(String email, HttpServletRequest request) throws UserNotFoundException, UserNotDeletedException;
}
