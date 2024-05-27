package com.service.hydrometrics.models.DB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.hydrometrics.models.DTO.user.CreateUserDTO;
import com.service.hydrometrics.models.DTO.user.UserDTO;
import com.service.hydrometrics.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Getter
@Setter
@Entity
@ToString
@Audited
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(CreateUserDTO createUserDTO) {
        this.username = createUserDTO.getUsername();
        this.password = createUserDTO.getPassword();
        this.firstName = createUserDTO.getFirstName();
        this.lastName = createUserDTO.getLastName();
        this.email = createUserDTO.getEmail();
        this.role = Role.valueOf(createUserDTO.getRole().toUpperCase());
        this.enabled = createUserDTO.getEnabled() != 0;
    }

    public User(UserDTO createUserDTO) {
        this.username = createUserDTO.getUsername();
        this.firstName = createUserDTO.getFirstName();
        this.lastName = createUserDTO.getLastName();
        this.email = createUserDTO.getEmail();
        this.role = Role.valueOf(createUserDTO.getRole().toUpperCase());
        this.enabled = createUserDTO.getEnabled() != 0;
    }

    public User(String username, String password, String firstName, String lastName, String email, Role role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }
}
