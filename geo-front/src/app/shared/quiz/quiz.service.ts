import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { map } from 'rxjs/operators';

import { Quiz, QuizResult, QuizAnswer } from './quiz';

@Injectable()
export class QuizService {

  constructor(private http: HttpClient) { }

  createQuiz(): Observable<number> {
    return this.http.post('api/quizzes', null, { observe: 'response' })
      .pipe(map(res => {
        const location = res.headers.get('Location');
        const index = location.lastIndexOf('/');
        return Number(location.substring(index + 1));
      }));
  }

  getQuiz(quizId: number): Observable<Quiz> {
    return this.http.get<Quiz>('api/quizzes/' + quizId);
  }

  answer(quizId: number, answers: QuizAnswer): Observable<QuizResult> {
    return this.http.put<QuizResult>('api/quizzes/' + quizId, answers);
  }

}
