package com.service.hydrometrics.dataprocessor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "WeatherData{" + "id=" + id + ", dateTime=" + dateTime + ", windSpeed=" + windSpeed + ", windDirection=" + windDirection + ", precipitation=" + precipitation + ", temperature=" + temperature + ", solarRadiation=" + solarRadiation + ", relativeHumidity=" + relativeHumidity + ", station=" + station + '}';
    }
}
