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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserGrievanceController {

    private final GrievanceService grievanceService;

    @Autowired
    public UserGrievanceController(GrievanceService grievanceService){
        this.grievanceService=grievanceService;
    }

    @PostMapping("/register-grievance/{user-id}")
    public ResponseEntity<ResponseInfo<String>> registerGrievance(@RequestBody GrievanceRequest grievanceRequest, @PathVariable("user-id")
    long userId, HttpServletRequest request) throws GrievanceNotSavedException, DatabaseConstraintVoilation {

        String message= grievanceService.saveGrievance(grievanceRequest, userId);

        ResponseInfo<String> rInfo= new ResponseInfo(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.ACCEPTED);
    }

    @GetMapping("/grievances/{user-id}")
    public ResponseEntity<ResponseInfo<List<GrievanceResponse>>> getGrievancesByUser(@PathVariable("user-id") long userId, HttpServletRequest request) throws GrievanceNotFoundException {

        List<GrievanceResponse> grievanceByUser = grievanceService.getGrievanceByUser(userId);

        ResponseInfo<List<GrievanceResponse>> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                grievanceByUser, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);
    }

    @PostMapping("/comment/{user-id}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> postComment(@RequestBody CommentRequest commentRequest, @PathVariable("user-id") long userId,
                            @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = grievanceService.addComment(userId, grievanceId, commentRequest);

        ResponseInfo<String> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.ACCEPTED);
    }

    @PostMapping("/update-grievance/{user-id}/{grievance-id}")
    public ResponseEntity<ResponseInfo<String>> updateGrievance(@RequestBody GrievanceRequest grievancerequest, @PathVariable("user-id") long userId,
                                @PathVariable("grievance-id") long grievanceId, HttpServletRequest request) throws DatabaseConstraintVoilation, GrievanceNotFoundException {

        String message = grievanceService.updateGrievance(grievancerequest, userId, grievanceId);

        ResponseInfo<String> rInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                message, request.getRequestURI());

        return new ResponseEntity<>(rInfo, HttpStatus.FOUND);

    }

}
