import { Component, OnInit, OnDestroy, Renderer2, HostBinding} from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AppService } from '@services/app.service';
import { AlertService } from '@services/alert.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-forgot',
    templateUrl: './forgot.component.html',
    styleUrls: ['./forgot.component.scss']
})
export class ForgotComponent implements OnInit{
    public forgotForm: FormGroup;

    constructor(
        private router: Router,
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private alert: AlertService,
    ) { 
        this.forgotForm = new FormGroup({
            email: new FormControl(null, Validators.required),
        });
    }

    ngOnInit() {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }
  
    forgotPassword(){

    }
}