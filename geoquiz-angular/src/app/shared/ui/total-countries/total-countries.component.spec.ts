import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Observable, of } from 'rxjs';

import { TotalCountriesComponent } from './total-countries.component';
import { CountryService } from 'src/app/shared/country/country.service';
import { Page, Country } from 'src/app/shared/country/country';

class MockCountryService {
  getCountries(offset: number, limit: number, sort: string): Observable<Page<Country>> {
    return of();
  }
}

describe('TotalCountriesComponent', () => {
  let component: TotalCountriesComponent;
  let fixture: ComponentFixture<TotalCountriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TotalCountriesComponent],
      imports: [FontAwesomeModule],
      providers: [{ provide: CountryService, useClass: MockCountryService }]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TotalCountriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
