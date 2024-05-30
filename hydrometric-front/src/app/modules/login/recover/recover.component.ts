import { Component, OnInit, OnDestroy, Renderer2, HostBinding} from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AppService } from '@services/app.service';
import { AlertService } from '@services/alert.service';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
    selector: 'app-recover',
    templateUrl: './recover.component.html',
    styleUrls: ['./recover.component.scss']
})
export class RecoverComponent {
    public token: string;
    public recoverForm: FormGroup;
    constructor(
        private renderer: Renderer2,
        private appService: AppService,
        private alert: AlertService,
        private ruta: ActivatedRoute
    ) { 
        this.recoverForm = new FormGroup({
            newPassword: new FormControl(null, Validators.required ),
        });
    }

    ngOnInit() {
        this.ruta.params.subscribe(
            (params: Params) => {
                this.token = params.token
                console.log(this.token)
            }
        )
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }
  
    async recoverPassword(){
        this.alert.loading(true);
        if(this.recoverForm.valid){
            await this.appService.recoverPassword(this.recoverForm.controls['newPassword'].value, this.token)
            this.alert.loading(true);
        }else{
            this.alert.Alert('Complete el formulario.')
        }
    }
 }
