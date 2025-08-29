package io.github.nitishc.grievance.grievance_service.controller;


import io.github.nitishc.grievance.grievance_service.dto.CommentRequest;
import io.github.nitishc.grievance.grievance_service.dto.OfficerGrievanceResponse;
import io.github.nitishc.grievance.grievance_service.exception.DatabaseConstraintVoilation;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.model.GrievanceFile;
import io.github.nitishc.grievance.grievance_service.service.AdminService;
import io.github.nitishc.grievance.grievance_service.service.FileService;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.ResponseInfo;
import io.github.nitishc.grievance.grievance_service.util.Status;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-grievance")
public class AdminGrievanceController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private FileService fileService;

    @GetMapping("all-grievances")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByType(HttpServletRequest request) throws GrievanceNotFoundException {

        List<OfficerGrievanceResponse> grievanceByType = adminService.getAllGrievances();
        ResponseInfo<List<OfficerGrievanceResponse>> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("/comment/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> postComment(@RequestBody CommentRequest commentRequest,
                                                            @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        String message = adminService.addComment(userEmail, grievanceId, commentRequest);

        ResponseInfo<String> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.ACCEPTED);
    }

    @GetMapping("grievances-by-status/{status}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByStatus(@PathVariable("status") Status status,
                                                                                             HttpServletRequest request) throws GrievanceNotFoundException {
        List<OfficerGrievanceResponse> grievanceByType = adminService.getGrievanceByStatus(status);
        ResponseInfo<List<OfficerGrievanceResponse>> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @GetMapping("grievances-by-priority/{priority}")
    public ResponseEntity<ResponseInfo<List<OfficerGrievanceResponse>>> getGrievanceByPriority(@PathVariable("priority") Priority priority,
                                                                                               HttpServletRequest request) throws GrievanceNotFoundException, DatabaseConstraintVoilation {
        List<OfficerGrievanceResponse> grievanceByType = adminService.getGrievanceByPriority(priority);

        ResponseInfo<List<OfficerGrievanceResponse>> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                grievanceByType, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-priority/{priority}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievancePriority(@PathVariable("priority") Priority priority,
                                                                        @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = adminService.updatePriority(priority, grievanceId);
        ResponseInfo<String> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-status/{status}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievanceStatus(@PathVariable("status") Status status,
                                                                      @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = adminService.updateStatus(status, grievanceId);
        ResponseInfo<String> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("update-type/{grievance-type}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievanceType(@PathVariable("grievance-type") Department grievanceType,
                                                                      @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = adminService.updateGrievanceType(grievanceType, grievanceId);
        ResponseInfo<String> rInfo = new ResponseInfo<>(HttpStatus.FOUND.value(), HttpStatus.FOUND.name(),
                message, request.getRequestURI());
        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @GetMapping("grievance-file/{grievance-id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("grievance-id") long grievanceId) throws GrievanceNotFoundException {
        GrievanceFile file = fileService.getFile(grievanceId);

        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getImage());
    }

}
