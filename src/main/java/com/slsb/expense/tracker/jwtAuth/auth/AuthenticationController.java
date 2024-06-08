package com.slsb.expense.tracker.jwtAuth.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            HttpServletRequest request,
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(service.register(request, registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            HttpServletRequest request,
            @RequestBody AuthenticationRequest loginRequest
    ) {
        return ResponseEntity.ok(service.authenticate(request, loginRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticateRequest
    ) {
        return ResponseEntity.ok(service.authenticate(request, authenticateRequest));
    }
}
