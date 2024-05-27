package com.service.hydrometrics.models.enums;

public enum DataCamp {
    VELOCIDAD_VIENTO("wind_speed"),
    DIRECCION_VIENTO("wind_direction"),
    PRECIPITACION("precipitation"),
    TEMPERATURA("temperature"),
    RADIACION_SOLAR("solar_radiation"),
    HUMEDAD_RELATIVA("relative_humidity");
    private String englis;

    DataCamp(String english) {
        this.englis = english;
    }
    public String getEnglish(){
        return this.englis;
    }
}
