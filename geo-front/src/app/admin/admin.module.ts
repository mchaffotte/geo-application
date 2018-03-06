import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { CountriesComponent } from './countries/countries.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SandboxComponent } from './sandbox/sandbox.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    NgbModule,
    CommonModule,
    AdminRoutingModule,
    SharedModule
  ],
  declarations: [
    AdminComponent,
    CountriesComponent,
    DashboardComponent,
    SandboxComponent
  ]
})
export class AdminModule { }
