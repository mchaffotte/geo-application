import { Component, OnInit } from '@angular/core';

import { Question, Quiz, QuizAnswer, QuizResult } from '../../shared/quiz/quiz';
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

  constructor(private quizService: QuizService) { }

  ngOnInit() {
  }

  createQuiz() {
    this.quizService.createQuiz().subscribe(res => {
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
