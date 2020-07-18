import { Component } from '@angular/core';

import { Quiz } from '../../shared/quiz/quiz';

@Component({
  selector: 'geo-quizzes',
  templateUrl: './quizzes.component.html',
  styleUrls: ['./quizzes.component.scss'],
})
export class QuizzesComponent {
  quiz: Quiz;

  constructor() {
    this.quiz = { id: -1, questions: [] };
  }
  startQuiz(quiz: Quiz): void {
    this.quiz = quiz;
  }
}
