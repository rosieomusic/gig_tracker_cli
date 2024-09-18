BEGIN TRANSACTION;

DROP TABLE IF EXISTS band_bill;
DROP TABLE IF EXISTS bill;
DROP TABLE IF EXISTS venue;
DROP TABLE IF EXISTS band;

CREATE TABLE band(
band_id serial PRIMARY KEY,
"name" varchar(60) NOT NULL UNIQUE,
hometown varchar(60),
"state" varchar(2),
country varchar(60),
bandcamp_link varchar(100),
contact varchar(100)
);

CREATE TABLE venue(
venue_id serial PRIMARY KEY,
"name" varchar(100) NOT NULL,
city varchar(100),
"state" varchar(2),
country varchar(60),
contact varchar(60),
email_instagram varchar(100)
);

CREATE TABLE bill(
bill_id serial PRIMARY KEY,
description varchar(300) NOT NULL,
num_of_bands int NOT NULL,
booker_name varchar(60),
date_time date NOT NULL,
"cost" int NOT NULL,
flyer varchar(100),
venue_id int NOT NULL,
FOREIGN KEY(venue_id) REFERENCES venue(venue_id)
);

CREATE TABLE band_bill(
band_id int NOT NULL,
bill_id int NOT NULL,
PRIMARY KEY(band_id, bill_id),
FOREIGN KEY(band_id) REFERENCES band(band_id),
FOREIGN KEY(bill_id) REFERENCES bill(bill_id)
);

-- BAND DATA
--START TRANSACTION;
INSERT INTO band("name", hometown, "state", country, bandcamp_link, contact)
VALUES('Diuretic', 'Philadelphia', 'PA', 'USA', 'diuretic.bandcamp.com', '@diureticthebandthatilove'),
('Fake Dust', 'Portland', 'OR','USA','fakedust1.bandcamp.com', null),
('Bohemian', 'Portland', 'OR', 'USA', 'bohemianrock.bandcamp.com', null),
('Ratpiss', 'Montreal', null, 'Canada', 'ratpissmtl.bandcamp.com', '@ratpissmtl'),
('Artificial Scarcity', 'Philadelphia', 'PA', 'USA', 'artificialscarcity.bandcamp.com','@artificial-scarcity'),
('Insecticide', 'Philadelphia', 'PA', 'USA', 'insecticide-grind.bandcamp.com', '@insecticide.grind');


--VENUE DATA
--START TRANSACTION;
INSERT INTO venue("name", city, "state", country, contact, email_instagram)
VALUES('Haus (fka haus of yarga)', 'Phiadelphia', 'PA', 'USA', 'Salt', '@haus'),
('The WvrmHole', 'Philadelphia', 'PA', 'USA', null, '@wvrmhole.philly');


--BILL DATA
--START TRANSACTION;
INSERT INTO bill(description, num_of_bands, booker_name, date_time, "cost", flyer, venue_id)
VALUES('Grinding violence from the pacific northwest, with philadelphia saboteurs diuretic', 3, 'Mac W. & Zook Productions', '2024-04-13T19:00:00', 10, 'drive.google.com/file/d/1DY3IZP3Sjrjm6DO1o4NSTx4gwd5sa0cj/view', 2),
('Another ripper', 4,'Salt', '2024-04-21T20:30:00', 10, 'drive.google.com/file/d/1dZZhmtnxcdcSlKfkbXTBsBdUGFZRhoGe/view', 1);

--BAND BILL DATA
--START TRANSACTION;
INSERT INTO band_bill(band_id, bill_id)
VALUES((SELECT band_id FROM band WHERE "name" = 'Diuretic'), (SELECT bill_id FROM bill WHERE description ILIKE 'Grinding%')),
((SELECT band_id FROM band WHERE "name" = 'Fake Dust'), (SELECT bill_id FROM bill WHERE description ILIKE 'Grinding%')),
((SELECT band_id FROM band WHERE "name" = 'Bohemian'), (SELECT bill_id FROM bill WHERE description ILIKE 'Grinding%')),

((SELECT band_id FROM band WHERE "name" = 'Diuretic'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),
((SELECT band_id FROM band WHERE "name" = 'Ratpiss'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),
((SELECT band_id FROM band WHERE "name" = 'Artificial Scarcity'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),
((SELECT band_id FROM band WHERE "name" = 'Insecticide'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%'));



COMMIT;