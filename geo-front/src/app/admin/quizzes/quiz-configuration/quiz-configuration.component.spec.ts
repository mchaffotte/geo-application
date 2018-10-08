import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

import { QuizConfigurationComponent } from './quiz-configuration.component';
import { QuizService } from '../../../shared/quiz/quiz.service';

const translations: any = { 'admin.menu.dashboard': 'Dashboard' };

class FakeLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of(translations);
  }
}

class MockQuizService {
}

describe('QuizConfigurationComponent', () => {
  let component: QuizConfigurationComponent;
  let fixture: ComponentFixture<QuizConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [QuizConfigurationComponent],
      imports: [
        ReactiveFormsModule,
        NgSelectModule,
        TranslateModule.forRoot({
          loader: { provide: TranslateLoader, useClass: FakeLoader },
        })
      ],
      providers: [
        { provide: QuizService, useClass: MockQuizService }
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
