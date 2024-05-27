package com.service.hydrometrics.dataprocessor.enver;

import com.service.hydrometrics.dataprocessor.controller.DataProcessorController;
import com.service.hydrometrics.dataprocessor.entity.User;
import com.service.hydrometrics.dataprocessor.repository.UserRepository;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRevisionListener implements RevisionListener {

    private final UserRepository userRepository;
    private final DataProcessorController dataProcessorController;

    @Autowired
    public UserRevisionListener(@Lazy UserRepository userRepository, @Lazy DataProcessorController dataProcessorController) {
        this.userRepository = userRepository;
        this.dataProcessorController = dataProcessorController;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity customRevisionEntity = (AuditRevisionEntity) revisionEntity;
        String username = dataProcessorController.getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) customRevisionEntity.setUser(null);
        else customRevisionEntity.setUser(user.get());
    }
}
