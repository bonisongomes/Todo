package com.github.bonison.to_do_app.security;

import com.github.bonison.to_do_app.config.JwtUtil;
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
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization header : " + authorizationHeader);
        String jwt = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt); // ✅ Extract username from JWT
            } catch (ExpiredJwtException e) {
                System.out.println(" JWT Token has expired");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
                return;
            } catch (MalformedJwtException e) {
                System.out.println(" Invalid JWT Token: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(" JWT claims string is empty");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT claims string is empty");
                return;
            } catch (Exception e) {
                System.out.println(" Error extracting JWT Token: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error extracting JWT Token");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        System.out.println("🔹 Request Passed Through Filter");
        try {
            chain.doFilter(request, response);
            System.out.println("Request Passed Successfully Through Filter");
        } catch (Exception e) {
            System.out.println("Error occurred at chain.doFilter(): " + e.getMessage());
            e.printStackTrace();
        }
    }

}
