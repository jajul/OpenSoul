ALTER TABLE `app`.`question`
ADD COLUMN `theme` VARCHAR(100) NULL AFTER `num`;

SET SQL_SAFE_UPDATES = 0;

UPDATE `app`.`question` SET `theme`='Java' WHERE `num`='1';
UPDATE `app`.`question` SET `theme`='Java' WHERE `num`='2';
UPDATE `app`.`question` SET `theme`='Java' WHERE `num`='3';
UPDATE `app`.`question` SET `theme`='JavaScript' WHERE `num`='4';

CREATE TABLE `app`.`theme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `app`.`theme` (`title`) VALUES ('Java');
INSERT INTO `app`.`theme` (`title`) VALUES ('JavaScript');

CREATE TABLE `app`.`vox_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(50) NOT NULL,
  `theme` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `app`.`vox_user` (`user`, `theme`) VALUES ('iuser', 'Java');
INSERT INTO `app`.`vox_user` (`user`, `theme`) VALUES ('test', 'JavaScript');

ALTER TABLE `app`.`theme`
ADD COLUMN `quiz` CHAR(1) NULL AFTER `title`;

UPDATE `app`.`theme` SET `quiz`='1' WHERE `id`='1';
UPDATE `app`.`theme` SET `quiz`='1' WHERE `id`='2';

CREATE TABLE `app`.`quiz` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(1000) NULL,
  `theme` VARCHAR(100) NULL,
  `answer_num` INT NULL,
  `num` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `app`.`quiz_opts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quiz_id` INT NOT NULL,
  `text` VARCHAR(1000) NULL,
  `num` INT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `app`.`quiz` (`text`, `theme`, `num`) VALUES ('Set right visibility order: default, private, protected, public', 'Java', '1');
INSERT INTO `app`.`quiz` (`text`, `theme`, `num`) VALUES ('A - class, B - interface. Chose right option:', 'Java', '2');

INSERT INTO `app`.`quiz_opts` (`quiz_id`, `text`, `num`) VALUES ('1', 'default - private - protected - public', '1');
INSERT INTO `app`.`quiz_opts` (`quiz_id`, `text`, `num`) VALUES ('1', 'private - default - protected - public', '2');
INSERT INTO `app`.`quiz_opts` (`quiz_id`, `text`, `num`) VALUES ('1', 'private - protected - default - public', '3');
INSERT INTO `app`.`quiz_opts` (`quiz_id`, `text`, `num`) VALUES ('2', 'A extends B', '1');
INSERT INTO `app`.`quiz_opts` (`quiz_id`, `text`, `num`) VALUES ('2', 'A implements B', '2');
INSERT INTO `app`.`quiz_opts` (`quiz_id`, `text`, `num`) VALUES ('2', 'A super B', '3');

UPDATE `app`.`quiz` SET `answer_num`='2' WHERE `id`='1';
UPDATE `app`.`quiz` SET `answer_num`='2' WHERE `id`='2';

ALTER TABLE `app`.`quiz_opts`
RENAME TO  `app`.`quiz_opt` ;

UPDATE `app`.`question` SET `num`='1' WHERE `num`='4';
