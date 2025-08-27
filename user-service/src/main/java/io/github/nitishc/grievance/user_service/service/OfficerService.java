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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficerService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfficerMapper mapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public OfficerResponse signupOfficer(OfficerSignupRequest officerDto) throws UserNotSavedException {

        User officer= mapper.toEntity(officerDto);
        officer.setPassword(passwordEncoder.encode(officer.getPassword()));
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


    public OfficerResponse updateOfficer(String email, OfficerSignupRequest officer) throws UserNotFoundException, UserNotSavedException {
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
        existingOfficer.setEmail(officer.getEmail() !=null && !officer.getEmail().isEmpty() ? officer.getEmail(): existingOfficer.getEmail());
        existingOfficer.setPhone(officer.getPhone() !=null && !officer.getPhone().isEmpty() ? officer.getPhone(): existingOfficer.getPhone());
        existingOfficer.setFullName(officer.getFullName() !=null && !officer.getFullName().isEmpty() ? officer.getFullName(): existingOfficer.getFullName());
        existingOfficer.setPassword(officer.getPassword() !=null && !officer.getPassword().isEmpty() ? passwordEncoder.encode(officer.getPassword()): passwordEncoder.encode(existingOfficer.getPassword()));
        existingOfficer.setRole(officer.getRole() != null ? officer.getRole(): existingOfficer.getRole());
        existingOfficer.setDepartment(officer.getDepartment() != null ? officer.getDepartment(): existingOfficer.getDepartment());

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
