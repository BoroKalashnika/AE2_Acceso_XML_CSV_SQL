CREATE DATABASE `population` DEFAULT CHARACTER SET utf8mb4;

USE `population`;

CREATE TABLE `users` (
	`id` INT(10) AUTO_INCREMENT,  
	`login` VARCHAR(20) NOT NULL,
	`password` VARCHAR(40) NOT NULL,
	`type` VARCHAR(6) NOT NULL,
	PRIMARY KEY (`ID`)
);


INSERT INTO users (login,`password`,`type`) VALUES ("admin","21232f297a57a5a743894a0e4a801fc3","admin");

CREATE USER 'admin'@'localhost' IDENTIFIED BY '21232f297a57a5a743894a0e4a801fc3';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;


