package com.service.hydrometrics.dataprocessor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.hydrometrics.dataprocessor.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

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
}
