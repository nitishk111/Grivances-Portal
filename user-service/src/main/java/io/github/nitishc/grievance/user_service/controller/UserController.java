package io.github.nitishc.grievance.user_service.controller;

import io.github.nitishc.grievance.user_service.dto.RequestUserDto;
import io.github.nitishc.grievance.user_service.dto.ResponseUserDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserController {
    public ResponseEntity<ResponseInfo<ResponseUserDto>> addUser(RequestUserDto userDto, HttpServletRequest request) throws UserNotSavedException;

    public ResponseEntity<ResponseInfo<ResponseUserDto>> getUserByEmail(String email, HttpServletRequest request) throws UserNotFoundException;
    public ResponseEntity<ResponseInfo<ResponseUserDto>> getUserByPhone(String phone, HttpServletRequest request) throws UserNotFoundException;

    public ResponseEntity<ResponseInfo<ResponseUserDto>> updateUser(String email, RequestUserDto user, HttpServletRequest request) throws UserNotFoundException, UserNotSavedException;

    public ResponseEntity<ResponseInfo<String>> deleteUser(String email, HttpServletRequest request) throws UserNotFoundException, UserNotDeletedException;
}
