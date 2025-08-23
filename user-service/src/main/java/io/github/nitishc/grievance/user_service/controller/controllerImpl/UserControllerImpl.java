package io.github.nitishc.grievance.user_service.controller.controllerImpl;

import io.github.nitishc.grievance.user_service.controller.UserController;
import io.github.nitishc.grievance.user_service.dto.RequestUserDto;
import io.github.nitishc.grievance.user_service.dto.ResponseUserDto;
import io.github.nitishc.grievance.user_service.exception.UserNotDeletedException;
import io.github.nitishc.grievance.user_service.exception.UserNotFoundException;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.service.UserService;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    @PostMapping("/save")
    public ResponseEntity<ResponseInfo<ResponseUserDto>> addUser(@Validated @RequestBody RequestUserDto userdto, HttpServletRequest request)
            throws UserNotSavedException {
        log.info("Request Received to save new user record");
        ResponseUserDto userDto= userService.addUser(userdto);
        ResponseInfo<ResponseUserDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @GetMapping("email/{email}")
    public ResponseEntity<ResponseInfo<ResponseUserDto>> getUserByEmail(@PathVariable("email") String email, HttpServletRequest request)
            throws UserNotFoundException {
        log.info("Request received to fetch record of via email: {}",email);
        ResponseUserDto userDto= userService.getUserByEmail(email);
        ResponseInfo<ResponseUserDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @GetMapping("phone/{phone}")
    public ResponseEntity<ResponseInfo<ResponseUserDto>> getUserByPhone(@PathVariable("phone") String phone, HttpServletRequest request)
            throws UserNotFoundException {
        log.info("Request received to fetch record of via phone: {}",phone);
        ResponseUserDto userDto =userService.getUserByPhone(phone);
        ResponseInfo<ResponseUserDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @PatchMapping("update/{email}")
    public ResponseEntity<ResponseInfo<ResponseUserDto>> updateUser(@PathVariable("email") String email, @RequestBody RequestUserDto user, HttpServletRequest request)
            throws UserNotFoundException, UserNotSavedException {
        log.info("User with email: {}, requested to update record", email);
        ResponseUserDto userDto= userService.updateUser(email, user);
        ResponseInfo<ResponseUserDto> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userDto, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @Override
    @DeleteMapping("delete/{email}")
    public ResponseEntity<ResponseInfo<String>> deleteUser(@PathVariable("email") String email, HttpServletRequest request) throws UserNotFoundException, UserNotDeletedException {
        log.info("User with email: {}, requested to delete record",email);
        userService.deleteUser(email);
        ResponseInfo<String> responseInfo= new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                "Record Deleted", request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }
}
