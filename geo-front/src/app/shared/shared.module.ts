import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';
import { InputFocusDirective } from './ui/input-focus/input-focus.directive';

@NgModule({
  imports: [
    CommonModule,
    NgbModule
  ],
  exports: [
    CommonModule,
    TranslateModule,
    NgbModule,
    InputFocusDirective
  ],
  providers: [
    CountryService,
    QuizService
  ],
  declarations: [
    InputFocusDirective
  ]
})
export class SharedModule {

}
