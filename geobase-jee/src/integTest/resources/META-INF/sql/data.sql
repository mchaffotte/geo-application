INSERT INTO city (id, name) VALUES (1, 'Brussels');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (1, 'BE', 'Belgium', 3027800, 25000, 1);

INSERT INTO city (id, name) VALUES (2, 'Zagreb');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (2, 'HR', 'Croatia', 5597400, 62000, 2);

INSERT INTO city (id, name) VALUES (3, 'Copenhagen');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (3, 'DK', 'Denmark', 4243400, 66000, 3);

INSERT INTO city (id, name) VALUES (4, 'London');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (4, 'GB-ENG', 'England', 13027900, 265900, 4);

INSERT INTO city (id, name) VALUES (5, 'Paris');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (5, 'FR', 'France', 54997000, 153000, 5);

INSERT INTO city (id, name) VALUES (6, 'Berlin');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (6, 'DE', 'Germany', 34867200, 835000, 6);

INSERT INTO city (id, name) VALUES (7, 'Rome');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (7, 'IT', 'Italy', 29414000, 720000, 7);

INSERT INTO city (id, name) VALUES (8, 'Amsterdam');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (8, 'NL', 'The Netherlands', 3389300, 765000, 8);

INSERT INTO city (id, name) VALUES (9, 'Warsaw');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (9, 'PL', 'Poland', 30425500, 843000, 9);

INSERT INTO city (id, name) VALUES (10, 'Lisbon');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (10, 'PT', 'Portugal', 9147000, 62000, 10);

INSERT INTO city (id, name) VALUES (11, 'Madrid');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (11, 'ES', 'Spain', 49898000, 639000, 11);

INSERT INTO city (id, name) VALUES (12, 'Stockholm');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (12, 'SE', 'Sweden', 41033500, 3996000, 12);

INSERT INTO city (id, name) VALUES (13, 'Bern');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (13, 'CH', 'Switzerland', 3999700, 128000, 13);

INSERT INTO city (id, name) VALUES (14, 'Cardiff');
INSERT INTO country (id, code, name, land_area, water_area, capital_id) VALUES (14, 'GB-WLS', 'Wales', 2073500, 4400, 14);

ALTER SEQUENCE city_sequence RESTART WITH 15;
ALTER SEQUENCE country_sequence RESTART WITH 15;
