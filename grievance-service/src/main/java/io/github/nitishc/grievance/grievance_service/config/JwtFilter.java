package io.github.nitishc.grievance.grievance_service.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String jwtoken = null;

        try{
            jwtoken = authorizationHeader.substring(7);
            jwtUtil.isTokenExpired(jwtoken);
            Claims claims = jwtUtil.extractAllClaims(jwtoken);
            String userEmail= claims.get("sub", String.class);
            String role= claims.get("role", String.class);

            String department= claims.get("department", String.class);

            SimpleGrantedAuthority authority= new SimpleGrantedAuthority(role);
            UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority(role), new SimpleGrantedAuthority(department)));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }catch (Exception e){
            throw new RuntimeException("Invalid token");
        }

        filterChain.doFilter(request, response);
    }

}
