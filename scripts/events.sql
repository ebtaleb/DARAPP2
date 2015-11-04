CREATE TABLE IF NOT EXISTS `events` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR( 60 ) NOT NULL ,
  `descr` VARCHAR( 140 ) NOT NULL,
  `address` VARCHAR( 80 ) NOT NULL ,
  `ev_date` DATE NOT NULL,
  `lat` FLOAT( 10, 6 ) NOT NULL ,
  `lng` FLOAT( 10, 6 ) NOT NULL);

INSERT INTO `events` (`name`, `address`, `descr`, `ev_date`, `lat`, `lng`)
VALUES ('Frankie Johnnie & Luigo Too','939 W El Camino Real, Mountain View, CA', "Nice", STR_TO_DATE('1-01-2016', '%d-%m-%Y'), '37.386339','-122.085823');
INSERT INTO `events` (`name`, `address`, `descr`, `ev_date`, `lat`, `lng`)
VALUES ('Amici\'s East Coast Pizzeria','790 Castro St, Mountain View, CA', "VeryNice", STR_TO_DATE('1-01-2015', '%d-%m-%Y'),'37.38714','-122.083235');
INSERT INTO `events` (`name`, `address`, `descr`, `ev_date`, `lat`, `lng`)
VALUES ('Kapp\'s Pizza Bar & Grill','191 Castro St, Mountain View, CA', "Waow", STR_TO_DATE('1-01-2012', '%d-%m-%Y'), '37.393885','-122.078916');
INSERT INTO `events` (`name`, `address`, `descr`, `ev_date`, `lat`, `lng`)
VALUES ('Round Table Pizza: Mountain View','570 N Shoreline Blvd, Mountain View, CA', "OK", STR_TO_DATE('1-01-2012', '%d-%m-%Y'), '37.402653','-122.079354');
INSERT INTO `events` (`name`, `address`, `descr`, `ev_date`, `lat`, `lng`)
VALUES ('Tony & Alba\'s Pizza & Pasta','619 Escuela Ave, Mountain View, CA', "Hmmm", STR_TO_DATE('1-01-2013', '%d-%m-%Y'), '37.394011','-122.095528');
INSERT INTO `events` (`name`, `address`, `descr`, `ev_date`, `lat`, `lng`)
VALUES ('Oregano\'s Wood-Fired Pizza','4546 El Camino Real, Los Altos, CA',"buerk", STR_TO_DATE('1-01-2012', '%d-%m-%Y'), '37.401724','-122.114646');
