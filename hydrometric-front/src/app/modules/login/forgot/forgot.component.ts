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
    public number: number = 1;

    constructor(
        private router: Router,
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private alert: AlertService,
    ) { 
        this.forgotForm = new FormGroup({
            email: new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')]) ),
        });
    }

    ngOnInit() {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }
  
    async forgotPassword(){
        this.alert.loading(true);
        if(this.forgotForm.valid){
            await this.appService.forgotPassword(this.forgotForm.controls['email'].value).subscribe({
                next: (response) => {
                    this.number = 2;
                    this.alert.loading(false);
                },
                error: (error) =>{
                    if(error.status == 404){
                        this.alert.Alert('El correo eléctronico no esta registrado.')
                    }else{
                        this.alert.Alert('Error al enviar el correo eléctronico.')
                    }
                }
            })
        }else{
            this.alert.Alert('Correo eléctronico incorrecto.')
        }
    }
}