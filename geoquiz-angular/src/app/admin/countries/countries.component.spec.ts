import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';

import { CountriesComponent } from './countries.component';
import { CountryService } from 'src/app/shared/country/country.service';
import { Page, Country } from 'src/app/shared/country/country';

class MockCountryService {
  getCountries(offset: number, limit: number, sort: string): Observable<Page<Country>> {
    return of();
  }
}

describe('CountriesComponent', () => {
  let component: CountriesComponent;
  let fixture: ComponentFixture<CountriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CountriesComponent],
      imports: [FontAwesomeModule, NgbModule],
      providers: [{ provide: CountryService, useClass: MockCountryService }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CountriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
