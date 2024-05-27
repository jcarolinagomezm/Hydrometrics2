import { Component } from '@angular/core';
import { UserAdminInterface } from '@/models/userAdmin.interface';
import { AlertService } from '@services/alert.service';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '@services/user.service';
import { ModalUserComponent } from '@components/modalUser/modalUser.component';
import { ScrollStrategy, ScrollStrategyOptions } from '@angular/cdk/overlay';

@Component({
    selector: 'app-user-admin',
    templateUrl: './userAdmin.component.html',
    styleUrls: ['./userAdmin.component.scss']
})
export class UserAdminComponent {
    p: number = 1;
    public dataUser: UserAdminInterface[];
    public modal: boolean = false;
    public data: UserAdminInterface;
    public scrollStrategy: ScrollStrategy;

    constructor(
        private alert: AlertService,
        private userService: UserService,
        public dialog: MatDialog,
        private sso: ScrollStrategyOptions
    ) {
        this.scrollStrategy = this.sso.noop()
    }

    ngOnInit(): void {
        this.loadData();
    }

    async loadData() {
        await this.userService.loadUser().subscribe({
            next: (response) => {
                this.dataUser = response;
            }
        })
    }

    openModal(data: UserAdminInterface) {
        const dialogo1 = this.dialog.open(ModalUserComponent, {
            autoFocus: false,
            maxHeight: '80vh',
            scrollStrategy: this.scrollStrategy,
            panelClass: "dialog-responsive-user",
            data: data
        });

        dialogo1.afterClosed().subscribe(result => {
            if (result) {
                this.ngOnInit()
            }
        });
    }

    openModalNew() {
        const dialogo1 = this.dialog.open(ModalUserComponent,{
            autoFocus: false,
            maxHeight: '80vh',
            scrollStrategy: this.scrollStrategy,
            panelClass: "dialog-responsive-user",
        });

        dialogo1.afterClosed().subscribe(result => {
            if (result) {
                this.ngOnInit()
            }
        });
    }
}
