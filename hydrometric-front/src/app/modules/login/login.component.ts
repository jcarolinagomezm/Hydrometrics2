import { Component, OnInit, OnDestroy, Renderer2, HostBinding} from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AppService } from '@services/app.service';
import { AlertService } from '@services/alert.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
    @HostBinding('class') class = 'login-box';
    public loginForm: FormGroup;

    constructor(
        private router: Router,
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private alert: AlertService,
    ) { 
        this.loginForm = new FormGroup({
            username: new FormControl(null, Validators.required),
            password: new FormControl(null, Validators.required)
        });
    }

    ngOnInit() {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }

    ngOnDestroy() {
        this.renderer.removeClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }

    async loginByAuth() {
        if (this.loginForm.valid) {
            this.alert.loading(true);
            await this.appService.loginByAuth(this.loginForm.value);
        } else {
            this.toastr.error('Diligencie el formulario correctamente.');
        }
    }

    
}
