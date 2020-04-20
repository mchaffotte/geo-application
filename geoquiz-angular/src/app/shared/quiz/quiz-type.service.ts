import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { QuizType } from './quiz';

@Injectable({
  providedIn: 'root',
})
export class QuizTypeService {
  constructor(private http: HttpClient) {}

  getQuizTypes(): Observable<QuizType[]> {
    return this.http.get<QuizType[]>('api/quiz-types');
  }
}
