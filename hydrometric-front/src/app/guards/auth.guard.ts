import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AppService } from '@services/app.service';
import { Location } from '@angular/common';
import { AlertService } from '@services/alert.service';


@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(
        private router: Router,
        private appService: AppService,
        private alert: AlertService,
    ) { }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.getProfile();
    }

    async canActivateChild(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        await this.getProfile();
        const url: string = state.url
        let user = JSON.parse(localStorage.getItem('user'))
        if(user.role == 'ADMIN' && (url == "/inicio" || url == "/alerts" || url == "/user" || url == "/dataEstaciones" || url == "/reportes"  || url == "/alerts-report"  || url == "/cargarDatos") ){
            return true;
        }else if(user.role == 'USER' && (url == "/inicio" || url == "/dataEstaciones" || url == "/reportes" )){
            return true;
        }else if(user.role == 'AUDITOR' && (url == "/inicio" || url == "/alerts" || url == "/dataEstaciones"  || url == "/reportes" || url == "/alerts-report")){
            return true;
        }else{
            this.alert.Alert('Acceso Denegado.')
            this.appService.logout();
            return false;
        }
    }

    async getProfile() {
        try {
            await this.appService.getProfile();
            return true;
        } catch (error) {
            return false;
        }
    }
}