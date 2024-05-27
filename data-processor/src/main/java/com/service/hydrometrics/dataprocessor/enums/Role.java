package com.service.hydrometrics.dataprocessor.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER,
    ADMIN,
    AUDITOR;
}
