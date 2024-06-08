package com.slsb.expense.tracker.jwtAuth.auth;

import com.slsb.expense.tracker.jwtAuth.config.JwtService;
import com.slsb.expense.tracker.jwtAuth.user.Role;
import com.slsb.expense.tracker.jwtAuth.user.User;
import com.slsb.expense.tracker.jwtAuth.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(HttpServletRequest request, RegisterRequest registerRequest) {
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .birthdate(registerRequest.getBirthdate())
                .role(Role.USER)
                .createdByIp(request.getRemoteAddr())
                .createdDate(new Date())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        User registered_user = userRepository.findByEmail(registerRequest.getEmail()).orElseThrow();

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(registered_user.getEmail())
                .userId(registered_user.getUserId())
                .build();
    }

    public AuthenticationResponse authenticate(HttpServletRequest request, AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }
}
