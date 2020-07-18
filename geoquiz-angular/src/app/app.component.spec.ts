import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

import { AppComponent } from './app.component';

const translations: object = { 'admin.menu.dashboard': 'Dashboard' };

class FakeLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<object> {
    return of(translations);
  }
}

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [
        RouterTestingModule,
        TranslateModule.forRoot({
          loader: { provide: TranslateLoader, useClass: FakeLoader },
        }),
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
