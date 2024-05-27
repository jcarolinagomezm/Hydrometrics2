package com.service.hydrometrics.models.DTO.graphics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Variable implements Serializable {

    private String dataCamp;
    private List<DataDTO> data = new ArrayList<>();

    public void setData(DataDTO data) {
        this.data.add(data);
    }
}
