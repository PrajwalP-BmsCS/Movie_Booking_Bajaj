package com.cinema_package.cinema_project.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cinema_package.cinema_project.user.User;
import com.cinema_package.cinema_project.user.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public JwtFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String email = JwtUtil.validateTokenAndGetEmail(token);
                System.out.println("JWT validated for: " + email);

                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // 👇 Convert role to authority
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                null,
                                List.of(authority)
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
