import { Component, OnInit, Input } from '@angular/core';
import { faBan } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'geo-indicator',
  templateUrl: './indicator.component.html',
  styleUrls: ['./indicator.component.scss']
})
export class IndicatorComponent implements OnInit {
  @Input() color: string;

  @Input() icon: any;

  @Input() label: string;

  @Input() value: string;

  constructor() {}

  ngOnInit(): void {
    if (!this.color) {
      this.color = 'rgb(0, 123, 255)';
    }
    if (!this.icon) {
      this.icon = faBan;
    }
  }
}
