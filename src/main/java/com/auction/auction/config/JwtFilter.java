package com.auction.auction.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.service.AuthenticateService;
import com.auction.auction.security.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            var data = jwtUtil.getAllClaimsFromToken(token);
            username = data.get("sub").toString();
            role = data.get("role").toString();
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            if (role.equals("CUSTOMER")) {
                authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
                var customer = authenticateService.authenticateCustomerFilter(username);
                userDetails = new User(customer.getUsername(), customer.getPassword(), authorities);
            } else if (role.equals("EMPLOYEE")) {
                authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
                var employee = authenticateService.authenticateCustomerFilter(username);
                userDetails = new User(employee.getUsername(), employee.getPassword(), authorities);
            } else {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while Filter Internal");
            }

            if (jwtUtil.validToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
