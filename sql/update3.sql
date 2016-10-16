INSERT INTO `app`.`theme` (`title`, `quiz`) VALUES ('Java 8', '1');

INSERT INTO `app`.`question` (`text`, `answer`, `num`, `theme`) VALUES ('What is constructor?', 'Constructor is just like a method that is used to initialize the state of an object. It is invoked at the time of object creation.', '1', 'Java 8');
INSERT INTO `app`.`question` (`text`, `answer`, `num`, `theme`) VALUES ('Can you make a constructor final?', 'No, constructor cant be final.', '2', 'Java 8');
INSERT INTO `app`.`question` (`text`, `answer`, `num`, `theme`) VALUES ('What is Inheritance?', 'Inheritance is a mechanism in which one object acquires all the properties and behaviour of another object of another class.', '3', 'Java 8');

UPDATE `app`.`vox_user` SET `theme`='Java 8' WHERE `id`='1';

INSERT INTO `app`.`quiz` (`text`, `theme`, `answer_num`, `num`) VALUES ('What is the scope of default access specifier?', 'Java 8', '3', '1');
INSERT INTO `app`.`quiz` (`text`, `theme`, `answer_num`, `num`) VALUES ('Which alogorithm is used by garabage collector ?', 'Java 8', '3', '2');
INSERT INTO `app`.`quiz` (`text`, `theme`, `answer_num`, `num`) VALUES ('What is the super class for all Exception and error?', 'Java 8', '4', '3');

INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('3', 'default member are available to all the packages', '1');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('3', 'default member is available only within the class', '2');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('3', 'default member is available only within the package', '3');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('3', 'None of the above', '4');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('4', 'Mark', '1');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('4', 'Sweep', '2');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('4', 'Mark and sweep', '3');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('4', 'None', '4');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('5', 'Run Time Exception', '1');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('5', 'Compile Time Exception', '2');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('5', 'Exception', '3');
INSERT INTO `app`.`quiz_opt` (`quiz_id`, `text`, `num`) VALUES ('5', 'Throwable', '4');
