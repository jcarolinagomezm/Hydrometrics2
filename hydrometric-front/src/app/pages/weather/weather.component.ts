import { Component, OnInit } from '@angular/core';
import { WeatherDataService } from '@services/weatherData.service';
import { AlertService } from '@services/alert.service';
import { Chart, ChartConfiguration, ChartItem } from 'chart.js/auto';
import { WeatherDataInterface } from '@/models/weather.interface';
import { StationInterface } from '@/models/station.interface';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ElementRef, ViewChild } from '@angular/core';

@Component({
    selector: 'app-weather',
    templateUrl: './weather.component.html',
    styleUrls: ['./weather.component.scss']
})
export class WeatherComponent implements OnInit{
    @ViewChild('chartCanvas') chartCanvas!: ElementRef;
    public chart: Chart[] = [];
    public data: WeatherDataInterface;
    public stationForm: FormGroup;
    public dataStation: StationInterface[];
    public show: boolean = false;
    public nameStation: string;
    public color = ['rgb(138, 233, 142)','rgb(75, 192, 192)','rgb(154, 241, 249)','rgb(255, 171, 106)','rgb(255, 165, 165)','rgb(204, 154, 93)']

    constructor(
        private weatherService: WeatherDataService,
        private alert: AlertService,
    ){
        this.stationForm = new FormGroup({
            codigo: new FormControl (null,Validators.required),
            dateStart: new FormControl (null, Validators.required),
            dateEnd: new FormControl (null, Validators.required)
        })
    }

    ngOnInit(): void {
        this.loadDataStation();
    }

    async loadDataStation(){
        this.alert.loading(true);
        await this.weatherService.loadDataStation().subscribe({
            next: (response) =>{
                this.dataStation = response;
                this.alert.loading(false);
            },
            error: (error) =>{
                this.alert.Alert('Intente Nuevamente')
            }
        })
    }

    async loadDataWeather(){
        this.alert.loading(true);
        await this.weatherService.loadDataWeather(this.stationForm.value).subscribe({
            next: (response) => {
                if(!response){
                    this.alert.loading(false);
                    return false;
                }
                this.show = true;
                this.nameStation = this.dataStation[this.stationForm.get('codigo').value-1].name;

                for(let i = 0; i <= response.length-1; i ++){
                    let info = []
                    for(let a = 0; a <= response[i].data.length-1; a++){
                        info.push({x: response[i].data[a].dateTime, y: response[i].data[a].value})
                    }

                    if(this.chart[i]){
                        this.chart[i].destroy();
                    }
                    var canvas = document.getElementById("myChart"+i) as HTMLCanvasElement;
                    var ctx = canvas.getContext("2d");

                    const chartConfig: ChartConfiguration = {
                        type: 'line',
                        data: {
                            datasets: [{
                                label: response[i].dataCamp,
                                data: info,
                                borderColor: this.color[i],
                                tension: 0.1,
                            }]
                        },
                        options: {
                          responsive: true,
                          scales: {
                            x:{
                                // reverse: true,
                                ticks: {
                                    // sampleSize: 2,
                                    // align: 'start',
                                    // maxTicksLimit:3
                                    // padding: 100
                                    display: false
                                },
                            }
                          }
                        }
                    }
                    this.chart[i] =  new Chart(ctx,chartConfig);
                }
                this.alert.loading(false);
            },
            error: (error) =>{
                this.alert.Alert('Intente Nuevamente')
            }
        })
    }
 }
