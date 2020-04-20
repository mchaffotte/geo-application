import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgSelectModule } from '@ng-select/ng-select';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { CountriesComponent } from './countries/countries.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SharedModule } from '../shared/shared.module';
import { QuizzesComponent } from './quizzes/quizzes.component';
import { QuizConfigurationComponent } from './quizzes/quiz-configuration/quiz-configuration.component';
import { QuizGameComponent } from './quizzes/quiz-game/quiz-game.component';

@NgModule({
  imports: [CommonModule, AdminRoutingModule, SharedModule, NgSelectModule, FormsModule, ReactiveFormsModule, FontAwesomeModule],
  declarations: [AdminComponent, CountriesComponent, DashboardComponent, QuizzesComponent, QuizConfigurationComponent, QuizGameComponent],
})
export class AdminModule {}
