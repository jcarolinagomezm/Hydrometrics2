package com.service.hydrometrics.models.DB.enver;

import com.service.hydrometrics.repository.UserRepository;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserRevisionListener implements RevisionListener {

    private final UserRepository userRepository;

    @Autowired
    public UserRevisionListener(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity customRevisionEntity = (AuditRevisionEntity) revisionEntity;
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            customRevisionEntity.setUser(null);
        } else {
            String username = context.getAuthentication().getName();
            customRevisionEntity.setUser(userRepository.findByUsername(username));
        }
    }
}