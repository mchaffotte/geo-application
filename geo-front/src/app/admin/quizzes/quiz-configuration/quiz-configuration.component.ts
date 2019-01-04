import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgOption } from '@ng-select/ng-select';
import { TranslateService } from '@ngx-translate/core';

import { Quiz, QuizConfiguration, ResponseType, QuizType } from '../../../shared/quiz/quiz';
import { QuizService } from '../../../shared/quiz/quiz.service';
import { QuizTypeService } from 'src/app/shared/quiz/quiz-type.service';

export class ResponseChoice {
  answer: boolean;
  multipleChoice: boolean;
}

@Component({
  selector: 'geo-quiz-configuration',
  templateUrl: './quiz-configuration.component.html',
  styleUrls: ['./quiz-configuration.component.scss']
})
export class QuizConfigurationComponent implements OnInit {

  @Output() quizCreated = new EventEmitter<Quiz>();

  questionTypes: Array<NgOption>;

  choice: ResponseChoice;

  configuration: QuizConfiguration;

  ResponseType = ResponseType;

  constructor(private quizService: QuizService, private quizTypeService: QuizTypeService, private translate: TranslateService) {
    this.choice = { answer: true, multipleChoice: true};

    this.configuration = new QuizConfiguration;
    this.configuration.responseType = ResponseType.MULTIPLE_CHOICE;
  }

  ngOnInit() {
    this.questionTypes = [];
    this.quizTypeService.getQuizTypes().subscribe(types => {
      let i = 1;
      const questionTypes = [];
      types.forEach(type => {
        questionTypes.push({
          index: i,
          label: this.translate.instant('model.question-type.' + type.questionType),
          questionType: type.questionType,
          responseTypes: type.responseTypes
        });
        i++;
      });
      this.questionTypes = questionTypes;
      if (this.questionTypes.length > 0) {
        this.configuration.questionType = this.questionTypes[0].questionType;
        this.updateQuestion(questionTypes[0]);
      }
    });
  }

  private contains(questionOption: NgOption, responseType: ResponseType): boolean {
    const response = questionOption.responseTypes.find((type: ResponseType) => type === responseType);
    return !!response;
  }

  updateQuestion(event: NgOption) {
    const questionOption = this.questionTypes.find(option => option.questionType === event.questionType);
    this.choice.multipleChoice = this.contains(questionOption, ResponseType.MULTIPLE_CHOICE);
    this.choice.answer = this.contains(questionOption, ResponseType.ANSWER);

    if (this.configuration.responseType === ResponseType.ANSWER && !this.choice.answer) {
      this.configuration.responseType = ResponseType.MULTIPLE_CHOICE;
    }
  }

  createQuiz() {
    this.quizService.createQuiz(this.configuration).subscribe(quizId => {
      this.quizService.getQuiz(quizId).subscribe(quiz => {
        this.quizCreated.emit(quiz);
      });
    });
  }

}
