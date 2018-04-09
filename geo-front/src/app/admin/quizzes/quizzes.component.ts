import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgOption } from '@ng-select/ng-select';
import { TranslateService } from '@ngx-translate/core';

import { Question, Quiz, QuizAnswer, QuizResult, QuizConfiguration, QuestionType } from '../../shared/quiz/quiz';
import { QuizService } from '../../shared/quiz/quiz.service';
import { SafeUrl } from '@angular/platform-browser/src/security/dom_sanitization_service';

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

  isMultipleChoice: boolean;

  answerControl: FormControl;

  shouldAnswer: boolean;

  constructor(private quizService: QuizService, private translate: TranslateService) { }

  ngOnInit() {
    this.answerControl = new FormControl('');
    this.questionTypes = new Array<NgOption>(
      {id: 1, label: this.translate.instant('model.question-type.capital'), type: QuestionType.CAPITAL},
      {id: 2, label: this.translate.instant('model.question-type.total-area'), type: QuestionType.TOTAL_AREA},
      {id: 3, label: this.translate.instant('model.question-type.flag'), type: QuestionType.FLAG},
      {id: 4, label: this.translate.instant('model.question-type.silhouette'), type: QuestionType.SILHOUETTE}
    );
    this.selectedQuestionType = QuestionType.CAPITAL;
    this.isMultipleChoice = true;
    this.shouldAnswer = true;
  }

  createQuiz() {
    const configuration = new QuizConfiguration();
    configuration.questionType = this.selectedQuestionType;
    configuration.multipleChoice = this.isMultipleChoice;
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

  answer() {
    this.shouldAnswer = false;
    this.selectAnswer(this.answerControl.value);
    this.shouldAnswer = true;
    this.answerControl.reset();
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

  getImageURL(imagePath: string): string {
    const index = imagePath.lastIndexOf('/');
    const imageId = imagePath.substring(index + 1);
    return 'api/images/' + imageId;
  }

}
