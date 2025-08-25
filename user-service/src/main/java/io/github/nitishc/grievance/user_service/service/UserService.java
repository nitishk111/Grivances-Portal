package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.UserResponse;
import io.github.nitishc.grievance.user_service.dto.UserSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public void userSignup(UserSignupRequest userDto) throws UserNotSavedException {
        User user= new User(userDto.getName(),userDto.getPassword(), userDto.getEmail(), userDto.getPhone());
        User savedUser;
        try{
            savedUser= userRepo.save(user);
        }catch (Exception e){
            log.error("User Can not be saved", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("User Added");
//        return new UserLoginRequest(savedUser.getEmail(), savedUser.getName(), savedUser.getPhone());
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
        return new UserResponse(user.getEmail(), user.getFullName(), user.getPhone());
    }


    public UserResponse updateUser(String email, UserSignupRequest user) throws UserNotFoundException, UserNotSavedException {
        User existingUser= userRepo.findByEmail(email); //getUserByEmail(email);
        existingUser.setEmail(user.getEmail() !=null && !user.getEmail().isEmpty() ? user.getEmail(): existingUser.getEmail());
        existingUser.setPhone(user.getPhone() !=null && !user.getPhone().isEmpty() ? user.getPhone(): existingUser.getPhone());
        existingUser.setFullName(user.getName() !=null && !user.getName().isEmpty() ? user.getName(): existingUser.getFullName());
        existingUser.setPassword(user.getPassword() !=null && !user.getPassword().isEmpty() ? user.getPassword(): existingUser.getPassword());

        try{
            existingUser= userRepo.save(existingUser);
        } catch (Exception e) {
            log.error("User record can not be updated.", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Record updated Successfully");
        return new UserResponse(existingUser.getEmail(), existingUser.getFullName(), existingUser.getPhone());
    }

    public String deleteUser(String email) throws UserNotFoundException, UserNotDeletedException {
        User user= userRepo.findByEmail(email) ;//getUserByEmail(userDto.getEmail());

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
