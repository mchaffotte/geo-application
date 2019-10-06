import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

import {
  Question,
  Quiz,
  QuizAnswer,
  QuizResult,
  QuestionAnswer,
} from '../../../shared/quiz/quiz';
import { QuizService } from '../../../shared/quiz/quiz.service';

@Component({
  selector: 'geo-quiz-game',
  templateUrl: './quiz-game.component.html',
  styleUrls: ['./quiz-game.component.scss'],
})
export class QuizGameComponent implements OnInit {
  private innerQuiz: Quiz;

  private quizAnswer: QuizAnswer;

  index: number;

  question: Question;

  result: QuizResult;

  quizGameForm: FormGroup;
  @ViewChild('question_answer', { static: false }) answerRef: ElementRef;

  constructor(private fb: FormBuilder, private quizService: QuizService) {
    this.quizGameForm = this.fb.group({
      answer: '',
    });
  }

  ngOnInit() {}

  @Input()
  set quiz(quiz: Quiz) {
    if (quiz) {
      this.innerQuiz = quiz;
      this.index = -1;
      this.changeQuestion();
      this.quizAnswer = new QuizAnswer();
      this.result = null;
      if (this.question.choices.length === 0) {
        setTimeout(() => this.answerRef.nativeElement.focus(), 5);
      }
    }
  }

  answer() {
    let value = this.quizGameForm.get('answer').value;
    if (value == null) {
      value = '';
    }
    this.selectAnswer(value);
    this.answerRef.nativeElement.focus();
    this.quizGameForm.reset();
  }

  selectAnswer(answer: string) {
    const questionAnswer = new QuestionAnswer();
    questionAnswer.answers.push(answer);
    this.quizAnswer.questionAnswers.push(questionAnswer);
    if (this.index + 1 < this.innerQuiz.questions.length) {
      this.changeQuestion();
    } else {
      this.quizService
        .answer(this.innerQuiz.id, this.quizAnswer)
        .subscribe(res => {
          this.question = null;
          this.result = res;
        });
    }
  }

  private changeQuestion() {
    this.index++;
    this.question = this.innerQuiz.questions[this.index];
  }

  getImageURL(imagePath: string): string {
    const index = imagePath.lastIndexOf('/');
    const imageId = imagePath.substring(index + 1);
    return 'api/images/' + imageId;
  }
}