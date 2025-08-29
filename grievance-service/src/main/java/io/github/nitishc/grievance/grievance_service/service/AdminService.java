package io.github.nitishc.grievance.grievance_service.service;

import io.github.nitishc.grievance.grievance_service.dto.CommentRequest;
import io.github.nitishc.grievance.grievance_service.dto.OfficerGrievanceResponse;
import io.github.nitishc.grievance.grievance_service.exception.DatabaseConstraintVoilation;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.model.Comment;
import io.github.nitishc.grievance.grievance_service.model.Grievance;
import io.github.nitishc.grievance.grievance_service.repository.CommentRepository;
import io.github.nitishc.grievance.grievance_service.repository.GrievanceRepository;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.OfficerGrievanceMapper;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    @Autowired
    GrievanceRepository grievanceRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    OfficerGrievanceMapper officerGrievanceMapper;

    public List<OfficerGrievanceResponse> getAllGrievances() throws GrievanceNotFoundException {

        List<Grievance> grievances;
        try {
            grievances = grievanceRepository.findAll();
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
        log.info("Admin fetched all grievances");
        return grievanceResponses;
    }

    public List<OfficerGrievanceResponse> getGrievanceByStatus(Status status) throws GrievanceNotFoundException {

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
                        .map(officerGrievanceMapper::toDto)
                        .toList());

        log.info("Admin fetched grievances for status: ", status);
        return grievanceResponses;
    }

    public List<OfficerGrievanceResponse> getGrievanceByPriority(Priority priority) throws GrievanceNotFoundException {
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
                        .map(officerGrievanceMapper::toDto)
                        .toList());

        log.info("Admin fetched grievances for status: ", priority);
        return grievanceResponses;
    }

    public String updatePriority(Priority priority, long grievanceId) throws GrievanceNotFoundException, DatabaseConstraintVoilation {
        Grievance grievance = grievanceRepository.findById(grievanceId).get();

        if (grievance == null) {
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

    public String updateStatus(Status status, long grievanceId) throws DatabaseConstraintVoilation, GrievanceNotFoundException {
        Grievance grievance = grievanceRepository.findById(grievanceId).get();

        if (grievance == null) {
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
        String message = "Complaint Priority updated";
        log.info(message);
        return message;
    }

    public String updateGrievanceType(Department grievanceType, long grievanceId) throws GrievanceNotFoundException, DatabaseConstraintVoilation {

        Grievance grievance = grievanceRepository.findById(grievanceId).get();

        if (grievance == null) {
            String message = "No grievance record found";
            log.warn(message);
            throw new GrievanceNotFoundException(message);
        }

        grievance.setGrievanceType(grievanceType);
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

    public String addComment(String userEmail, long grievanceId, CommentRequest commentRequest) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        Grievance grievance = grievanceRepository.findById(grievanceId).get();
        if (grievance == null) {
            String message = "No complaint found with grievance id: " + grievanceId;
            log.warn(message);
            throw new GrievanceNotFoundException(message);
        }
        Comment comment = new Comment(userEmail, grievance, commentRequest);

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
}
