package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.RequestUserDto;
import io.github.nitishc.grievance.user_service.dto.ResponseUserDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;

public interface UserService {

    public ResponseUserDto addUser(RequestUserDto userDto) throws UserNotSavedException;

    public ResponseUserDto getUserByEmail(String email) throws UserNotFoundException;
    public ResponseUserDto getUserByPhone(String phone) throws UserNotFoundException;

    public ResponseUserDto updateUser(String email, RequestUserDto user) throws UserNotFoundException, UserNotSavedException;

    public String deleteUser(String email) throws UserNotFoundException, UserNotDeletedException;

}
