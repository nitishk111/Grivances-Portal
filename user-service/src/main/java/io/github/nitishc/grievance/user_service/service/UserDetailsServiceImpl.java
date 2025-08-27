package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.model.User;
import io.github.nitishc.grievance.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user==null) throw new UsernameNotFoundException("Email is not registered");

        if(user.getDepartment()==null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
//                .roles(user.getRole().toString())
                    .authorities(new SimpleGrantedAuthority(user.getRole().toString()))
                    .build();
        }
        else{
            return new CustomOfficerDetails(user);
        }
    }
}
