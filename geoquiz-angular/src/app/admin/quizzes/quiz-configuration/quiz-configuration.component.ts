import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgOption } from '@ng-select/ng-select';
import { TranslateService } from '@ngx-translate/core';

import { Quiz, QuizConfiguration, AnswerType } from '../../../shared/quiz/quiz';
import { QuizService } from '../../../shared/quiz/quiz.service';
import { QuizTypeService } from 'src/app/shared/quiz/quiz-type.service';

export class ResponseChoice {
  answer: boolean;
  multipleChoice: boolean;
}

@Component({
  selector: 'geo-quiz-configuration',
  templateUrl: './quiz-configuration.component.html',
  styleUrls: ['./quiz-configuration.component.scss'],
})
export class QuizConfigurationComponent implements OnInit {
  @Output() quizCreated = new EventEmitter<Quiz>();

  questionTypes: Array<NgOption>;

  choice: ResponseChoice;

  configuration: QuizConfiguration;

  AnswerType = AnswerType;

  constructor(private quizService: QuizService, private quizTypeService: QuizTypeService, private translate: TranslateService) {
    this.choice = { answer: true, multipleChoice: true };

    this.configuration = new QuizConfiguration();
    this.configuration.answerType = AnswerType.MULTIPLE_CHOICE;
  }

  ngOnInit() {
    this.questionTypes = [];
    this.quizTypeService.getQuizTypes().subscribe((types) => {
      let i = 1;
      const questionTypes = [];
      types.forEach((type) => {
        questionTypes.push({
          index: i,
          label: this.translate.instant('model.question-type.' + type.questionType),
          questionType: type.questionType,
          answerTypes: type.answerTypes,
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

  private contains(questionOption: NgOption, answerType: AnswerType): boolean {
    const response = questionOption.answerTypes.find((type: AnswerType) => type === answerType);
    return !!response;
  }

  updateQuestion(event: NgOption) {
    const questionOption = this.questionTypes.find((option) => option.questionType === event.questionType);
    this.choice.multipleChoice = this.contains(questionOption, AnswerType.MULTIPLE_CHOICE);
    this.choice.answer = this.contains(questionOption, AnswerType.ANSWER);

    if (this.configuration.answerType === AnswerType.ANSWER && !this.choice.answer) {
      this.configuration.answerType = AnswerType.MULTIPLE_CHOICE;
    }
  }

  createQuiz() {
    this.quizService.createQuiz(this.configuration).subscribe((quizId) => {
      this.quizService.getQuiz(quizId).subscribe((quiz) => {
        this.quizCreated.emit(quiz);
      });
    });
  }
}
