import { Component, OnInit } from '@angular/core';
import { CountryService } from '../shared/country/country.service';
import { Country } from '../shared/country/country';

@Component({
  selector: 'geo-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  countries: Country[];

  constructor(private countryService: CountryService) { }

  ngOnInit() {
    this.countryService.getCountries().subscribe(res => {
        this.countries = res.resources;
      });
  }

}
