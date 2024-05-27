import { AppState } from '@/store/state';
import { UiState } from '@/store/ui/state';
import { Component, HostBinding, OnInit, AfterViewInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppService } from '@services/app.service';
import { Observable } from 'rxjs';
import { UserInterface } from '@/models/user.interface';

const BASE_CLASSES = 'main-sidebar elevation-4';
@Component({
    selector: 'app-menu-sidebar',
    templateUrl: './menu-sidebar.component.html',
    styleUrls: ['./menu-sidebar.component.scss']
})
export class MenuSidebarComponent implements OnInit {
    @HostBinding('class') classes: string = BASE_CLASSES;
    public ui: Observable<UiState>;
    public user: UserInterface
    public menu = MENU;

    constructor(
        public appService: AppService,
        private store: Store<AppState>
    ) { }

    ngOnInit() {
        this.ui = this.store.select('ui');
        this.ui.subscribe((state: UiState) => {
            this.classes = `${BASE_CLASSES} ${state.sidebarSkin}`;
        });
        this.user = JSON.parse(localStorage.getItem('user'))
        this.menu = this.menu[this.user.role]
    }

}

export const MENU = {
    ADMIN: [
        {
            name: 'Inicio',
            iconClasses: 'fas fa-home',
            path: ['/inicio']
        },
        {
            name: 'Estaciones',
            iconClasses: 'fas fa-tachometer-alt',
            path: ['/dataEstaciones']
        },
        {
            name: 'Reportes',
            iconClasses: 'fas fa-file',
            children: [
                {
                    name: 'Reporte Usuarios',
                    iconClasses: 'fas fa-file',
                    path: ['/reportes']
                },
                {
                    name: 'Reporte Auditorias',
                    iconClasses: 'fas fa-file',
                    path: ['/alerts-report']
                }
            ]
        },
        {
            name: 'Alertas',
            iconClasses: 'fas fa-bell',
            path: ['/alerts']
        },
        {
            name: 'Cargar Datos',
            iconClasses: 'fas fa-database',
            path: ['/cargarDatos']
        },
        {
            name: 'Usuarios',
            iconClasses: 'fas fa-user',
            path: ['/user']
        },
    ],
    AUDITOR: [
        {
            name: 'Inicio',
            iconClasses: 'fas fa-home',
            path: ['/inicio']
        },
        {
            name: 'Estaciones',
            iconClasses: 'fas fa-tachometer-alt',
            path: ['/dataEstaciones']
        },
        {
            name: 'Reportes',
            iconClasses: 'fas fa-file',
            children: [
                {
                    name: 'Reporte Usuarios',
                    iconClasses: 'fas fa-file',
                    path: ['/reportes']
                },

                {
                    name: 'Reporte Auditorias',
                    iconClasses: 'fas fa-file',
                    path: ['/alerts-report']
                }
            ]
        },
        {
            name: 'Alertas',
            iconClasses: 'fas fa-bell',
            path: ['/alerts']
        },
    ],
    USER: [
        {
            name: 'Inicio',
            iconClasses: 'fas fa-home',
            path: ['/inicio']
        },
        {
            name: 'Estaciones',
            iconClasses: 'fas fa-tachometer-alt',
            path: ['/dataEstaciones']
        },
        {
            name: 'Reportes',
            iconClasses: 'fas fa-file',
            children: [
                {
                    name: 'Reporte Usuarios',
                    iconClasses: 'fas fa-file',
                    path: ['/reportes']
                },
            ]
        }
    ],

}