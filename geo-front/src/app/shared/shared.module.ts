import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';

@NgModule({
  imports: [
    CommonModule,
    NgbModule
  ],
  exports: [
    CommonModule,
    TranslateModule,
    NgbModule
  ],
  providers: [
    CountryService,
    QuizService
  ],
  declarations: [
  ]
})
export class SharedModule {

}
