export interface UserReportInterface{
    username: string,
    first_name: string,
    last_name: string,
    email: string,
    rola: string,
    enabled: number,
    creation_date: string,
    modification_date: string,
}

export interface dataReportInterface{
    precipitation: number,
    relative_humidity: number,
    solar_radiation: number,
    temperature: number,
    wind_direction: number,
    wind_speed: number,
    date_time: string,
    station_name: string,
    creation_date: string,
    modification_date: string,
    modification_by_user: string
}
