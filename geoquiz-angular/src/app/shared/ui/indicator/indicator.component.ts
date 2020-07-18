import { Component, Input } from '@angular/core';
import { faBan, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'geo-indicator',
  templateUrl: './indicator.component.html',
  styleUrls: ['./indicator.component.scss'],
})
export class IndicatorComponent {
  @Input() color: string;

  @Input() icon: IconDefinition;

  @Input() label: string;

  @Input() value: string | number;

  constructor() {
    this.color = 'rgb(0, 123, 255)';
    this.icon = faBan;
    this.label = '';
    this.value = '';
  }
}
