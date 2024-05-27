import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AppService } from '@services/app.service';
import { Location } from '@angular/common';


@Injectable({
    providedIn: 'root'
})
export class RolGuard implements CanActivate {
    constructor(
        private router: Router,
        private appService: AppService,
        private location: Location
    ) { }

    canActivate(): | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        let user = JSON.parse(localStorage.getItem('user'))
        if(user.role == "admin")
        return this.getProfile();
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
