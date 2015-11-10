CREATE TABLE IF NOT EXISTS `events` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `owner` VARCHAR(45) NOT NULL,
  `title` VARCHAR( 60 ) NOT NULL,
  `descr` VARCHAR( 140 ) NOT NULL,
  `event_type` VARCHAR( 15 ) NOT NULL,
  `address` VARCHAR( 80 ) NOT NULL,
  `start_date` DATE NOT NULL,
  `start_time` TIME NOT NULL,
  `lat` FLOAT( 10, 6 ) NOT NULL,
  `lng` FLOAT( 10, 6 ) NOT NULL,
   CONSTRAINT fk_owner FOREIGN KEY (owner) REFERENCES users (username));

INSERT INTO events(owner, title, address, descr, event_type, start_date, start_time, lat, lng)
VALUES ('mkyong', 'Frankie Johnnie & Luigo Too', '939 W El Camino Real, Mountain View, CA', "Nice", "RANDO", STR_TO_DATE('1-01-2016', '%d-%m-%Y'), STR_TO_DATE('11:30', '%h:%i'), '37.386339','-122.085823');
INSERT INTO events(owner, title, address, descr, event_type, start_date, start_time, lat, lng)
VALUES ('mkyong', 'Amici\'s East Coast Pizzeria', '790 Castro St, Mountain View, CA', "VeryNice", "RANDO", STR_TO_DATE('1-01-2015', '%d-%m-%Y'), STR_TO_DATE('11:30', '%h:%i'), '37.38714','-122.083235');
INSERT INTO events(owner, title, address, descr, event_type, start_date, start_time, lat, lng)
VALUES ('mkyong', 'Kapp\'s Pizza Bar & Grill', '191 Castro St, Mountain View, CA', "Waow", "VELO", STR_TO_DATE('1-01-2012', '%d-%m-%Y'), STR_TO_DATE('11:30', '%h:%i'), '37.393885','-122.078916');
