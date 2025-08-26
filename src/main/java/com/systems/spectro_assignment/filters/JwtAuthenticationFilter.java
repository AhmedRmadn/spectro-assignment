package com.systems.spectro_assignment.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systems.spectro_assignment.responses.ApiResponse;
import com.systems.spectro_assignment.security.AuthUserDetailsService;
import com.systems.spectro_assignment.security.JwtUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final AuthUserDetailsService userDetailsService;

	@Autowired
	public JwtAuthenticationFilter(JwtUtil jwtService, AuthUserDetailsService userDetailsService) {
		this.jwtUtil = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(
	        @NonNull HttpServletRequest request,
	        @NonNull HttpServletResponse response,
	        @NonNull FilterChain filterChain
	) throws ServletException, IOException {

	    if (request.getServletPath().contains("/api/auth")||request.getServletPath().contains("/h2-console")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    final String authHeader = request.getHeader("Authorization");

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        writeErrorResponse(response, "Missing or invalid Authorization header", HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }

	    final String jwt = authHeader.substring(7);

	    if (!jwtUtil.validateJwtToken(jwt)) {
	        writeErrorResponse(response, "Invalid or expired JWT token", HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }

	    final String userName = jwtUtil.extractUsername(jwt);

	    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

	        if (userDetails == null) {
	            writeErrorResponse(response, "User not found", HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }

	        if (!jwtUtil.isTokenValid(jwt, userDetails)) {
	            writeErrorResponse(response, "JWT token does not match user details", HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }

	        UsernamePasswordAuthenticationToken authToken =
	                new UsernamePasswordAuthenticationToken(
	                        userDetails,
	                        null,
	                        userDetails.getAuthorities()
	                );
	        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	        SecurityContextHolder.getContext().setAuthentication(authToken);
	    }

	    filterChain.doFilter(request, response);
	}
	
	private void writeErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
	    response.setStatus(status);
	    response.setContentType("application/json");

	    ApiResponse<Object> apiResponse = new ApiResponse<>();
	    apiResponse.setSuccess(false);
	    apiResponse.setMessage(message);
	    apiResponse.setData(null);

	    ObjectMapper mapper = new ObjectMapper();
	    response.getWriter().write(mapper.writeValueAsString(apiResponse));
	}


}
