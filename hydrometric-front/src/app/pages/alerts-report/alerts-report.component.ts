import { DatePipe } from "@angular/common";
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '@services/alert.service';
import { StationInterface } from '@/models/station.interface';
import jsPDF from 'jspdf';
import { WeatherDataService } from '@services/weatherData.service';
import { dataReportInterface } from '@/models/report.interface';
import { WeatherService } from '@services/weather.service';
import html2canvas from 'html2canvas';


@Component({
    selector: 'app-reportes',
    templateUrl: './alerts-report.component.html',
    styleUrls: ['.//alerts-report.component.scss'],
})
export class AlertsReportComponent implements OnInit{
    p: number = 1;
    public dataForm: FormGroup;
    public dataStation: StationInterface[];
    public data: dataReportInterface[];
    public minDateStart: Date;
    public maxDateStart: Date;
    public minDateEnd: Date;
    public maxDateEnd: Date;

    constructor(
        private alert: AlertService,
        private weatherDataService: WeatherDataService,
        private weatherService: WeatherService,
        private elementRef: ElementRef,
        private datePipe: DatePipe,
    ) {
        this.dataForm = new FormGroup({
            stationId: new FormControl(null, Validators.required),
            startDate: new FormControl(null, Validators.required),
            endDate: new FormControl(null, Validators.required),
        })
        const currentYear = new Date();
        this.minDateStart = new Date(currentYear.getFullYear() - 1, currentYear.getMonth() , currentYear.getDate())
        this.maxDateStart = new Date(currentYear.getFullYear(), currentYear.getMonth(), currentYear.getDate())        
    }

    ngOnInit(): void {
        this.loadDataStation();
    }

    ajustarRango(event){
        this.dataForm.controls['endDate'].reset();
        const currentYear= new Date(event.target.value.format('yyyy-MM-DD'))
        this.minDateEnd = new Date(currentYear.getFullYear(), currentYear.getMonth() , currentYear.getDate() + 1)
        this.maxDateEnd = new Date(currentYear.getFullYear(), currentYear.getMonth() + 1, currentYear.getDate())   
    }

    async loadDataStation(){
        this.alert.loading(true);
        await this.weatherDataService.loadDataStation().subscribe({
            next: (response) =>{
                this.dataStation = response;
                this.alert.loading(false);
            },
            error: (error) =>{
                this.alert.Alert('Intente Nuevamente')
            }
        })
    }

    async loadData(){
        if(this.dataForm.valid){
            this.alert.loading(true);
            this.weatherService.loadReport(this.dataForm.value).subscribe({
                next: (response) =>{
                    this.data = response;
                    this.generarPDF()
                    this.alert.loading(false);
                },
                error: (error) =>{
                    this.alert.Alert('')
                }
            })
        }else{
            this.alert.Alert('Diligencie el formulario correctamente.')
        }
    }

    generarPDF(): Promise<void> {
        return new Promise((resolve) => {
            setTimeout(() => {
                const element = this.elementRef.nativeElement.querySelector('#tablaDatos');
                const doc = new jsPDF('p', 'pt', 'a4');
                const options = {
                    background: 'white',
                    scale: 3
                };
                let date = new Date()
                html2canvas(element, options).then((canvas) => {
                    const img = canvas.toDataURL('image/PNG');
                    const imgProps = (doc as any).getImageProperties(img);
                    const pdfWidth = doc.internal.pageSize.getWidth() - 2 * 15;
                    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
                    doc.addImage(img, 'PNG', 15, 160, pdfWidth, pdfHeight, undefined, 'FAST');
                    doc.setFontSize(8)
                    doc.text('Fecha Generacion: ' + this.datePipe.transform(date, "medium"), 35, 150)
                    doc.addImage('./assets/img/meteodata.png', 'PNG', 240, 9, 100, 100)
                    doc.setFontSize(20)
                    doc.text('MeteoData',240,130)
                    return doc;
                }).then((docResult) => {
                    docResult.save('Reporte Alertas Auditadas');
                });
               
                resolve();
            }, 300);
        });
    }
}