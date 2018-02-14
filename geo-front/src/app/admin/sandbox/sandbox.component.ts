import { Component, OnInit } from '@angular/core';

import { Country } from '../../shared/country/country';
import { CountryService } from '../../shared/country/country.service';

@Component({
  selector: 'geo-sandbox',
  templateUrl: './sandbox.component.html',
  styleUrls: ['./sandbox.component.scss']
})
export class SandboxComponent implements OnInit {

  countries: Country[];

  constructor(private countryService: CountryService) { }

  ngOnInit() {
    this.countryService.getCountries().subscribe(res => {
      this.countries = res.resources;
    });
  }

}
