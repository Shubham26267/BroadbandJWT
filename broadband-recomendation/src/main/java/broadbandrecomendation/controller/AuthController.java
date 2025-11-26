package broadbandrecomendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import broadbandrecomendation.model.Role;
import broadbandrecomendation.model.User;
import broadbandrecomendation.payload.AuthRequest;
import broadbandrecomendation.payload.AuthResponse;
import broadbandrecomendation.security.JwtTokenProvider;
import broadbandrecomendation.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserService userService;

    // -------------------- LOGIN ----------------------
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getIdentifier(),
                        loginRequest.getPassword()
                )
        );

        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String token = tokenProvider.generateToken(username, role);

        AuthResponse response = new AuthResponse(token, username, role);

        return ResponseEntity.ok(response);
    }

    // -------------------- REGISTER CUSTOMER ----------------------
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setRole(Role.CUSTOMER);
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }
}