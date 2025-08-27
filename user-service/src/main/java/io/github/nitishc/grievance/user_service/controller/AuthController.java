package io.github.nitishc.grievance.user_service.controller;

import io.github.nitishc.grievance.user_service.config.JwtUtil;
import io.github.nitishc.grievance.user_service.dto.UserLoginRequest;
import io.github.nitishc.grievance.user_service.dto.UserResponse;
import io.github.nitishc.grievance.user_service.dto.UserSignupRequest;
import io.github.nitishc.grievance.user_service.exception.UserNotSavedException;
import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.service.CustomOfficerDetails;
import io.github.nitishc.grievance.user_service.service.UserService;
import io.github.nitishc.grievance.user_service.util.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class AuthController {


    @Autowired
    private  UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/officer-login")
    public ResponseEntity<ResponseInfo<String>> OfficerLogin(@RequestBody UserLoginRequest officerDto, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(officerDto.getEmail(), officerDto.getPassword()));

            CustomOfficerDetails officerDetails =(CustomOfficerDetails) userDetailsService.loadUserByUsername(officerDto.getEmail());
            if(!officerDetails.getAuthorities().stream().anyMatch(auth-> auth.getAuthority().equals("ROLE_OFFICER") || auth.getAuthority().equals("ROLE_ADMIN"))){
                throw new Exception();
            }
            String token = jwtUtil.generateToken(officerDto.getEmail(), officerDetails.getAuthorities().iterator().next().toString(),
                    officerDetails.getDepartment());

            ResponseInfo<String> loginSuccessful = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                    token, request.getRequestURI());
            return new ResponseEntity<>(loginSuccessful, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            ResponseInfo<String> loginSuccessful = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                    "Please enter correct credentials. Or make sure you are already registered", request.getRequestURI());
            return new ResponseEntity<>(loginSuccessful, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseInfo<UserResponse>> addUser(
            @Validated @RequestBody UserSignupRequest userSignup, HttpServletRequest request)
            throws UserNotSavedException {
        log.info("Request Received to save new user record");
        UserResponse userResponse = userService.userSignup(userSignup);
        ResponseInfo<UserResponse> responseInfo = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                userResponse, request.getRequestURI());
        return new ResponseEntity<>(responseInfo, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseInfo<String>> UserLogin(@RequestBody UserLoginRequest userDto, HttpServletRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            UserDetails userDetails= userDetailsService.loadUserByUsername(userDto.getEmail());
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//            for(GrantedAuthority auth : authorities){
//                System.out.println("Comparing:"+ auth.getAuthority());
//                if("ROLE_CITIZEN".equals(auth.getAuthority())){
//                    System.out.println("Mathced");
//                }
//            }
            if(!userDetails.getAuthorities().stream().anyMatch(auth-> auth.getAuthority().equals("ROLE_CITIZEN"))){
                throw new Exception();
            }
            String token = jwtUtil.generateToken(userDto.getEmail(),userDetails.getAuthorities().iterator().next().toString());

            ResponseInfo<String> loginSuccessful = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                    token, request.getRequestURI());
            return new ResponseEntity<>(loginSuccessful, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            ResponseInfo<String> loginSuccessful = new ResponseInfo<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(),
                    "Please enter correct credentials. Or make sure you are already registered", request.getRequestURI());
            return new ResponseEntity<>(loginSuccessful, HttpStatus.UNAUTHORIZED);
        }
    }
}
