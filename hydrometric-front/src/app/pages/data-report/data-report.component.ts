import { DatePipe } from "@angular/common";
import { Component, ElementRef } from '@angular/core';
import { UserService } from "@services/user.service";
import { AlertService } from "@services/alert.service";
import { UserAdminInterface } from "@/models/userAdmin.interface";
import jsPDF from 'jspdf';

@Component({
    selector: 'app-reportes',
    templateUrl: './data-report.component.html',
    styleUrls: ['./data-report.component.scss']
})
export class DataReportComponent {
    public thead = [];
    public data: UserAdminInterface[];
    public object: any;

    constructor(
        private userService: UserService,
        private alert: AlertService,
        private elementRef: ElementRef,
        private datePipe: DatePipe
    ) {

    }

    async cargarUsuarios() {
        this.alert.loading(true)
        await this.userService.loadUser().subscribe({
            next: (response) => {
                this.data = response;
                this.object = Object.keys(this.data[0])
                this.thead = ['#','Usuario', 'Primer Nombre', 'Segundo Nombre', 'Email',  'rol']
            },
            error: (error) => {
                this.alert.Alert('Intente nuevamente')
            }
        })
        await this.generarPDF('Usuario');
        this.alert.loading(false)
    }

    generarPDF(titulo: string): Promise<void> {
        return new Promise((resolve) => {
            setTimeout(() => {
                const element = this.elementRef.nativeElement.querySelector('#tablaDatos')
        
                const doc = new jsPDF('p', 'pt', 'a4');
                doc.html(element, {
                    callback: (doc) => {
                        let date = new Date()
                        doc.setFontSize(8)
                        doc.text('Fecha Generacion: '+this.datePipe.transform(date,"medium"),70,160)
                        doc.addImage('./assets/img/meteodata.png', 'PNG', 240, 9, 100, 100)
                        doc.setFontSize(20)
                        doc.text('MeteoData',240,135)
                        doc.save('Reporte '+titulo);
                    },
                    y: 120,
                    x: 270
                });
                resolve();
            }, 300);
        });
    }
}
