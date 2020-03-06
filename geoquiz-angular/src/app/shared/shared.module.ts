import { NgModule, ErrorHandler } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { CountryService } from './country/country.service';
import { QuizService } from './quiz/quiz.service';
import { QuizTypeService } from './quiz/quiz-type.service';
import { SecuredImageComponent } from './ui/secured-image/secured-image.component';
import { GlobalErrorHandler } from './global-error-handler';
import { AlertsComponent } from './alerts/alerts.component';
import { IndicatorComponent } from './ui/indicator/indicator.component';
import { TotalCountriesComponent } from './ui/total-countries/total-countries.component';

@NgModule({
  imports: [CommonModule, NgbModule, FontAwesomeModule],
  exports: [CommonModule, TranslateModule, NgbModule, SecuredImageComponent, AlertsComponent, TotalCountriesComponent],
  providers: [{ provide: ErrorHandler, useClass: GlobalErrorHandler }, CountryService, QuizService, QuizTypeService],
  declarations: [SecuredImageComponent, AlertsComponent, IndicatorComponent, TotalCountriesComponent]
})
export class SharedModule {}
