package com.service.hydrometrics.models.DB.entity;

import com.service.hydrometrics.models.enums.DataCamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.envers.RelationTargetNotFoundAction;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED, targetNotFoundAction = RelationTargetNotFoundAction.DEFAULT)
@RequiredArgsConstructor
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Timestamp dateTime;
    private Double windSpeed;
    private Double windDirection;
    private Double precipitation;
    private Double temperature;
    private Double solarRadiation;
    private Double relativeHumidity;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    private Station station;

    public Double getValueVariable(DataCamp dataCamp) {
        switch (dataCamp) {
            case PRECIPITACION -> {
                return this.getPrecipitation();
            }
            case TEMPERATURA -> {
                return this.getTemperature();
            }
            case RADIACION_SOLAR -> {
                return this.getSolarRadiation();
            }
            case HUMEDAD_RELATIVA -> {
                return this.getRelativeHumidity();
            }
            case DIRECCION_VIENTO -> {
                return this.getWindDirection();
            }
            case VELOCIDAD_VIENTO -> {
                return this.getWindSpeed();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "WeatherData{" + "id=" + id + ", dateTime=" + dateTime + ", windSpeed=" + windSpeed + ", windDirection=" + windDirection + ", precipitation=" + precipitation + ", temperature=" + temperature + ", solarRadiation=" + solarRadiation + ", relativeHumidity=" + relativeHumidity + ", station=" + station + '}';
    }
}
