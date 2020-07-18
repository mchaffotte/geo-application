import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Subject } from 'rxjs';

import { Action } from './countdown.directive';

@Component({
  selector: 'geo-countdown',
  templateUrl: './countdown.component.html',
  styleUrls: ['./countdown.component.scss'],
})
export class CountdownComponent {
  interval: number;
  timeLeft: number;

  circumference = 289.026;
  progress: number;

  @Input() seconds: number;
  @Input() actions: Subject<Action>;
  @Output() over = new EventEmitter<number>();

  constructor() {
    this.progress = 0;
    this.interval = 200;
    this.seconds = 10;
    this.timeLeft = this.seconds * 1000;
    this.actions = new Subject<Action>();
  }

  onProgress(timeLeft: number): void {
    this.timeLeft = timeLeft;
    this.progress = this.circumference - (this.circumference * this.timeLeft) / (this.seconds * 1000);
    if (this.timeLeft <= 0) {
      this.over.emit();
    }
  }

  getRemainingSeconds(time: number): number {
    return Math.ceil(time / 1000);
  }
}
