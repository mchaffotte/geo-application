import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

import { QuizGameComponent } from './quiz-game.component';
import { QuizService } from '../../../shared/quiz/quiz.service';

const translations: object = { 'admin.menu.dashboard': 'Dashboard' };

class FakeLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<object> {
    return of(translations);
  }
}

@Component({
  selector: 'geo-secured-image',
  template: '<p>Mock Secured Image Component</p>',
})
class MockSecuredImageComponent {
  @Input() src = '';
}

class MockQuizService {}

describe('QuizGameComponent', () => {
  let component: QuizGameComponent;
  let fixture: ComponentFixture<QuizGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QuizGameComponent, MockSecuredImageComponent],
      imports: [
        ReactiveFormsModule,
        TranslateModule.forRoot({
          loader: { provide: TranslateLoader, useClass: FakeLoader },
        }),
      ],
      providers: [{ provide: QuizService, useClass: MockQuizService }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
