import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { QuizTypeService } from './quiz-type.service';

describe('QuizTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [QuizTypeService],
    });
  });

  it('should be created', inject([QuizTypeService], (service: QuizTypeService) => {
    expect(service).toBeTruthy();
  }));
});
