package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomOfficerDetails implements UserDetails {

    private String username;
    private String password;
    private String department;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomOfficerDetails(User user){
        this.username= user.getEmail();
        this.password=user.getPassword();
        this.department=user.getDepartment().toString();
        this.authorities= List.of(new SimpleGrantedAuthority(user.getRole().toString()));

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getDepartment(){
        return this.department;
    }
}
