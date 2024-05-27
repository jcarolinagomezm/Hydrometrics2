import {Injectable} from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
    providedIn: 'root'
})
export class AlertService {

    constructor() {
    }

    public Success(tittle, message): void{
        Swal.fire({
            title: tittle,
            text: message,
            icon: 'success',
            showConfirmButton: false,
            timer: 900
        })
    }

    public Alert(message): void{
        Swal.fire({
            title: 'Error',
            text: message,
            icon: 'warning',
            confirmButtonColor: '#3085d6'
        })
    }

    public loading(boolean:boolean):void{
        if(boolean){
            Swal.fire({
                title: 'Cargando...',
                showCancelButton: false,
                showConfirmButton: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            })
        }else{
            Swal.close();
        }
    }
}