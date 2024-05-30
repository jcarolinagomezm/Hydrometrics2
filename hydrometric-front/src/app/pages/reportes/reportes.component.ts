import { DatePipe } from "@angular/common";
import { Component, ElementRef, OnInit } from '@angular/core';
import { UserService } from "@services/user.service";
import { AlertService } from "@services/alert.service";
import { UserReportInterface } from "@/models/report.interface";
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
    selector: 'app-reportes',
    templateUrl: './reportes.component.html',
    styleUrls: ['./reportes.component.scss']
})
export class ReportesComponent implements OnInit {
    public data: UserReportInterface[];

    constructor(
        private userService: UserService,
        private alert: AlertService,
        private elementRef: ElementRef,
        private datePipe: DatePipe
    ) {

    }

    ngOnInit(): void {
        this.cargarUsuarios();
    }

    async cargarUsuarios() {
        this.alert.loading(true)
        await this.userService.loadReport().subscribe({
            next: (response) => {
                this.data = response;
            },
            error: (error) => {
                this.alert.Alert('Intente nuevamente')
            }
        })
        this.alert.loading(false)
    }

    generarPDF() {
        this.alert.loading(true)
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
            doc.addImage(img, 'PNG', 15, 140, pdfWidth, pdfHeight, undefined, 'FAST');
            doc.setFontSize(8)
            doc.text('Fecha Generacion: ' + this.datePipe.transform(date, "medium"), 35, 150)
            doc.addImage('./assets/img/meteodata.png', 'PNG', 240, 9, 100, 100)
            doc.setFontSize(20)
            doc.text('MeteoData',240,130)
            return doc;
        }).then((docResult) => {
            docResult.save('Reporte Usuarios');
            this.alert.loading(false)
        });
    }
}