package com.service.hydrometrics.models.DB.enver;

import com.service.hydrometrics.models.DB.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Getter
@Setter
@Entity
@Table(name = "audit_revision")
@RevisionEntity(UserRevisionListener.class)
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @ManyToOne
    private User user;
}