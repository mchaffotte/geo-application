import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';

import { Question, Quiz, QuizAnswer, QuizResult } from '../../../shared/quiz/quiz';
import { QuizService } from '../../../shared/quiz/quiz.service';

@Component({
  selector: 'geo-quiz-game',
  templateUrl: './quiz-game.component.html',
  styleUrls: ['./quiz-game.component.scss']
})
export class QuizGameComponent implements OnInit {

  private _quiz: Quiz;

  private answers: QuizAnswer;

  index: number;

  question: Question;

  result: QuizResult;

  answerControl: FormControl;

  shouldAnswer: boolean;

  constructor(private fb: FormBuilder, private quizService: QuizService) { }

  ngOnInit() {
    this.answerControl = new FormControl('');
    this.shouldAnswer = true;
  }

  @Input()
  set quiz(quiz: Quiz) {
    if (quiz) {
      this._quiz = quiz;
      this.index = -1;
      this.changeQuestion();
      this.answers = new QuizAnswer;
      this.result = null;
    }
  }

  answer() {
    this.shouldAnswer = false;
    this.selectAnswer(this.answerControl.value);
    this.shouldAnswer = true;
    this.answerControl.reset();
  }

  selectAnswer(answer: string) {
    this.answers.answers.push(answer);
    if (this.index + 1 < this._quiz.questions.length) {
      this.changeQuestion();
    } else {
      this.quizService.answer(this._quiz.id, this.answers).subscribe(res => {
        this.question = null;
        this.result = res;
      });
    }
  }

  private changeQuestion() {
    this.index++;
    this.question = this._quiz.questions[this.index];
  }

  getImageURL(imagePath: string): string {
    const index = imagePath.lastIndexOf('/');
    const imageId = imagePath.substring(index + 1);
    return 'api/images/' + imageId;
  }

}
