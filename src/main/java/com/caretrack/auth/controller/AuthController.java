package com.caretrack.auth.controller;

import com.caretrack.auth.dto.LoginRequest;
import com.caretrack.auth.dto.LoginResponse;
import com.caretrack.security.JwtProperties;
import com.caretrack.security.JwtService;
import com.caretrack.user.model.Role;
import com.caretrack.user.model.User;
import com.caretrack.user.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          JwtService jwtService,
                          JwtProperties jwtProperties) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * POST /api/auth/login
     * Accepts email + password, authenticates via Spring Security, and returns JWT with user info.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
           //Spring Security’s login OBJECT i.e. DTO ::Contains user enter email + password
            var authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            //✔ This is the actual login-where user dto request i.e.authToken is send to below method to check authentication
            //✔ Spring checks:does user exist?does password match?is account enabled or not?

            //If wrong → throws BadCredentialsException...// .Authenticate user
            //
            //Load user from DB
            //
            //Generate JWT token
            //
            //Create LoginResponse
            //
            //Return LoginResponse to frontend...hereController handles login (NOT service)
            authenticationManager.authenticate(authToken);
//check login credentials from db
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtService.generateToken(user);
//after generating token-->✔ Converts User roles → Set<String>
            Set<String> roleNames = user.getRoles() == null
                    ? Set.of()
                    : user.getRoles().stream().map(Role::name).collect(Collectors.toSet());
//Create UserInfo object for response containing user info defined in AuthUserInfo class under dto login response.java..// .So you are sending the logged-in user's info from the database user object → into the DTO AuthUserInfo.
            var userInfo = new LoginResponse.AuthUserInfo(
                    user.getId(),   //You are literally calling the constructor:
                    user.getEmail(),//new AuthUserInfo(id, email, fullName, roles)
                    user.getFullName(),
                    roleNames
            );
//Build Final Response: containing token,expiration time,user info like:://// :"token": "eyJhbGciOiJIUzI1NiIs...",
//  "expiresIn": 36000,
//  "user": {
//     "id": 1,
//     "email": "abc@test.com",
//     "fullName": "ABC",
//     "roles": ["ADMIN"]
            var response = new LoginResponse(token, jwtProperties.getExpirationSeconds(), userInfo);
            return ResponseEntity.ok(response);
          //User disabled:Wrong password:Any unexpected error:
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User disabled");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
