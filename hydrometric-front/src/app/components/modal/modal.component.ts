import { WeatherInterface } from "@/models/weather.interface";
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, ElementRef, Inject, OnInit } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { WeatherService } from "@services/weather.service";
import { dataCorrelation } from "@/models/weather.interface";
import { AlertService } from "@services/alert.service";

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
    public weatherForm: FormGroup;
    public predictForm: FormGroup;
    public correlationForm: FormGroup;
    public prediction: number;
    public show: boolean = false;
    public dictionary = traduction;
    public correlation: dataCorrelation[] = []


    constructor(
        public dialogRef: MatDialogRef<ModalComponent>,
        @Inject(MAT_DIALOG_DATA) public data: WeatherInterface,
        private elementRef: ElementRef,
        private weatherService: WeatherService,
        private alert: AlertService
    ) {
        this.weatherForm = new FormGroup({
            alertId: new FormControl(null, Validators.required),
            dataCamp: new FormControl(null, Validators.required),
            prediction: new FormControl(null, Validators.required),
        })

        this.predictForm = new FormGroup({
            station: new FormControl(null, Validators.required),
            variable: new FormControl(null, Validators.required),
            datetime: new FormControl(null, Validators.required),
        })
        this.correlationForm = new FormGroup({
            predictionValue: new FormControl(null),
            stationId: new FormControl(null),
            dataCamp: new FormControl(null)
        })
    }

    async ngOnInit() {
        await this.startForm();
        await this.redColor();
        await this.chargePredict();
    }

    startForm() {
        this.weatherForm.get('alertId').setValue(this.data.alertId)
        this.weatherForm.get('dataCamp').setValue(this.data.dataCamp)
        this.predictForm.get('station').setValue(this.data.weatherDataDTO.station.id)
        this.predictForm.get('variable').setValue(this.data.dataCamp.toLowerCase())
        this.predictForm.get('datetime').setValue(this.data.weatherDataDTO.dateTime)
    }

    redColor() {
        const element = this.elementRef.nativeElement.querySelector('#' + this.data.dataCamp)
        element.classList.add('bg-danger')
    }

    async chargePredict() {
        this.alert.loading(true)
        await this.weatherService.loadPredict(this.predictForm.value).subscribe({
            next: (response) => {
                if (response.success) {
                    this.weatherForm.get('prediction').setValue(response.data.prediction.prediction)
                    this.weatherForm.controls['prediction'].addValidators([Validators.min(traduction[this.data.dataCamp].low), Validators.max(traduction[this.data.dataCamp].up)])
                    this.weatherForm.updateValueAndValidity()
                    this.prediction = response.data.prediction.prediction
                    this.chargeCorrelation()
                } else {
                    let station = Math.floor(Math.random() * (23 - 1 + 1) + 1)
                    this.predictForm.get('station').setValue(station);
                    this.chargePredict()
                }
            }
        });
    }

    async chargeCorrelation(): Promise<void> {
        return new Promise((resolve) => {
            setTimeout(() => {
                this.weatherService.loadCorrelation(this.weatherForm.controls['prediction'].value, this.data.weatherDataDTO.station.id, this.data.dataCamp).subscribe({
                    next: (response) => {
                        this.correlation = response;
                        this.alert.loading(false)
                    },
                    error: (error) => {
                        this.alert.Alert('Intente Nuevamente')
                    }
                });
                console.log(this.weatherForm.controls['prediction'].value)
                resolve();
            }, 1000);
        });
    }

    async save() {
        this.alert.loading(true)
        if (this.weatherForm.valid) {
            await this.weatherService.saveAlert(this.weatherForm.value).subscribe({
                error: (error) => {
                    this.alert.Alert('Error. Intente nuevamente');
                },
                complete: () => {
                    this.alert.Success('Exitoso', 'Datos Guardados exitosamente');
                    this.cancelar(true)
                }
            })
        } else {
            this.alert.loading(false)
            this.alert.Alert('Complete Correctamente el formulario.')
        }
    }

    cancelar(value: boolean): void {
        this.dialogRef.close(value);
    }

}

export const traduction = {
    DIRECCION_VIENTO: {
        low: 0,
        up: 360,
        text: ' °'
    },
    HUMEDAD_RELATIVA: {
        low: 0,
        up: 100,
        text: ' %'
    },
    PRECIPITACION: {
        low: 0,
        up: 55,
        text: ' mm'
    },
    RADIACION_SOLAR: {
        low: 0,
        up: 1400,
        text: ' W/m²'
    },
    TEMPERATURA: {
        low: 0,
        up: 26,
        text: ' °'
    },
    VELOCIDAD_VIENTO: {
        low: 0,
        up: 11.8,
        text: ' m/s'
    },
}
