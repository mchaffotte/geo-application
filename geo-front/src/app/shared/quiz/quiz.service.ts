import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Quiz, QuizConfiguration, QuizResponse, QuizResult, QuestionType } from './quiz';

@Injectable()
export class QuizService {

  constructor(private http: HttpClient) { }

  createQuiz(configuration: QuizConfiguration): Observable<number> {
    return this.http.post('api/quizzes', configuration, { observe: 'response' })
      .pipe(map(res => {
        const location = res.headers.get('Location');
        const index = location.lastIndexOf('/');
        return Number(location.substring(index + 1));
      }));
  }

  getQuiz(quizId: number): Observable<Quiz> {
    return this.http.get<Quiz>('api/quizzes/' + quizId);
  }

  answer(quizId: number, response: QuizResponse): Observable<QuizResult> {
    return this.http.put<QuizResult>('api/quizzes/' + quizId, response);
  }

  isMultipleChoiceOnly(questionType: QuestionType): boolean {
    return QuestionType.LAND_AREA === questionType || QuestionType.WATER_AREA === questionType
      || QuestionType.TOTAL_AREA === questionType;
  }

}
