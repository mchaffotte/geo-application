import { Component, OnInit } from '@angular/core';
import { faGlobeEurope } from '@fortawesome/free-solid-svg-icons';

import { CountryService } from '../../country/country.service';

@Component({
  selector: 'geo-total-countries',
  templateUrl: './total-countries.component.html',
  styleUrls: ['./total-countries.component.scss']
})
export class TotalCountriesComponent implements OnInit {
  faGlobeEurope = faGlobeEurope;

  value: number | string;

  constructor(private countryService: CountryService) {}

  ngOnInit(): void {
    this.countryService.getCountries(0, 1, null).subscribe(
      pagedData => {
        this.value = pagedData.total;
      },
      error => {
        this.value = '?';
      }
    );
  }
}
