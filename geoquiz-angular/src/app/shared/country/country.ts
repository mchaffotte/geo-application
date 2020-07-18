export interface Page<T> {
  resources: T[];

  total: number;
}

export interface Area {
  land: number;

  water: number;
}

export interface City {
  name: string;
}

export interface Country {
  id: number;

  code: string;

  name: string;

  area: Area;

  capital: City;
}
