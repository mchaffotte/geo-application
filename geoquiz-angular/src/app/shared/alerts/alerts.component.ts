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
  alert: Alert;

  constructor(private alertService: AlertsService) {}

  ngOnInit() {
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

  close() {
    this.alertService.clear();
  }
}
