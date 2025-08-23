package io.github.nitishc.grievance.user_service.service.serviceimpl;

import io.github.nitishc.grievance.user_service.dto.RequestOfficerDto;
import io.github.nitishc.grievance.user_service.dto.ResponseOfficerDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.repository.UserRepository;
import io.github.nitishc.grievance.user_service.service.OfficerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficerServiceImpl implements OfficerService {

    private final UserRepository userRepo;

    @Autowired
    public OfficerServiceImpl(UserRepository userRepo){
        this.userRepo= userRepo;
    }

    @Override
    public ResponseOfficerDto addOfficer(RequestOfficerDto officerDto) throws UserNotSavedException {
        User user= new User(officerDto.getName(), officerDto.getPassword(), officerDto.getEmail(), officerDto.getPhone(), officerDto.getRole(), officerDto.getDepartment());
        User savedUser;
        try{
            savedUser= userRepo.save(user);
        } catch (Exception e) {
            log.error("Officer can not be saved", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Officer record added");
        return new ResponseOfficerDto(savedUser.getName(), savedUser.getEmail(), savedUser.getPhone(), savedUser.getRole(), savedUser.getDepartment());
    }

    @Override
    public ResponseOfficerDto getOfficerByEmail(String email) throws UserNotFoundException {
        User user;
        try{
            user= userRepo.findByEmail(email);
            if(user ==null){
                log.error("No Officer Recors with email: {} found",email);
                throw new UserNotFoundException("Officer not found with email: "+email);
            }
        } catch (Exception e) {
            log.error("No Officer Recors with email: {} found", email, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        log.info("User Reocrd with email: {} found", email);
        return new ResponseOfficerDto(user.getName(), user.getEmail(), user.getPhone(), user.getRole(), user.getDepartment());
    }

    @Override
    public ResponseOfficerDto getOfficerByPhone(String phone) throws UserNotFoundException {
        User user;
        try{
            user= userRepo.findByPhone(phone);
            if(user ==null) {
                log.error("No Officer Recors with phone: {} found", phone);
                throw new UserNotFoundException("Officer not found with phone: " + phone);
            }
        } catch (Exception e) {
            log.error("No Officer Recors with phone: {} found", phone, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        log.info("User Reocrd with phone: {} found", phone);
        return new ResponseOfficerDto(user.getName(), user.getEmail(), user.getPhone(), user.getRole(), user.getDepartment());
    }

    @Override
    public ResponseOfficerDto updateOfficer(String email, RequestOfficerDto user) throws UserNotFoundException, UserNotSavedException {
        User existingUser;
        try{
            existingUser= userRepo.findByEmail(email);
            if(existingUser ==null){
                log.error("No Officer Recors with email: {} found",email);
                throw new UserNotFoundException("Officer not found with email: "+email);
            }
        } catch (Exception e) {
            log.error("No Officer Recors with email: {} found", email, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        existingUser.setEmail(user.getEmail() !=null && !user.getEmail().isEmpty() ? user.getEmail(): existingUser.getEmail());
        existingUser.setPhone(user.getPhone() !=null && !user.getPhone().isEmpty() ? user.getPhone(): existingUser.getPhone());
        existingUser.setName(user.getName() !=null && !user.getName().isEmpty() ? user.getName(): existingUser.getName());
        existingUser.setPassword(user.getPassword() !=null && !user.getPassword().isEmpty() ? user.getPassword(): existingUser.getPassword());
        existingUser.setRole(user.getRole() != null ? user.getRole(): existingUser.getRole());
        existingUser.setDepartment(user.getDepartment() != null ? user.getDepartment(): existingUser.getDepartment());

        try{
            existingUser= userRepo.save(existingUser);
        } catch (Exception e) {
            log.error("Officer record can not be updated.", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Record updated Successfully");
        return new ResponseOfficerDto(existingUser.getName(),existingUser.getEmail(), existingUser.getPhone(), existingUser.getRole(), existingUser.getDepartment());

    }

    @Override
    public String deleteOfficer(String email) throws UserNotFoundException, UserNotDeletedException {
        User user= userRepo.findByEmail(email) ;//getUserByEmail(userDto.getEmail());

        try{
            userRepo.delete(user);
        }catch (Exception e){
            log.error("Officer record can not be deleted", e.getMessage());
            throw new UserNotDeletedException(e.getMessage());
        }
        String msg= "officer record deleted Successfully";
        log.info(msg);
        return msg;
    }
}
