import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { QuizService } from './quiz.service';

describe('QuizService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [QuizService]
    });
  });

  it('should be created', inject([QuizService], (service: QuizService) => {
    expect(service).toBeTruthy();
  }));
});
