export class Page<T> {
  resources: T[];

  total: number;
}

export class Area {
  land: number;

  water: number;
}

export class City {
  name: string;
}

export class Country {
  id: number;

  code: string;

  name: string;

  area: Area;

  capital: City;
}
