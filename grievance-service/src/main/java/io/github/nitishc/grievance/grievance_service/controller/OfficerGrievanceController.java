package io.github.nitishc.grievance.grievance_service.controller;

import io.github.nitishc.grievance.grievance_service.dto.OfficerGrievanceResponse;
import io.github.nitishc.grievance.grievance_service.exception.DatabaseConstraintVoilation;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.service.GrievanceService;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.ResponseInfo;
import io.github.nitishc.grievance.grievance_service.util.Status;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/officer")
public class OfficerGrievanceController {

    private final GrievanceService grievanceService;

    @Autowired
    public OfficerGrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    }

    @GetMapping("all-grievances")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByType(HttpServletRequest request) throws GrievanceNotFoundException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        Claims claims= (Claims) authentication.getDetails();
        Department grievanceType=Department.valueOf(claims.get("department", String.class));

        List<OfficerGrievanceResponse> grievanceByType = grievanceService.getGrievanceByType(grievanceType);
        ResponseInfo<List<OfficerGrievanceResponse>> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @GetMapping("grievances-by-status/{status}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByStatus(@PathVariable("status") Status status,
                                                                                             HttpServletRequest request) throws GrievanceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        Claims claims= (Claims) authentication.getDetails();
        String grievanceType= claims.get("department", String.class);

        List<OfficerGrievanceResponse> grievanceByType = grievanceService.getGrievanceByStatus(status, grievanceType);
        ResponseInfo<List<OfficerGrievanceResponse>> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @GetMapping("grievances-by-priority/{priority}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByPriority(@PathVariable("priority") Priority priority,
                                                                                               HttpServletRequest request) throws GrievanceNotFoundException, DatabaseConstraintVoilation {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        Claims claims= (Claims) authentication.getDetails();
        String grievanceType= claims.get("department", String.class);

        List<OfficerGrievanceResponse> grievanceByType = grievanceService.getGrievanceByPriority(priority, grievanceType);

        ResponseInfo<List<OfficerGrievanceResponse>> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-status/{priority}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievanceStatus(@PathVariable("priority") Priority priority,
                                                                      @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        Claims claims= (Claims) authentication.getDetails();
        String grievanceType= claims.get("department", String.class);

        String message = grievanceService.updatePriority(priority, grievanceId, grievanceType);
        ResponseInfo<String> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-priority/{status}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievancePriority(@PathVariable("status") Status status,
                                                                        @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        Claims claims= (Claims) authentication.getDetails();
        String grievanceType= claims.get("department", String.class);

        String message = grievanceService.updateStatus(status, grievanceId, grievanceType);
        ResponseInfo<String> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }
}
