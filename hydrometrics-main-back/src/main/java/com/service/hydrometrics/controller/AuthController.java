package com.service.hydrometrics.controller;

import com.service.hydrometrics.configuration.security.Jwt.JwtService;
import com.service.hydrometrics.models.DB.entity.User;
import com.service.hydrometrics.models.DTO.auth.AuthRequestDTO;
import com.service.hydrometrics.models.DTO.auth.JwtResponseDTO;
import com.service.hydrometrics.services.IUserService;
import com.service.hydrometrics.utils.UtilsMethods;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Login Auth")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(UtilsMethods.validatorBody(bindingResult));
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.getUser(authRequestDTO.getUsername());
        if (!user.isEnabled()) {
            return ResponseEntity.badRequest().build();
        }
        var response = JwtResponseDTO.builder().accessToken(jwtService.generateToken(authRequestDTO.getUsername())).build();
        UtilsMethods.generateAuthLogger(authentication.getName(), request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        Optional<User> user = userService.userByEmail(email);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        if (!user.get().isEnabled()) return ResponseEntity.badRequest().build();
        var token = jwtService.generateTokenResetPassword(user.get().getUsername());
        userService.sendPasswordResetEmail(email, token);
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/new-password")
    public ResponseEntity<?> newPassword(@RequestParam String newPassword, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) return ResponseEntity.badRequest().build();
        String token = authorization.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        User user = userService.getUser(username);
        if (!user.isEnabled()) return ResponseEntity.badRequest().build();
        String newPasswordEncrypt = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordEncrypt);
        userService.updatePassword(user);
        return ResponseEntity.noContent().build();
    }
}
