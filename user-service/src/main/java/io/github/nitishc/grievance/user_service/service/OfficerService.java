package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.RequestOfficerDto;
import io.github.nitishc.grievance.user_service.dto.ResponseOfficerDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface OfficerService {
    public ResponseOfficerDto addOfficer(RequestOfficerDto officerDto) throws UserNotSavedException;

    public ResponseOfficerDto getOfficerByEmail(String email) throws UserNotFoundException;
    public ResponseOfficerDto getOfficerByPhone(String phone) throws UserNotFoundException;

    public ResponseOfficerDto updateOfficer(String email, RequestOfficerDto user) throws UserNotFoundException, UserNotSavedException;

    public String deleteOfficer(String email) throws UserNotFoundException, UserNotDeletedException;

}
