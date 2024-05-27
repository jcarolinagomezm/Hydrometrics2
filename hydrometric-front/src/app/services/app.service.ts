import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';
import { AlertService } from './alert.service';
import { UserInterface } from '@/models/user.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  public apiUrl: string;
  public user: UserInterface;

  constructor(private router: Router, private toastr: ToastrService, private http: HttpClient, private alert: AlertService) {
    this.apiUrl = environment.apiUrl + '/login'
  }

  loginByAuth(formLogin): void {
    try {
      this.http.post<any>(this.apiUrl, formLogin).subscribe({
        next: (response) => {
          localStorage.setItem('token', response.accessToken)
          this.getProfile();
          this.router.navigate(['/inicio']);
        },
        error: (error) => {
          this.alert.Alert('Error de Credenciales. Intente nuevamente');
        },
        complete: () =>{
          this.alert.Success('Bienvenido','Bienvenido');
        }
      })
    } catch (error) {
      this.toastr.error(error.message);
    }
  }

  getProfile(): Promise<void>{
    try {
      return new Promise((resolve) => {
        setTimeout(() => {
          this.http.get<UserInterface>(environment.apiUrl + '/user/get').subscribe({
          next: (response) => {
            localStorage.setItem('user',JSON.stringify(response));
          },
          error: (error) => {
            this.logout();
          }
        })
          resolve();
        }, 500);
      });
    }catch(error){
      this.toastr.error(error.message);
    }
  }

  public logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}