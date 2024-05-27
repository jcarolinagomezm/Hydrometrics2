import { WeatherInterface } from "@/models/weather.interface";
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, ElementRef, Inject, OnInit } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { UserService } from "@services/user.service";
import { AlertService } from "@services/alert.service";
import { UserAdminInterface } from "@/models/userAdmin.interface";

@Component({
    selector: 'app-modal-user',
    templateUrl: './modalUser.component.html',
    styleUrls: ['./modalUser.component.scss']
})
export class ModalUserComponent implements OnInit { 
    public userForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<ModalUserComponent>, 
        @Inject(MAT_DIALOG_DATA) public data: UserAdminInterface,
        private userService: UserService, 
        private alert: AlertService
    ) {
        this.userForm = new FormGroup({
            id: new FormControl(null),
            username: new FormControl(null, Validators.required),
            password: new FormControl(null),
            firstName: new FormControl(null, Validators.required),
            lastName: new FormControl(null, Validators.required),
            email: new FormControl(null, Validators.required),
            role: new FormControl(null, Validators.required),
            enabled: new FormControl(null, Validators.required),
        })
    }

    ngOnInit(): void {
        this.startForm();
    }

    startForm() {
        if(this.data != null){
            this.userForm.get('username').setValue(this.data.username)
            this.userForm.get('id').setValue(this.data.id)
            this.userForm.get('firstName').setValue(this.data.firstName)
            this.userForm.get('lastName').setValue(this.data.lastName)
            this.userForm.get('email').setValue(this.data.email)
            this.userForm.get('role').setValue(this.data.role)
            this.userForm.get('enabled').setValue(this.data.enabled)
        }else{
            this.userForm.controls['password'].addValidators([Validators.required])
            this.userForm.updateValueAndValidity()
        }
    }

    cancelar(boolean: boolean){
        if(boolean){
            this.dialogRef.close(true);
        }else{
            this.dialogRef.close();
        }
    }

    saveUser(){
        if(this.userForm.valid){
            if(this.userForm.get('id').value == null){
                this.userService.saveUser(this.userForm.value).subscribe({
                    next: (response) =>{
                        this.alert.Success('Exitoso','Usuario Registrado exitosamente');
                        this.cancelar(true)
                    },
                    error: (error) =>{
                        if(error.status == 400){
                            this.alert.Alert(error.error.email);
                        }else if(error.status == 409){
                            this.alert.Alert('El usuario ya existe.');
                        }
                    }
                })
            }else{
                this.userService.updateUser(this.userForm.value).subscribe({
                    next: (response) =>{
                        this.alert.Success('Exitoso','Usuario modificado exitosamente');
                        this.cancelar(true)
                    },
                    error: (error) =>{
                        if(error.status == 400){
                            this.alert.Alert(error.error.email);
                        }
                    }
                })
            }
        }else{
            this.alert.Alert('Complete Correctamente el Formulario');
        }
    }

}