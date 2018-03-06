import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    CommonModule,
    TranslateModule
  ],
  providers: [
    CountryService,
    QuizService
  ],
  declarations: []
})
export class SharedModule {

}
