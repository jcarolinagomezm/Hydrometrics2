package com.service.hydrometrics.controller;

import com.service.hydrometrics.configuration.security.Jwt.JwtService;
import com.service.hydrometrics.utils.UtilsMethods;
import com.service.hydrometrics.models.DB.entity.User;
import com.service.hydrometrics.models.DTO.auth.AuthRequestDTO;
import com.service.hydrometrics.models.DTO.auth.JwtResponseDTO;
import com.service.hydrometrics.services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login Auth")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(UtilsMethods.validatorBody(bindingResult));
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.getUser(authRequestDTO.getUsername());
        if(!user.isEnabled()) {
            return ResponseEntity.badRequest().build();
        }
        var response = JwtResponseDTO.builder().accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
        UtilsMethods.generateAuthLogger(authentication.getName(), request);
        return ResponseEntity.ok(response);
    }
}
