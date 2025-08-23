package io.github.nitishc.grievance.user_service.controller.controllerImpl;

import io.github.nitishc.grievance.user_service.controller.OfficerController;
import io.github.nitishc.grievance.user_service.dto.RequestOfficerDto;
import io.github.nitishc.grievance.user_service.dto.ResponseOfficerDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.service.OfficerService;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/officer")
@Slf4j
public class OfficerControllerImpl  implements OfficerController {

    private final OfficerService officerService;

    @Autowired
    public OfficerControllerImpl(OfficerService officerService){
        this.officerService= officerService;
    }

    @Override
    @PostMapping("/save")
    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> addOfficer(@Valid @RequestBody RequestOfficerDto officerDto,
                                                                       HttpServletRequest request) throws UserNotSavedException {
        log.info("Request Received to save new officer record");
        ResponseOfficerDto officerdto= officerService.addOfficer(officerDto);
        ResponseInfo<ResponseOfficerDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerdto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);

    }

    @Override
    @GetMapping("email/{email}")
    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> getOfficerByEmail(@PathVariable("email") String email,
                                                                              HttpServletRequest request) throws UserNotFoundException {
        log.info("Request received to fetch record of via email: {}",email);
        ResponseOfficerDto officerDto= officerService.getOfficerByEmail(email);
        ResponseInfo<ResponseOfficerDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @GetMapping("phone/{phone}")
    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> getOfficerByPhone(@PathVariable("phone") String phone,
                                                                              HttpServletRequest request) throws UserNotFoundException {
        log.info("Request received to fetch record of via phone: {}",phone);
        ResponseOfficerDto officerDto= officerService.getOfficerByPhone(phone);
        ResponseInfo<ResponseOfficerDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @PatchMapping("update/{email}")
    public ResponseEntity<ResponseInfo<ResponseOfficerDto>> updateOfficer(@PathVariable("email") String email, @RequestBody RequestOfficerDto officer,
                                                                          HttpServletRequest request) throws UserNotFoundException, UserNotSavedException {
        log.info("User with email: {}, requested to update record", email);
        ResponseOfficerDto officerDto= officerService.updateOfficer(email, officer);
        ResponseInfo<ResponseOfficerDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                officerDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @DeleteMapping("delete/{email}")
    public ResponseEntity<ResponseInfo<String>> deleteOfficer(@PathVariable("email") String email,
                                                              HttpServletRequest request) throws UserNotFoundException, UserNotDeletedException {
        log.info("User with email: {}, requested to delete record",email);
        officerService.deleteOfficer(email);
        ResponseInfo<String> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "Record Deleted", request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }
}
