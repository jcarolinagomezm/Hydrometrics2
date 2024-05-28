package com.service.hydrometrics.models.DB.entity;

import com.service.hydrometrics.models.enums.DataCamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer value;
    @Enumerated(EnumType.STRING)
    private DataCamp dataCamp;
    @ManyToOne
    private WeatherData WeatherData;
    @OneToOne(fetch = FetchType.LAZY)
    private Alert alert;

    public Prediction(Integer value, DataCamp dataCamp, WeatherData weatherDataId, Alert alert) {
        this.value = value;
        this.dataCamp = dataCamp;
        WeatherData = weatherDataId;
        this.alert = alert;
    }

  /*
     Para recibir de Servicio de Prediccion:
     1. El valor de la predicción -> value
     2. La variable de WeatherData a modificar -> dataCamp
     3. El identificador de WeatherData -> WeatherDataId
     */
}
