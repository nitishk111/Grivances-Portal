package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.OfficerResponse;
import io.github.nitishc.grievance.user_service.dto.OfficerSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.model.Officer;
import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.repository.OfficerRepository;
import io.github.nitishc.grievance.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficerService{

    private final OfficerRepository officerRepository;

    @Autowired
    public OfficerService(OfficerRepository officerRepository){
        this.officerRepository= officerRepository;
    }

    public void signupOfficer(OfficerSignupRequest officerDto) throws UserNotSavedException {
        Officer officer= new Officer(officerDto.getName(), officerDto.getPassword(), officerDto.getEmail(),
                officerDto.getPhone(), officerDto.getRole(), officerDto.getDepartment());
        User savedUser;
        try{
            savedUser= officerRepository.save(officer);
        } catch (Exception e) {
            log.error("Officer can not be saved", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Officer record added");
    }

    public OfficerResponse OfficerProfile(String email) throws UserNotFoundException {
        Officer officer;
        try{
            officer= officerRepository.findByEmail(email);
            if(officer ==null){
                log.error("No Officer Recors with email: {} found",email);
                throw new UserNotFoundException("Officer not found with email: "+email);
            }
        } catch (Exception e) {
            log.error("No Officer Recors with email: {} found", email, e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        log.info("User Reocrd with email: {} found", email);
        return new OfficerResponse(officer.getFullName(), officer.getEmail(), officer.getPhone(), officer.getRole(), officer.getDepartment());
    }


    public OfficerResponse updateOfficer(String email, OfficerSignupRequest user) throws UserNotFoundException, UserNotSavedException {
        Officer existingOfficer;
        try{
            existingOfficer= officerRepository.findByEmail(email);
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
        existingOfficer.setFullName(user.getName() !=null && !user.getName().isEmpty() ? user.getName(): existingOfficer.getFullName());
        existingOfficer.setPassword(user.getPassword() !=null && !user.getPassword().isEmpty() ? user.getPassword(): existingOfficer.getPassword());
        existingOfficer.setRole(user.getRole() != null ? user.getRole(): existingOfficer.getRole());
        existingOfficer.setDepartment(user.getDepartment() != null ? user.getDepartment(): existingOfficer.getDepartment());

        try{
            existingOfficer= officerRepository.save(existingOfficer);
        } catch (Exception e) {
            log.error("Officer record can not be updated.", e.getMessage());
            throw new UserNotSavedException(e.getMessage());
        }
        log.info("Record updated Successfully");
        return new OfficerResponse(existingOfficer.getFullName(),existingOfficer.getEmail(), existingOfficer.getPhone(), existingOfficer.getRole(), existingOfficer.getDepartment());

    }

    public String deleteOfficer(String email) throws UserNotFoundException, UserNotDeletedException {
        Officer officer= officerRepository.findByEmail(email) ;//getUserByEmail(userDto.getEmail());

        try{
            officerRepository.delete(officer);
        }catch (Exception e){
            log.error("Officer record can not be deleted", e.getMessage());
            throw new UserNotDeletedException(e.getMessage());
        }
        String msg= "officer record deleted Successfully";
        log.info(msg);
        return msg;
    }
}
