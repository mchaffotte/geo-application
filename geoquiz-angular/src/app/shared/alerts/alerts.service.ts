import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';

import { AlertType, Alert } from './alerts.model';

@Injectable({
  providedIn: 'root',
})
export class AlertsService {
  private subject = new Subject<Alert>();

  constructor(private translate: TranslateService) {}

  getAlert(): Observable<Alert> {
    return this.subject.asObservable();
  }

  success(translationKey: string, values?: object) {
    this.alert(AlertType.SUCCESS, translationKey, values);
  }

  warn(translationKey: string, values?: object) {
    this.alert(AlertType.WARNING, translationKey, values);
  }

  error(translationKey: string, values?: object) {
    this.alert(AlertType.DANGER, translationKey, values);
  }

  info(translationKey: string, values?: object) {
    this.alert(AlertType.INFO, translationKey, values);
  }

  private alert(type: string, translationKey: string, values?: object) {
    const message = this.translate.instant(translationKey, values);
    this.subject.next({ type, message } as Alert);
  }

  clear() {
    this.subject.next();
  }
}
