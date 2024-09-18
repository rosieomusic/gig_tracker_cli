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
date_time timestamp NOT NULL,
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
('Insecticide', 'Philadelphia', 'PA', 'USA', 'insecticide-grind.bandcamp.com', '@insecticide.grind'),
('Poison Ruin', 'Philadelphia', 'PA', 'USA', 'poisonruin.bandcamp.com', '@poisonruin'),
('Vacation', 'Cincinnati', 'OH', 'USA', 'vacation.bandcamp.com', '@vacation_rnr'),
('Destructos', 'Philadelphia', 'PA', 'USA', 'destructos.bandcamp.com', 'destructosphl'),
('Waste Man', 'New York', 'NY', 'USA', 'wastemannola.bandcamp.com', null),
('ME.N.U', 'Philadelphia', 'PA', 'USA', 'menumenu.bandcamp.com', '@me.n.u.band'),
('Driltusk', 'Philadelphia', 'PA', 'USA', 'driltusk.bandcamp.com', '@driltusk'),
('ABBATIA', 'Philadelphia', 'PA', 'USA', 'abbatia.bandcamp.com', '@abbatia_'),
('SHARE', 'Philadelphia', 'PA', 'USA', 'sharephl.bandcamp.com', null),
('CRY9C', 'Philadelphia', 'PA', 'USA', 'fakemp3.bandcamp.com', '@cry.9c'),
('Thought Control', null, 'NJ', 'USA', 'crewcuts.bandcamp.com/album/sick-tired-of-the-talking-heads', '@thoughtcontrolhc'),
('The Tarrys', 'Danbury', 'CT', 'USA', 'thetarrys.bandcamp.com','@thetarrys'),
('Scramble', 'Baltimore', 'MD', 'USA', 'scramblehc.bandcamp.com', '@scramble.bmore');

--VENUE DATA
--START TRANSACTION;
INSERT INTO venue("name", city, "state", country, contact, email_instagram)
VALUES('Foto Club', 'Philadelphia', 'PA', 'USA', 'Jim', 'jimxshomo@gmail.com'),
('Haus (fka haus of yarga)', 'Phiadelphia', 'PA', 'USA', 'Salt', '@haus'),
('Ukie Club', 'Philadelphia', 'PA', 'USA', '4333 Collective', '@4333 Collective'),
('Another Round', 'Richmond', 'VA', 'USA','Weird World', null),
('Uranus', 'Baltimore', 'MD','USA', null, null),
('The WvrmHole', 'Philadelphia', 'PA', 'USA', null, '@wvtmhole.philly'),
('Cousin Dannys', 'Philadelphia', 'PA', 'USA', 'Cousin Danny', '@cousindannyslounge');

--BILL DATA
--START TRANSACTION;
INSERT INTO bill(description, num_of_bands, booker_name, date_time, "cost", flyer, venue_id)
VALUES('Grinding violence from the pacific northwest, with philadelphia saboteurs diuretic', 3, 'Mac W. & Zook Productions', '2024-04-13', 10, 'drive.google.com/file/d/1DY3IZP3Sjrjm6DO1o4NSTx4gwd5sa0cj/view', 6),
('Another ripper', 4,'Salt', '2024-04-21', 10, 'drive.google.com/file/d/1dZZhmtnxcdcSlKfkbXTBsBdUGFZRhoGe/view', 2),
('Couple ghouls & friends Fest!', 10, 'Couple Ghouls', '2024-04-27', 30, 'drive.google.com/file/d/1hU92hv_j3XOFRV42z2i36S_R1x85YRpI/view', 3),
('He has risen', 4, null, '2024-03-31', 10, 'drive.google.com/file/d/13THBDB7NW3tm9VjRYGEk8nIP_ar3ZBL8/view', 5);

--BAND BILL DATA
--START TRANSACTION;
INSERT INTO band_bill(band_id, bill_id)
VALUES((SELECT band_id FROM band WHERE "name" = 'Diuretic'), (SELECT bill_id FROM bill WHERE description ILIKE 'Grinding%')),
((SELECT band_id FROM band WHERE "name" = 'Fake Dust'), (SELECT bill_id FROM bill WHERE description ILIKE 'Grinding%')),
((SELECT band_id FROM band WHERE "name" = 'Bohemian'), (SELECT bill_id FROM bill WHERE description ILIKE 'Grinding%')),

((SELECT band_id FROM band WHERE "name" = 'Diuretic'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),
((SELECT band_id FROM band WHERE "name" = 'Ratpiss'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),
((SELECT band_id FROM band WHERE "name" = 'Artificial Scarcity'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),
((SELECT band_id FROM band WHERE "name" = 'Insecticide'), (SELECT bill_id FROM bill WHERE description ILIKE 'Another%')),

((SELECT band_id FROM band WHERE "name" = 'Diuretic'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'Poison Ruin'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'Vacation'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'Destructos'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'Waste Man'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'ME.N.U'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'Driltusk'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'ABBATIA'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'SHARE'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),
((SELECT band_id FROM band WHERE "name" = 'CRY9C'), (SELECT bill_id FROM bill WHERE description ILIKE 'Couple%')),

((SELECT band_id FROM band WHERE "name" = 'Diuretic'), (SELECT bill_id FROM bill WHERE description ILIKE 'He%')),
((SELECT band_id FROM band WHERE "name" = 'Thought Control'), (SELECT bill_id FROM bill WHERE description ILIKE 'He%')),
((SELECT band_id FROM band WHERE "name" = 'The Tarrys'), (SELECT bill_id FROM bill WHERE description ILIKE 'He%')),
((SELECT band_id FROM band WHERE "name" = 'Scramble'), (SELECT bill_id FROM bill WHERE description ILIKE 'He%'));


COMMIT;
