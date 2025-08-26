package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.UserResponse;
import io.github.nitishc.grievance.user_service.dto.UserSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.repository.UserRepository;
import io.github.nitishc.grievance.user_service.util.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserMapper mapper;

    public UserResponse userSignup(UserSignupRequest userDto) throws UserNotSavedException {
        User user= mapper.toEntity(userDto);
        User savedUser;
        try{
            savedUser= userRepo.save(user);
        }catch (Exception e){
            log.error("User Can not be saved", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("User Added");
        return mapper.toDto(savedUser);
    }

    public UserResponse userProfile(String email) throws UserNotFoundException {
        User user;
        try{
            user= userRepo.findByEmail(email);
            if(user==null){
                log.error("No user Record with email: {} found", email);
                throw new UserNotFoundException("No user found for email: "+email);
            }
        } catch (Exception e) {
            log.error("No user Record with email: {} found", email, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        log.info("User Record with email: {} found", email);
        return mapper.toDto(user);
    }


    public UserResponse updateUser(String email, UserSignupRequest user) throws UserNotFoundException, UserNotSavedException {
        User existingUser= userRepo.findByEmail(email); //getUserByEmail(email);
        existingUser.setEmail(user.getEmail() !=null && !user.getEmail().isEmpty() ? user.getEmail(): existingUser.getEmail());
        existingUser.setPhone(user.getPhone() !=null && !user.getPhone().isEmpty() ? user.getPhone(): existingUser.getPhone());
        existingUser.setFullName(user.getFullName() !=null && !user.getFullName().isEmpty() ? user.getFullName(): existingUser.getFullName());
        existingUser.setPassword(user.getPassword() !=null && !user.getPassword().isEmpty() ? user.getPassword(): existingUser.getPassword());

        try{
            existingUser= userRepo.save(existingUser);
        } catch (Exception e) {
            log.error("User record can not be updated.", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Record updated Successfully");
        return mapper.toDto(existingUser);
    }

    public String deleteUser(String email) throws UserNotFoundException, UserNotDeletedException {
        User user= userRepo.findByEmail(email);
        try{
            userRepo.delete(user);
        }catch (Exception e){
            log.error("User can not be deleted", e.getMessage());
            throw new UserNotDeletedException(e.getMessage());
        }
        String msg= "User Deleted Successfully";
        log.info(msg);
        return msg;
    }
}
