import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

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

  quizGameForm: FormGroup;
  @ViewChild('question_answer') answerRef: ElementRef;

  constructor(private fb: FormBuilder, private quizService: QuizService) {
    this.quizGameForm = this.fb.group({
      answer: ''
    });
  }

  ngOnInit() { }

  @Input()
  set quiz(quiz: Quiz) {
    if (quiz) {
      this._quiz = quiz;
      this.index = -1;
      this.changeQuestion();
      this.answers = new QuizAnswer;
      this.result = null;
      if (this.question.suggestions.length === 0) {
        setTimeout(() => this.answerRef.nativeElement.focus(), 5);
      }
    }
  }

  answer() {
    this.selectAnswer(this.quizGameForm.get('answer').value);
    this.answerRef.nativeElement.focus();
    this.quizGameForm.reset();
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
