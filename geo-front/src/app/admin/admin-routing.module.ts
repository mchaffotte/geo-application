import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AdminComponent } from './admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { Country } from '../shared/country/country';
import { CountriesComponent } from './countries/countries.component';

const routes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AdminComponent,
      children: [{
        path: 'dashboard',
        component: DashboardComponent
      }, {
        path: 'countries',
        component: CountriesComponent
      }, {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }, {
        path: '**',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }]
    }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
