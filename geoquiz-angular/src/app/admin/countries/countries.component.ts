import { Component, OnInit } from '@angular/core';
import {
  faSort,
  faSortUp,
  faSortDown
} from '@fortawesome/free-solid-svg-icons';

import { CountryService } from '../../shared/country/country.service';
import { Country } from '../../shared/country/country';

enum Order {
  ASC,
  DESC,
  NONE
}

export class PageInfo {
  size: number;

  totalElements: number;

  pageNumber: number;

  sort: string;

  public constructor(pageNumber: number, size: number, sort: string) {
    this.size = size;
    this.pageNumber = pageNumber;
    this.sort = sort;
    this.totalElements = 0;
  }
}

export class Column {
  label: string;

  prop: string;

  order: Order;

  sortable: boolean;

  public constructor(
    label: string,
    prop: string,
    sortable?: boolean,
    order?: Order
  ) {
    this.label = label;
    this.prop = prop;
    this.sortable = sortable == null ? false : sortable;
    this.order = order == null ? Order.NONE : order;
  }
}

@Component({
  selector: 'geo-countries',
  templateUrl: './countries.component.html',
  styleUrls: ['./countries.component.scss']
})
export class CountriesComponent implements OnInit {
  Order = Order;

  countries: Country[];

  page: PageInfo;

  columns: Column[];

  currentSortColumn: Column;

  faSort = faSort;
  faSortUp = faSortUp;
  faSortDown = faSortDown;

  constructor(private countryService: CountryService) {}

  ngOnInit() {
    this.columns = new Array<Column>();
    const columns = new Array<Column>();
    columns.push(new Column('Code', 'code', true, Order.ASC));
    columns.push(new Column('Name', 'name', true));
    columns.push(new Column('Total area', 'totalArea'));
    columns.push(new Column('Capital name', 'capital.name'));
    this.columns = columns;
    this.currentSortColumn = this.columns[0];
    this.page = new PageInfo(1, 10, this.currentSortColumn.prop);

    this.getCountries();
  }

  changePage() {
    this.getCountries();
  }

  sortPage(column: Column) {
    if (column.sortable) {
      if (column.order === Order.ASC) {
        column.order = Order.DESC;
      } else {
        column.order = Order.ASC;
      }
      if (this.currentSortColumn.label !== column.label) {
        this.currentSortColumn.order = Order.NONE;
        this.currentSortColumn = column;
      }

      let m = '';
      if (column.order === Order.DESC) {
        m += '-';
      }
      m += column.prop;
      this.page.sort = m;
      this.getCountries();
    }
  }

  private getCountries() {
    const offset = (this.page.pageNumber - 1) * this.page.size + 1;
    this.countryService
      .getCountries(offset, this.page.size, this.page.sort)
      .subscribe(pagedData => {
        this.countries = pagedData.resources;
        this.page.totalElements = pagedData.total;
      });
  }
}
