package com.service.hydrometrics.models.DTO.graphics;

import java.io.Serializable;

public record DataDTO(String dateTime, Integer value) implements Serializable {

}
