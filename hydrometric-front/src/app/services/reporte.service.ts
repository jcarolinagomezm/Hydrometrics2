import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WeatherInterface } from '@/models/weather.interface';
@Injectable({
  providedIn: 'root'
})
export class ReporteService {
  public apiUrl: string;

  constructor(
    private toastr: ToastrService,
    private http: HttpClient, 
  ) {
    this.apiUrl = environment.apiUrl + '/alert'
  }

  loadData(): Observable<WeatherInterface[]> {
    try {
      return this.http.get<WeatherInterface[]>(this.apiUrl+'/uncheked')
    } catch (error) {
      this.toastr.error(error.message);
    }
  }

  

  loadPredict(form){
    let url = 'https://xqki41ej46.execute-api.us-east-1.amazonaws.com/dev/meteodata/predict'
    return this.http.post<any>(url, form)
  }


  saveAlert(formWeather){
    try {
      return this.http.put<any>(this.apiUrl+'/confirm', formWeather)
    } catch (error) {
      this.toastr.error(error.message);
    }
  }
}

