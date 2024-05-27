package com.service.hydrometrics.controller;

import com.service.hydrometrics.utils.UtilsMethods;
import com.service.hydrometrics.models.DB.entity.User;
import com.service.hydrometrics.models.DTO.user.CreateUserDTO;
import com.service.hydrometrics.models.DTO.user.UserDTO;
import com.service.hydrometrics.services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Tag(name = "User resources")
public class UserController {

    private final IUserService service;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody @Valid CreateUserDTO createUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(UtilsMethods.validatorBody(bindingResult));
        }
        User user = new User(createUserDTO);
        if (service.userExist(user)) {
            return ResponseEntity.status(409).body("User already exists");
        }
        return ResponseEntity.status(202).body(service.saveUser(user));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(UtilsMethods.validatorBody(bindingResult));
        }
        User userUpdate = new User(userDTO);
        if (!service.userExist(userUpdate)) {
            return ResponseEntity.status(409).body("User does not exist");
        }
        return ResponseEntity.ok(service.updateUser(userUpdate));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUDITOR','ROLE_USER')")
    @GetMapping("/get")
    public ResponseEntity<?> getUser() {
        User user = service.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUDITOR','ROLE_USER')")
    @GetMapping("/report")
    public ResponseEntity<?> getUserReport() {
        return ResponseEntity.ok(service.getUserReports());
    }
}

