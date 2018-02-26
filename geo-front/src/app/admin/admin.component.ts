import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'geo-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  isNavbarCollapsed: boolean;

  ngOnInit() {
    this.isNavbarCollapsed = true;
  }

  collapseNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

}
