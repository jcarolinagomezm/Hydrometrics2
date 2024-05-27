import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AlertService } from './alert.service';
import { Observable } from 'rxjs';
import { WeatherInterface } from '@/models/weather.interface';
import { StationInterface } from '@/models/station.interface';

@Injectable({
  providedIn: 'root'
})
export class WeatherDataService {
  public apiUrl: string;

  constructor(private toastr: ToastrService, private http: HttpClient, private alert: AlertService) {
    this.apiUrl = environment.apiUrl
  }

  loadDataStation(): Observable<StationInterface[]> {
    try {
      return this.http.get<StationInterface[]>(this.apiUrl+'/station/')
    } catch (error) {
      this.toastr.error(error.message);
    }
  }


  loadDataWeather(formData){
    try {
      let startDate = formData.dateStart.format('yyyy-MM-DD');
      let endDate = formData.dateEnd.format('yyyy-MM-DD');
      console.log(startDate)
      return this.http.get<any>(this.apiUrl+'/weather/'+formData.codigo,{
        params: {
          dateStart: startDate,
          dateEnd: endDate,
        }
      })
    } catch (error) {
      this.toastr.error(error.message);
    }
  }

  chargeDataWeather(archivo: Blob): Observable<any>{
    try{
      const token = localStorage.getItem('token') || '';
      const headers = new HttpHeaders({
        Authorization: 'Bearer ' + token,
        'Content-Type': 'text/csv',
        Accept: '*/*',
      });
      let url = 'https://xqki41ej46.execute-api.us-east-1.amazonaws.com/dev/meteodata/csv-upload';
      return this.http.post<any>(url,archivo, {headers})
    }catch (error){
      this.toastr.error(error.message);
    }
  }
}