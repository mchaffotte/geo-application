import { TestBed } from '@angular/core/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';

import { AlertsService } from '../alerts/alerts.service';
import { Observable, of } from 'rxjs';

const translations: any = { 'admin.menu.dashboard': 'Dashboard' };

class FakeLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of(translations);
  }
}

describe('AlertsService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [
        TranslateModule.forRoot({
          loader: { provide: TranslateLoader, useClass: FakeLoader },
        }),
      ],
    })
  );

  it('should be created', () => {
    const service: AlertsService = TestBed.inject(AlertsService);
    expect(service).toBeTruthy();
  });
});
