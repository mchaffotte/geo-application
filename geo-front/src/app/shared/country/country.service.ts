import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Country, Page } from './country';

@Injectable()
export class CountryService {

  constructor(private http: HttpClient) { }

  getCountries(): Observable<Page<Country>> {
    return this.http.get<Page<Country>>('api/countries');
  }

}
