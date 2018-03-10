import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Country, Page } from './country';

@Injectable()
export class CountryService {

  constructor(private http: HttpClient) { }

  getCountries(offset: number, limit: number, sort: string): Observable<Page<Country>> {
    let params = new HttpParams();
    if (offset) {
      params = params.append('offset', offset.toString());
    }
    if (limit) {
      params = params.append('limit', limit.toString());
    }
    if (sort) {
      params = params.append('sort', sort);
    }
    return this.http.get<Page<Country>>('api/countries', { params: params });
  }

}
