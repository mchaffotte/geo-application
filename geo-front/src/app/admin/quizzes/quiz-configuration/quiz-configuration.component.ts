import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgOption } from '@ng-select/ng-select';
import { TranslateService } from '@ngx-translate/core';

import { QuestionType, Quiz, QuizConfiguration } from '../../../shared/quiz/quiz';
import { QuizService } from '../../../shared/quiz/quiz.service';

@Component({
  selector: 'geo-quiz-configuration',
  templateUrl: './quiz-configuration.component.html',
  styleUrls: ['./quiz-configuration.component.scss']
})
export class QuizConfigurationComponent implements OnInit {

  quizConfigurationForm: FormGroup;

  questionTypes: Array<NgOption>;

  @Output() quizCreated  = new EventEmitter<Quiz>();

  constructor(private fb: FormBuilder, private quizService: QuizService, private translate: TranslateService) {
    this.questionTypes = new Array<NgOption>(
      {id: 1, label: this.translate.instant('model.question-type.capital'), type: QuestionType.CAPITAL},
      {id: 2, label: this.translate.instant('model.question-type.total-area'), type: QuestionType.TOTAL_AREA},
      {id: 3, label: this.translate.instant('model.question-type.flag'), type: QuestionType.FLAG},
      {id: 4, label: this.translate.instant('model.question-type.silhouette'), type: QuestionType.SILHOUETTE},
      {id: 5, label: this.translate.instant('model.question-type.land-area'), type: QuestionType.LAND_AREA},
      {id: 6, label: this.translate.instant('model.question-type.water-area'), type: QuestionType.WATER_AREA},
    );

    this.quizConfigurationForm = this.fb.group({
      questionType: QuestionType.CAPITAL,
      mulitpleChoice: true,
    });
   }

  ngOnInit() {
    this.quizConfigurationForm.get('questionType').valueChanges.subscribe(
      (questionType: QuestionType) => {
        if (this.quizService.isMultipleChoiceOnly(questionType)) {
          this.quizConfigurationForm.get('mulitpleChoice').disable();
        } else {
          this.quizConfigurationForm.get('mulitpleChoice').enable();
        }
      }
    );
  }

  createQuiz() {
    const choiceControl = this.quizConfigurationForm.get('mulitpleChoice');
    const multipleChoice = choiceControl.disabled ? true : choiceControl.value;
    const configuration = new QuizConfiguration();
    configuration.questionType = this.quizConfigurationForm.get('questionType').value;
    configuration.multipleChoice = multipleChoice;
    this.quizService.createQuiz(configuration).subscribe(quizId => {
      this.quizService.getQuiz(quizId).subscribe(quiz => {
        this.quizCreated.emit(quiz);
      });
    });
  }

}
