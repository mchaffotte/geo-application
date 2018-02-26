import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { CountryService } from './country/country.service';

@NgModule({
  imports: [
    CommonModule,
  ],
  exports: [
    CommonModule,
    TranslateModule
  ],
  providers: [
    CountryService
  ],
  declarations: []
})
export class SharedModule {

}
