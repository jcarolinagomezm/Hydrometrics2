import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';
import { AlertService } from './alert.service';
import { Observable } from 'rxjs';
import { UserAdminInterface } from '@/models/userAdmin.interface';
import { UserReportInterface } from '@/models/report.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public apiUrl: string;

  constructor(private toastr: ToastrService, private http: HttpClient, private alert: AlertService) {
    this.apiUrl = environment.apiUrl + '/user'
  }

  loadUser(): Observable<UserAdminInterface[]> {
    try {
      return this.http.get<UserAdminInterface[]>(this.apiUrl+'/')
    } catch (error) {
      this.toastr.error(error.message);
    }
  }

  saveUser(form){
    try{
      return this.http.post<any>(this.apiUrl+'/register', form)
    }catch(error){
      this.toastr.error(error.message);
    }
  }

  updateUser(form){
    try{
      return this.http.put<any>(this.apiUrl+'/update',form)
    }catch(error){
      this.toastr.error(error.message);
    }
  }

  loadReport(): Observable<UserReportInterface[]>{
    try{
      return this.http.get<UserReportInterface[]>(this.apiUrl+'/report')
    }catch(error){
      this.toastr.error(error.message);
    }
  }

}