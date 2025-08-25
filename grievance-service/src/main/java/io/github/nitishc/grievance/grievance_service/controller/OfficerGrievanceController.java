package io.github.nitishc.grievance.grievance_service.controller;

import io.github.nitishc.grievance.grievance_service.dto.GrievanceRequest;
import io.github.nitishc.grievance.grievance_service.dto.OfficerGrievanceResponse;
import io.github.nitishc.grievance.grievance_service.exception.DatabaseConstraintVoilation;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotSavedException;
import io.github.nitishc.grievance.grievance_service.service.GrievanceService;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.ResponseInfo;
import io.github.nitishc.grievance.grievance_service.util.Status;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/officer")
public class OfficerGrievanceController {

    private final GrievanceService grievanceService;

    @Autowired
    public OfficerGrievanceController(GrievanceService grievanceService){
        this.grievanceService =grievanceService;
    }

    @GetMapping("grievances-by-type/{grievance-type}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByType(@PathVariable("grievance-type") Department grievanceType,
                                                                                           HttpServletRequest request) throws GrievanceNotFoundException {

        List<OfficerGrievanceResponse> grievanceByType = grievanceService.getGrievanceByType(grievanceType);

        ResponseInfo<List<OfficerGrievanceResponse>> rInfo= new ResponseInfo(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @GetMapping("grievances-by-status/{status}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByStatus(@PathVariable("status") Status status,
                                                                                           HttpServletRequest request) throws GrievanceNotFoundException {

        List<OfficerGrievanceResponse> grievanceByType = grievanceService.getGrievanceByStatus(status);

        ResponseInfo<List<OfficerGrievanceResponse>> rInfo= new ResponseInfo(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @GetMapping("grievances-by-priority/{priority}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByPriority(@PathVariable("priority") Priority priority,
                                                                                           HttpServletRequest request) throws GrievanceNotFoundException, DatabaseConstraintVoilation {

        List<OfficerGrievanceResponse> grievanceByType = grievanceService.getGrievanceByPriority(priority);

        ResponseInfo<List<OfficerGrievanceResponse>> rInfo= new ResponseInfo(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-status/{priority}/{user-id}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievanceStatus(@PathVariable("priority") Priority priority, @PathVariable("user-id") long userId,
                                      @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = grievanceService.updatePriority(priority, userId, grievanceId);

        ResponseInfo<String> rInfo= new ResponseInfo(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-status/{status}/{user-id}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievancePriority(@PathVariable("status")Status status, @PathVariable("user-id") long userId,
                                                                        @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = grievanceService.updateStatus(status, userId, grievanceId);

        ResponseInfo<String> rInfo= new ResponseInfo(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }
}
