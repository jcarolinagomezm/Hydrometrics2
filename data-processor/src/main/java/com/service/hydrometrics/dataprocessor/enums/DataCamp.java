package com.service.hydrometrics.dataprocessor.enums;

public enum DataCamp {
    VELOCIDAD_VIENTO("wind_speed"),
    DIRECCION_VIENTO("wind_direction"),
    PRECIPITACION("precipitation"),
    TEMPERATURA("temperature"),
    RADIACION_SOLAR("solar_radiation"),
    HUMEDAD_RELATIVA("relative_humidity");
    private String english;

    DataCamp(String english) {
        this.english = english;
    }

    public String getEnglish() {
        return this.english;
    }
}
