import { Component } from '@angular/core';

@Component({
  selector: 'geo-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
})
export class AdminComponent {
  isNavbarCollapsed: boolean;

  constructor() {
    this.isNavbarCollapsed = true;
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
}
