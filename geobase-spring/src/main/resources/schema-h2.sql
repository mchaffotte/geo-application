CREATE SEQUENCE city_sequence INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE country_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE city (id BIGINT NOT NULL, name VARCHAR(255) NOT NULL, PRIMARY KEY(id));
CREATE TABLE country (id BIGINT NOT NULL, code VARCHAR(6) NOT NULL, name VARCHAR(25) NOT NULL, land_area INTEGER, water_area INTEGER, capital_id BIGINT NOT NULL, PRIMARY KEY(id));
ALTER TABLE country ADD CONSTRAINT uk_country_code UNIQUE(code);
ALTER TABLE country ADD CONSTRAINT uk_capital_id UNIQUE(capital_id);
ALTER TABLE country ADD CONSTRAINT fk_country_capital_id FOREIGN KEY(capital_id) REFERENCES city;

CREATE SEQUENCE question_sequence INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE quiz_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE image (id BINARY NOT NULL, type VARCHAR(10), country_id BIGINT, PRIMARY KEY(id));
CREATE TABLE question (id BIGINT NOT NULL, wording VARCHAR(255), answer VARCHAR(50), image_id BINARY, quiz_id BIGINT, PRIMARY KEY(id));
CREATE TABLE suggestion (question_id BIGINT NOT NULL, suggestion VARCHAR(255));
CREATE TABLE quiz (id BIGINT NOT NULL, PRIMARY KEY(id));
ALTER TABLE image ADD CONSTRAINT fk_image_country FOREIGN KEY(country_id) REFERENCES country;
ALTER TABLE question ADD CONSTRAINT fk_question_image FOREIGN KEY(image_id) REFERENCES image;
ALTER TABLE question ADD CONSTRAINT fk_question_quiz FOREIGN KEY(quiz_id) REFERENCES quiz;
ALTER TABLE suggestion ADD CONSTRAINT fk_suggestion_question FOREIGN KEY(question_id) REFERENCES question;