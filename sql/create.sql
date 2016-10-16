CREATE DATABASE `app` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `app`.`question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) DEFAULT NULL,
  `answer` varchar(1000) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `app`.`question` (`text`, `answer`, `num`) VALUES ('Последняя версия Java?', '8', '1');
INSERT INTO `app`.`question` (`text`, `answer`, `num`) VALUES ('Что такое наследование?', 'принцип ООП', '2');
INSERT INTO `app`.`question` (`text`, `answer`, `num`) VALUES ('Назовите методы Object', 'toString(), hashCode(), clone(), getClass()', '3');
INSERT INTO `app`.`question` (`text`, `answer`, `num`) VALUES ('Что такое замыкание?', 'JS', '4');