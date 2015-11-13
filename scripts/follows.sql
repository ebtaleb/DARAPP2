CREATE TABLE IF NOT EXISTS `follows` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user` VARCHAR(45) NOT NULL,
  `followed_event_id` INT NOT NULL,
   CONSTRAINT fk_user_follow FOREIGN KEY (user) REFERENCES users (username), 
   CONSTRAINT fk_event_id_follow FOREIGN KEY (followed_event_id) REFERENCES events (id));

INSERT INTO follows(user, followed_event_id) VALUES ('mkyong', 1);
INSERT INTO follows(user, followed_event_id) VALUES ('mkyong', 2);