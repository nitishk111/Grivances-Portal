package io.github.nitishc.grievance.user_service.config;

import io.github.nitishc.grievance.user_service.service.CustomOfficerDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String userEmail = null;
        String jwtoken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            jwtoken = authorizationHeader.substring(7);
            jwtUtil.isTokenExpired(jwtoken);
            userEmail = jwtUtil.extractUserEmail(jwtoken);
        }
        if (userEmail != null) {

            UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);
            if(userDetails.getAuthorities().stream().filter(x -> x.equals("ROLE_OFFICER")).count()==1) {
                userDetails = (CustomOfficerDetails) userDetails;
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

}
