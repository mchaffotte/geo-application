import { ErrorHandler, Injectable, Injector, NgZone } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { AlertsService } from './alerts/alerts.service';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private injector: Injector, private zone: NgZone) {
  }

  private get alertsService(): AlertsService {
    return this.injector.get(AlertsService);
  }

  handleError(error: Error | HttpErrorResponse): void {
    this.zone.run(() => {
      if (error instanceof HttpErrorResponse) {
        if (!navigator.onLine) {
          this.alertsService.error('No Internet Connection');
        } else {
          this.alertsService.warn(error.status + ' - ' + error.message);
        }
      } else {
        this.alertsService.error(error.message);
      }
    });
    console.error('Something wrong happens: ', error);
  }

}
