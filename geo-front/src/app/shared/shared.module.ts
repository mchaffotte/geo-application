import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';
import { QuizTypeService } from './quiz/quiz-type.service';
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
    SecuredImageComponent
  ],
  providers: [
    CountryService,
    QuizService,
    QuizTypeService
  ],
  declarations: [
    SecuredImageComponent
  ]
})
export class SharedModule {

}
