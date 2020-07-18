import { Component, OnInit } from '@angular/core';
import { Alert } from './alerts.model';
import { AlertsService } from './alerts.service';
import { delay } from 'rxjs/operators';

@Component({
  selector: 'geo-alerts',
  templateUrl: './alerts.component.html',
  styleUrls: ['./alerts.component.scss'],
})
export class AlertsComponent implements OnInit {
  alert: Alert | null;

  constructor(private alertService: AlertsService) {
    this.alert = null;
  }

  ngOnInit(): void {
    this.alertService.getAlert().subscribe((alert) => {
      this.alert = alert;
    });

    this.alertService
      .getAlert()
      .pipe(delay(5000))
      .subscribe((alert) => {
        if (alert) {
          this.close();
        }
      });
  }

  close(): void {
    this.alertService.clear();
  }
}
