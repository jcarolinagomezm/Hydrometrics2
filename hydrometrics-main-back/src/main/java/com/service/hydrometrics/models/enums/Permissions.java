package com.service.hydrometrics.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    //* ADMIN
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    //* AUDITOR
    AUDITOR_READ("auditor:read"),
    AUDITOR_UPDATE("auditor:update");

    @Getter
    private final String permission;
    }
