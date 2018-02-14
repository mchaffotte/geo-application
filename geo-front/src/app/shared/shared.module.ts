import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModuleWithProviders } from '@angular/compiler/src/core';

import { CountryService } from './country/country.service';

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [
    CountryService
  ],
  declarations: []
})
export class SharedModule {

}
