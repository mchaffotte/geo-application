import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

import { QuizzesComponent } from './quizzes.component';
import { Quiz } from '../../shared/quiz/quiz';

const translations: object = { 'admin.menu.dashboard': 'Dashboard' };

class FakeLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<object> {
    return of(translations);
  }
}

@Component({
  selector: 'geo-quiz-configuration',
  template: '<p>Mock Geo Quiz Configuration Component</p>',
})
class MockQuizConfigurationComponent {}

@Component({
  selector: 'geo-quiz-game',
  template: '<p>Mock Geo Quiz Game Component</p>',
})
class MockQuizGameComponent {
  @Input() quiz: Quiz | null = null;
}

describe('QuizzesComponent', () => {
  let component: QuizzesComponent;
  let fixture: ComponentFixture<QuizzesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QuizzesComponent, MockQuizConfigurationComponent, MockQuizGameComponent],
      imports: [
        TranslateModule.forRoot({
          loader: { provide: TranslateLoader, useClass: FakeLoader },
        }),
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizzesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
