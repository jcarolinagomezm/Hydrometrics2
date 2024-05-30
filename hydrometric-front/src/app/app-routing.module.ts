import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainComponent} from '@modules/main/main.component';
import {AlertComponent} from '@pages/alerts/alert.component';
import {LoginComponent} from '@modules/login/login.component';
import {UserAdminComponent} from '@pages/user/userAdmin.component';
import {DashboardComponent} from '@pages/dashboard/dashboard.component';
import {AuthGuard} from '@guards/auth.guard';
import {NonAuthGuard} from '@guards/non-auth.guard';
import { WeatherComponent } from '@pages/weather/weather.component';
import { ReportesComponent } from '@pages/reportes/reportes.component';
import { CargarDatosComponent } from '@pages/cargarDatos/cargarDatos.component';
import { AlertsReportComponent } from '@pages/alerts-report/alerts-report.component';
import { DataReportComponent } from '@pages/data-report/data-report.component';
import { ForgotComponent } from '@modules/login/forgot/forgot.component';
import { RecoverComponent } from '@modules/login/recover/recover.component';

const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        children: [
            {
                path: 'user',
                component: UserAdminComponent,
            },
            {
                path: 'alerts',
                component: AlertComponent,
            },
            {
                path: 'inicio',
                component: DashboardComponent,
            },
            {
                path: "dataEstaciones",
                component: WeatherComponent,
            },
            {
                path: "reportes",
                component: ReportesComponent,
            },
            {
                path: "reportes",
                component: ReportesComponent
            },
            {
                path: 'user-reports',
                component: ReportesComponent
            },
            {
                path: 'alerts-report',
                component: AlertsReportComponent
            },
            {
                path: 'data-report',
                component: DataReportComponent
            },
            {
                path: 'cargarDatos',
                component: CargarDatosComponent,
            }
        ]
    },
    {
        path: 'login',
        component: LoginComponent,
        canActivate: [NonAuthGuard]
    },
    {
        path: 'recuperar-contraseña',
        component: ForgotComponent,
        canActivate: [NonAuthGuard]
    },
    {
        path: 'change-password/:token',
        component: RecoverComponent,
        canActivate: [NonAuthGuard]
    },
    {path: '**', redirectTo: 'login'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {})],
    exports: [RouterModule]
})
export class AppRoutingModule {}