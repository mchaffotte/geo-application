import { Component, OnInit } from '@angular/core';
import { NgOption } from '@ng-select/ng-select';
import { TranslateService } from '@ngx-translate/core';

import { Question, Quiz, QuizAnswer, QuizResult, QuizConfiguration, QuestionType } from '../../shared/quiz/quiz';
import { QuizService } from '../../shared/quiz/quiz.service';

@Component({
  selector: 'geo-quizzes',
  templateUrl: './quizzes.component.html',
  styleUrls: ['./quizzes.component.scss']
})
export class QuizzesComponent implements OnInit {

  private quiz: Quiz;

  private answers: QuizAnswer;

  index: number;

  question: Question;

  result: QuizResult;

  questionTypes: Array<NgOption>;
  selectedQuestionType: QuestionType;

  constructor(private quizService: QuizService, private translate: TranslateService) { }

  ngOnInit() {
    this.questionTypes = new Array<NgOption>(
      {id: 1, label: this.translate.instant('model.question-type.capital'), type: QuestionType.CAPITAL, selected: true},
      {id: 2, label: this.translate.instant('model.question-type.total-area'), type: QuestionType.TOTAL_AREA}
    );
    this.selectedQuestionType = this.questionTypes[0].type;
  }

  createQuiz() {
    const configuration = new QuizConfiguration();
    configuration.questionType = this.selectedQuestionType;
    this.quizService.createQuiz(configuration).subscribe(res => {
      this.quizService.getQuiz(res).subscribe(quiz => {
        this.quiz = quiz;
        this.index = -1;
        this.changeQuestion();
        this.answers = new QuizAnswer;
        this.result = null;
      });
    });
  }

  selectAnswer(answer: string) {
    this.answers.answers.push(answer);
    if (this.index + 1 < this.quiz.questions.length) {
      this.changeQuestion();
    } else {
      this.quizService.answer(this.quiz.id, this.answers).subscribe(res => {
        this.question = null;
        this.result = res;
      });
    }
  }

  private changeQuestion() {
    this.index++;
    this.question = this.quiz.questions[this.index];
  }

}
