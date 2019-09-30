import { NgModule, ErrorHandler } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';
import { QuizTypeService } from './quiz/quiz-type.service';
import { SecuredImageComponent } from './ui/secured-image/secured-image.component';
import { GlobalErrorHandler } from './global-error-handler';
import { AlertsComponent } from './alerts/alerts.component';

@NgModule({
  imports: [
    CommonModule,
    NgbModule
  ],
  exports: [
    CommonModule,
    TranslateModule,
    NgbModule,
    SecuredImageComponent,
    AlertsComponent
  ],
  providers: [
    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    CountryService,
    QuizService,
    QuizTypeService
  ],
  declarations: [
    SecuredImageComponent,
    AlertsComponent
  ]
})
export class SharedModule {

}
