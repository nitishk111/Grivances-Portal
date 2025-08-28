package io.github.nitishc.grievance.grievance_service.controller;

import io.github.nitishc.grievance.grievance_service.dto.CommentRequest;
import io.github.nitishc.grievance.grievance_service.dto.GrievanceRequest;
import io.github.nitishc.grievance.grievance_service.dto.GrievanceResponse;
import io.github.nitishc.grievance.grievance_service.exception.DatabaseConstraintVoilation;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotSavedException;
import io.github.nitishc.grievance.grievance_service.service.GrievanceService;
import io.github.nitishc.grievance.grievance_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grievances")
public class UserGrievanceController {

    private final GrievanceService grievanceService;

    @Autowired
    public UserGrievanceController(GrievanceService grievanceService){
        this.grievanceService=grievanceService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseInfo<String>> registerGrievance(@RequestBody GrievanceRequest grievanceRequest, HttpServletRequest request)
            throws GrievanceNotSavedException, DatabaseConstraintVoilation {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        String message= grievanceService.saveGrievance(grievanceRequest, userEmail);

        ResponseInfo<String> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.ACCEPTED);
    }

    @GetMapping("/show")
    public ResponseEntity<ResponseInfo<List<GrievanceResponse>>> getGrievancesByUser(HttpServletRequest request) throws GrievanceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();

        System.out.println("joj");
        List<GrievanceResponse> grievanceByUser = grievanceService.getGrievanceByUser(userEmail);

        ResponseInfo<List<GrievanceResponse>> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                grievanceByUser, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("/comment/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> postComment(@RequestBody CommentRequest commentRequest,
                            @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();
        String message = grievanceService.addComment(userEmail, grievanceId, commentRequest);

        ResponseInfo<String> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.ACCEPTED);
    }

    @PostMapping("/update/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievance(@RequestBody GrievanceRequest grievancerequest,
                                @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail =(String) authentication.getPrincipal();

        String message = grievanceService.updateGrievance(grievancerequest, userEmail, grievanceId);

        ResponseInfo<String> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);

    }

}
