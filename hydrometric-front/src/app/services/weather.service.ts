import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AlertService } from './alert.service';
import { UserInterface } from '@/models/user.interface';
import { Observable } from 'rxjs';
import { WeatherInterface, dataCorrelation } from '@/models/weather.interface';
import { dataReportInterface } from '@/models/report.interface';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  public apiUrl: string;

  constructor(private toastr: ToastrService, private http: HttpClient, private alert: AlertService) {
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

  loadCorrelation(predict,station,datacamp): Observable<dataCorrelation[]>{
    const dataCorrelation = {
      predictionValue: predict,
      stationId: station,
      dataCamp: datacamp
    }
    return this.http.post<dataCorrelation[]>(this.apiUrl+'/correlation',dataCorrelation)
  }

  saveAlert(formWeather){
    try {
      return this.http.put<any>(this.apiUrl+'/confirm', formWeather)
    } catch (error) {
      this.toastr.error(error.message);
    }
  }

  loadReport(formData): Observable<any>{
    try{
      let startDate = formData.startDate.format('yyyy-MM-DD HH:mm:ss')
      let endDate = formData.endDate.format('yyyy-MM-DD HH:mm:ss')
      return this.http.get<dataReportInterface[]>(environment.apiUrl+'/weather/report',{
        params:{
          stationId: formData.stationId,
          startDate: startDate,
          endDate: endDate,
        }
      })
    }catch(error){
      this.toastr.error(error.message)
    }
  }


}