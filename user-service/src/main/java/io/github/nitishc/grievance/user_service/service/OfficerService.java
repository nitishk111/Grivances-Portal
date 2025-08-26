package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.OfficerResponse;
import io.github.nitishc.grievance.user_service.dto.OfficerSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.repository.UserRepository;
import io.github.nitishc.grievance.user_service.util.OfficerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficerService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfficerMapper mapper;

    public OfficerResponse signupOfficer(OfficerSignupRequest officerDto) throws UserNotSavedException {

        User officer= mapper.toEntity(officerDto);
        User savedUser;
        try{
            savedUser= userRepository.save(officer);
        } catch (Exception e) {
            log.error("Officer can not be saved", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Officer record added");
        return mapper.toDto(savedUser);
    }

    public OfficerResponse OfficerProfile(String email) throws UserNotFoundException {
        User officer;
        try{
            officer= userRepository.findByEmail(email);
            if(officer ==null){
                log.error("No Officer Recors with email: {} found",email);
                throw new UserNotFoundException("Officer not found with email: "+email);
            }
        } catch (Exception e) {
            log.error("No Officer Recors with email: {} found", email, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        log.info("User Reocrd with email: {} found", email);
        return mapper.toDto(officer);
    }


    public OfficerResponse updateOfficer(String email, OfficerSignupRequest user) throws UserNotFoundException, UserNotSavedException {
        User existingOfficer;
        try{
            existingOfficer= userRepository.findByEmail(email);
            if(existingOfficer ==null){
                log.error("No Officer Recors with email: {} found",email);
                throw new UserNotFoundException("Officer not found with email: "+email);
            }
        } catch (Exception e) {
            log.error("No Officer Recors with email: {} found", email, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        existingOfficer.setEmail(user.getEmail() !=null && !user.getEmail().isEmpty() ? user.getEmail(): existingOfficer.getEmail());
        existingOfficer.setPhone(user.getPhone() !=null && !user.getPhone().isEmpty() ? user.getPhone(): existingOfficer.getPhone());
        existingOfficer.setFullName(user.getFullName() !=null && !user.getFullName().isEmpty() ? user.getFullName(): existingOfficer.getFullName());
        existingOfficer.setPassword(user.getPassword() !=null && !user.getPassword().isEmpty() ? user.getPassword(): existingOfficer.getPassword());
        existingOfficer.setRole(user.getRole() != null ? user.getRole(): existingOfficer.getRole());
        existingOfficer.setDepartment(user.getDepartment() != null ? user.getDepartment(): existingOfficer.getDepartment());

        try{
            existingOfficer= userRepository.save(existingOfficer);
        } catch (Exception e) {
            log.error("Officer record can not be updated.", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Record updated Successfully");
        return mapper.toDto(existingOfficer);
    }

    public String deleteOfficer(String email) throws UserNotFoundException, UserNotDeletedException {
        User officer= userRepository.findByEmail(email) ;
        try{
            userRepository.delete(officer);
        }catch (Exception e){
            log.error("Officer record can not be deleted", e.getMessage());
            throw new UserNotDeletedException(e.getMessage());
        }
        String msg= "officer record deleted Successfully";
        log.info(msg);
        return msg;
    }
}
