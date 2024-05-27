import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AlertService } from "@services/alert.service";
import { WeatherDataService } from "@services/weatherData.service";

@Component({
    selector: 'app-cargar-datos',
    templateUrl: './cargarDatos.component.html',
    styleUrls: ['./cargarDatos.component.scss']
})
export class CargarDatosComponent {
    public dataForm: FormGroup;
    public file: any;

    constructor(
        private alert: AlertService,
        private weatherService: WeatherDataService,
    ) {
        this.dataForm = new FormGroup({
            archivo: new FormControl (null, Validators.required)
        })

    }

    chargeArchivo(event){
        let archivo = event.target.files[0]
        if (archivo.type != 'text/csv' || (archivo.size/1024/1024) >= 80){
            this.alert.Alert('Archivo Incorrecto. Solo archivos .csv y menor a 80 MB')
            this.dataForm.reset()
        }else{
            this.file = archivo
        }
    }

    async chargeData(){
        this.alert.loading(true)
        await this.weatherService.chargeDataWeather(this.file).subscribe({
            next: (response) =>{
                if(response.success){
                    this.alert.Success('Cargue Exitoso','Se cargaron las variables exitosamente.')
                }else{
                    this.alert.Alert('No se pudo cargar los datos.')
                }
            },
            error: (error) =>{
                this.alert.Alert('Intente nuevamente.')
            }
            
        })
        
    }
}