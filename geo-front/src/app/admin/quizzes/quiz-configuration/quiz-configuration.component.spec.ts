import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

import { QuizConfigurationComponent } from './quiz-configuration.component';
import { QuizService } from '../../../shared/quiz/quiz.service';
import { QuizTypeService } from '../../../shared/quiz/quiz-type.service';
import { QuizType } from 'src/app/shared/quiz/quiz';

const translations: any = { 'admin.menu.dashboard': 'Dashboard' };

class FakeLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of(translations);
  }
}

class MockQuizService {
}

class MockQuizTestService {

  getQuizTypes(): Observable<QuizType[]> {
    return of([]);
  }

}

describe('QuizConfigurationComponent', () => {
  let component: QuizConfigurationComponent;
  let fixture: ComponentFixture<QuizConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [QuizConfigurationComponent],
      imports: [
        FormsModule,
        NgSelectModule,
        NgbModule,
        TranslateModule.forRoot({
          loader: { provide: TranslateLoader, useClass: FakeLoader },
        })
      ],
      providers: [
        { provide: QuizService, useClass: MockQuizService },
        { provide: QuizTypeService, useClass: MockQuizTestService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    component.ngOnInit();
    expect(component).toBeTruthy();
  });
});
