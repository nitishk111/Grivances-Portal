package io.github.nitishc.grievance.grievance_service.service;

import io.github.nitishc.grievance.grievance_service.dto.CommentRequest;
import io.github.nitishc.grievance.grievance_service.dto.GrievanceRequest;
import io.github.nitishc.grievance.grievance_service.dto.GrievanceResponse;
import io.github.nitishc.grievance.grievance_service.dto.OfficerGrievanceResponse;
import io.github.nitishc.grievance.grievance_service.exception.DatabaseConstraintVoilation;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotSavedException;
import io.github.nitishc.grievance.grievance_service.model.Address;
import io.github.nitishc.grievance.grievance_service.model.Comment;
import io.github.nitishc.grievance.grievance_service.model.Grievance;
import io.github.nitishc.grievance.grievance_service.repository.AddressRepository;
import io.github.nitishc.grievance.grievance_service.repository.CommentRepository;
import io.github.nitishc.grievance.grievance_service.repository.GrievanceRepository;
import io.github.nitishc.grievance.grievance_service.util.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GrievanceService {


    @Autowired
    private GrievanceRepository grievanceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GrievancesMapper grievancesMapper;
    @Autowired
    private OfficerGrievanceMapper officerGrievanceMapper;


    @Transactional
    public String saveGrievance(GrievanceRequest grievanceDto, String userEmail) throws GrievanceNotSavedException, DatabaseConstraintVoilation {
        Grievance grievance = grievancesMapper.toEntity(grievanceDto);
        grievance.setUserEmail(userEmail);
        try {
            grievance = grievanceRepository.save(grievance);
            if (grievanceDto.getAddress() != null) {
                Address address = grievanceDto.getAddress();
                address.setGrievance(grievance);
                addressRepository.save(address);
            }
        } catch (Exception e) {
            String message = "Can not save, recheck record.";
            log.error(message, e.getMessage());
            throw new DatabaseConstraintVoilation(message + e.getMessage());
        }
        String message = "Complaint registered with unique id:" + grievance.getGrievanceId();
        log.info(message);
        return message;
    }

    public List<GrievanceResponse> getGrievanceByUser(String userEmail) throws GrievanceNotFoundException {
        List<Grievance> grievances;
        try {
            grievances = grievanceRepository.findAllByUserEmail(userEmail);
            if (grievances == null || grievances.size() == 0) {
                String message = "No registered complain found.";
                log.warn(message);
                throw new GrievanceNotFoundException(message);
            }
        } catch (Exception e) {
            throw new GrievanceNotFoundException(e.getMessage());
        }
        List<GrievanceResponse> grievanceResponses = new ArrayList<>();
        for (Grievance g : grievances) {
            grievanceResponses.add(grievancesMapper.toDto(g));
        }
        log.info("User fetched grievances");
        return grievanceResponses;
    }

    public List<OfficerGrievanceResponse> getGrievanceByType(Department grievanceType) throws GrievanceNotFoundException {
        List<Grievance> grievances;
        try {
            grievances = grievanceRepository.findAllByGrievanceType(grievanceType);
            if (grievances == null || grievances.isEmpty()) {
                String message = "No registered complain found.";
                log.warn(message);
                throw new GrievanceNotFoundException(message);
            }
        } catch (Exception e) {
            throw new GrievanceNotFoundException(e.getMessage());
        }
        List<OfficerGrievanceResponse> grievanceResponses = new ArrayList<>();
        for (Grievance g : grievances) {
            grievanceResponses.add(officerGrievanceMapper.toDto(g));
        }
        log.info("Officer fetched grievances for department: ", grievanceType);
        return grievanceResponses;
    }

    public List<OfficerGrievanceResponse> getGrievanceByStatus(Status status, String grievanceType) throws GrievanceNotFoundException {
        List<Grievance> grievances;

        try {
            grievances = grievanceRepository.findAllByStatus(status);
            if (grievances == null || grievances.isEmpty()) {
                String message = "No registered complain found.";
                log.warn(message);
                throw new GrievanceNotFoundException(message);
            }
        } catch (Exception e) {
            throw new GrievanceNotFoundException(e.getMessage());
        }
        List<OfficerGrievanceResponse> grievanceResponses = new ArrayList<>();
        grievanceResponses.addAll(
                grievances.stream()
                        .filter(g -> g.getGrievanceType().toString().equals(grievanceType))
                        .map(officerGrievanceMapper::toDto)
                        .toList());

        log.info("Officer fetched grievances for status: ", status);
        return grievanceResponses;
    }

    public List<OfficerGrievanceResponse> getGrievanceByPriority(Priority priority, String grievanceType) throws GrievanceNotFoundException, DatabaseConstraintVoilation {
        List<Grievance> grievances;

        try {
            grievances = grievanceRepository.findAllByPriority(priority);
            if (grievances == null || grievances.isEmpty()) {
                String message = "No registered complain found.";
                log.warn(message);
                throw new GrievanceNotFoundException(message);
            }
        } catch (Exception e) {
            throw new GrievanceNotFoundException(e.getMessage());
        }
        List<OfficerGrievanceResponse> grievanceResponses = new ArrayList<>();
        grievanceResponses.addAll(
                grievances.stream()
                        .filter(g -> g.getGrievanceType().toString().equals(grievanceType))
                        .map(officerGrievanceMapper::toDto)
                        .toList());
        log.info("Officer fetched grievances for priority: ", priority);
        return grievanceResponses;
    }

    @Transactional
    public String addComment(String userEmail, long grievanceId, CommentRequest commentRequest) throws GrievanceNotFoundException, DatabaseConstraintVoilation {

        Grievance grievance = grievanceRepository.findById(grievanceId).get();
        if (grievance == null) {
            String message = "No complaint found with grievance id: " + grievanceId;
            log.warn(message);
            throw new GrievanceNotFoundException(message);
        }
        Comment comment = new Comment(userEmail, grievance, commentRequest);

//        List<Comment> comments = grievance.getComments();
//        comments.add(comment);
//        grievance.setComments(comments);

        grievance.setLastUpdate(LocalDate.now());
        try {
            grievanceRepository.save(grievance);
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new DatabaseConstraintVoilation(e.getMessage());
        }
        log.info("Comment saved");
        return "Comment Saved";
    }

    public String updateGrievance(GrievanceRequest grievancerequest, String userEmail, long grievanceId) throws GrievanceNotFoundException, DatabaseConstraintVoilation {

        Grievance grievance = grievanceRepository.findById(grievanceId).get();
        String registeredUserEmail = grievance.getUserEmail();
        if (!registeredUserEmail.equals(userEmail)) {
            String message = "No grievance record found";
            log.warn(message);
            throw new GrievanceNotFoundException(message);
        }
        grievance.setComplaintDescription(grievancerequest.getComplaintDescription() == null || grievancerequest.getComplaintDescription().length() == 0
                ? grievance.getComplaintDescription() : grievancerequest.getComplaintDescription());

        grievance.setComplaintTitle(grievancerequest.getComplaintTitle() == null || grievancerequest.getComplaintTitle().length() == 0
                ? grievance.getComplaintTitle() : grievancerequest.getComplaintTitle());

//        grievance.setAddress(grievancerequest.getAddress() == null ? grievance.getAddress(): grievancerequest.getAddress());

        grievance.setLastUpdate(LocalDate.now());
        try {
            grievanceRepository.save(grievance);
        } catch (Exception e) {
            String message = "Can not update, recheck record.";
            log.error(message, e.getMessage());
            throw new DatabaseConstraintVoilation(message + e.getMessage());
        }
        String message = "Complaint record updated";
        log.info(message);
        return message;
    }

    public String updatePriority(Priority priority, long grievanceId, String grievanceType) throws GrievanceNotFoundException, DatabaseConstraintVoilation {

        Grievance grievance = grievanceRepository.findById(grievanceId).get();

        if (grievance == null || !grievance.getGrievanceType().toString().equals(grievanceType)) {
            String message = "No grievance record found";
            log.warn(message);
            throw new GrievanceNotFoundException(message);
        }

        grievance.setPriority(priority);
        grievance.setLastUpdate(LocalDate.now());

        try {
            grievanceRepository.save(grievance);
        } catch (Exception e) {
            String message = "Can not update, recheck record.";
            log.error(message, e.getMessage());
            throw new DatabaseConstraintVoilation(message + e.getMessage());
        }
        String message = "Complaint Priority updated";
        log.info(message);
        return message;
    }

    public String updateStatus(Status status, long grievanceId, String grievanceType) throws GrievanceNotFoundException, DatabaseConstraintVoilation {

        Grievance grievance = grievanceRepository.findById(grievanceId).get();

        if (grievance == null || !grievance.getGrievanceType().toString().equals(grievanceType)) {
            String message = "No grievance record found";
            log.warn(message);
            throw new GrievanceNotFoundException(message);
        }

        grievance.setStatus(status);
        grievance.setLastUpdate(LocalDate.now());

        try {
            grievanceRepository.save(grievance);
        } catch (Exception e) {
            String message = "Can not update, recheck record.";
            log.error(message, e.getMessage());
            throw new DatabaseConstraintVoilation(message + e.getMessage());
        }
        String message = "Complaint Status updated";
        log.info(message);
        return message;
    }
}
