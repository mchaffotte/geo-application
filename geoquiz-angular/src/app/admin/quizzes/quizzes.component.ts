import { Component, OnInit } from '@angular/core';

import { Quiz } from '../../shared/quiz/quiz';

@Component({
  selector: 'geo-quizzes',
  templateUrl: './quizzes.component.html',
  styleUrls: ['./quizzes.component.scss'],
})
export class QuizzesComponent implements OnInit {
  quiz: Quiz;

  constructor() {}

  ngOnInit() {}

  startQuiz(quiz: Quiz) {
    this.quiz = quiz;
  }
}
