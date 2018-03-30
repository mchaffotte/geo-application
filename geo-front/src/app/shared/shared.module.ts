import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';
import { InputFocusDirective } from './ui/input-focus/input-focus.directive';
import { SecuredImageComponent } from './ui/secured-image/secured-image.component';

@NgModule({
  imports: [
    CommonModule,
    NgbModule
  ],
  exports: [
    CommonModule,
    TranslateModule,
    NgbModule,
    InputFocusDirective,
    SecuredImageComponent
  ],
  providers: [
    CountryService,
    QuizService
  ],
  declarations: [
    InputFocusDirective,
    SecuredImageComponent
  ]
})
export class SharedModule {

}
