export interface WeatherInterface{
    alertId: number,
    dataCamp: string,
    weatherDataDTO: DataDTO,
}

export interface DataDTO{
    dateTime: string,
    windSpeed: number,
    windDirection: number,
    precipitation: number,
    temperature: number,
    solarRadiation: number,
    relativeHumidity: number,
    station: station,
}

export interface station {
    id: number,
    name: string,
}

export interface WeatherDataInterface{
    dataCamp: string,
    data: DataInterface,
}

export interface DataInterface{
    dataTime: string,
    value: number,
}

export interface dataCorrelation{
    weatherDataVariable: string,
    promedioVariable: number
}