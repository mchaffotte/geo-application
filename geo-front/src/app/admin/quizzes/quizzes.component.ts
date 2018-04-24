import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';

import { Question, Quiz, QuizAnswer, QuizResult } from '../../shared/quiz/quiz';
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

  answerControl: FormControl;

  shouldAnswer: boolean;

  constructor(private fb: FormBuilder, private quizService: QuizService) { }

  ngOnInit() {
    this.answerControl = new FormControl('');
    this.shouldAnswer = true;
  }

  startQuiz(quiz: Quiz) {
    this.quiz = quiz;
    this.index = -1;
    this.changeQuestion();
    this.answers = new QuizAnswer;
    this.result = null;
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
