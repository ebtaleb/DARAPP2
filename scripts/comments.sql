CREATE TABLE IF NOT EXISTS `comments` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `event_id` INT NOT NULL ,
  `owner` VARCHAR(45) NOT NULL,
  `content` VARCHAR( 60 ) NOT NULL,
  `creation_time` TIMESTAMP,
   CONSTRAINT fk_cowner FOREIGN KEY (owner) REFERENCES users (username));

INSERT INTO comments(owner, content, event_id)
VALUES ('mkyong', 'excellent hahaha', 1)