import { Component, OnInit, Renderer2, ElementRef, ViewChild } from '@angular/core';
import { AlertService } from '@services/alert.service';
import { WeatherService } from '@services/weather.service';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { WeatherInterface } from '@/models/weather.interface';
import { MatDialog, MatDialogConfig, MatDialogModule } from '@angular/material/dialog';
import { ModalComponent } from '@components/modal/modal.component';
import { ScrollStrategy, ScrollStrategyOptions } from '@angular/cdk/overlay';


@Component({
    selector: 'app-alert',
    templateUrl: './alert.component.html',
    styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {
    p: number = 1;
    public correccion: boolean = false;
    public correcionForm: FormGroup;
    public dataWeather: WeatherInterface[];
    public modal: boolean = false;
    public data: WeatherInterface;
    public scrollStrategy: ScrollStrategy;

    constructor(private alert: AlertService, private weatherService: WeatherService, public dialog: MatDialog, private sso: ScrollStrategyOptions) {
        this.scrollStrategy = this.sso.noop()
    }

    ngOnInit(): void {
        this.loadData();
    }

    async loadData() {
        await this.weatherService.loadData().subscribe({
            next: (response) => {
                this.dataWeather = response;
            }
        })
    }

    openModal(data: WeatherInterface) {
        const dialogo1 = this.dialog.open(ModalComponent, {
            autoFocus: false,
            maxHeight: '80vh',
            scrollStrategy: this.scrollStrategy,
            panelClass: "dialog-responsive",
            data: data
        });

        dialogo1.afterClosed().subscribe(result => {
            if (result) {
                this.ngOnInit()
            }
        });
    }

}
