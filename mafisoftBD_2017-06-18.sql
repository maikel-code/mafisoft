# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.18)
# Datenbank: mafisoftBD
# Erstellt am: 2017-06-18 21:15:39 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Export von Tabelle course
# ------------------------------------------------------------

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(40) COLLATE latin1_german1_ci NOT NULL,
  `trainer_name` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `start` time DEFAULT NULL,
  `end` time DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;

INSERT INTO `course` (`course_id`, `course_name`, `trainer_name`, `start`, `end`)
VALUES
	(1,'Fatburn','Shahl','17:00:00','20:00:00'),
	(2,'Joga','Ali','15:00:00','19:00:00'),
	(3,'Cycle','Schmidt','18:00:00','21:00:00'),
	(5,'Ploetz','Tester','12:00:00','21:00:00'),
	(6,'test','test','12:33:00','12:44:00');

/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;


# Export von Tabelle customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customer_firstname` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `customer_lastname` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `birthday` datetime NOT NULL,
  `email` varchar(255) COLLATE latin1_german1_ci NOT NULL,
  `mobilephone` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `zipCode` int(5) NOT NULL,
  `city` varchar(70) COLLATE latin1_german1_ci NOT NULL,
  `street` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `create_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `customer_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;

INSERT INTO `customer` (`customer_firstname`, `customer_lastname`, `birthday`, `email`, `mobilephone`, `zipCode`, `city`, `street`, `create_time`, `end_time`, `customer_id`)
VALUES
	('Peter','Yermash','1993-09-04 00:00:00','peter.yermash@fh-dortmund.de','',44227,'Dortmund','Am Gardenkamp 45','2017-04-24','2018-04-02',1),
	('Anil Can','Afak','1993-02-19 00:00:00','can.afak@fh-dortmund.de','',44135,'Dortmund','Königswall 15','2017-04-24','2018-04-02',11),
	('Michael 12','Tjupalow','1990-12-12 00:00:00','typalow.michael@fh-dortmund.de','',44135,'Dortmund','irgendwo 1','2017-04-25','2018-04-03',13);

/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;


# Export von Tabelle customer_course
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_course`;

CREATE TABLE `customer_course` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

LOCK TABLES `customer_course` WRITE;
/*!40000 ALTER TABLE `customer_course` DISABLE KEYS */;

INSERT INTO `customer_course` (`id`, `customer_id`, `course_id`)
VALUES
	(2,11,1),
	(3,11,3),
	(4,11,4),
	(6,13,4),
	(7,13,3),
	(17,11,5);

/*!40000 ALTER TABLE `customer_course` ENABLE KEYS */;
UNLOCK TABLES;


# Export von Tabelle video_course
# ------------------------------------------------------------

DROP TABLE IF EXISTS `video_course`;

CREATE TABLE `video_course` (
  `videoID` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(30) COLLATE latin1_german1_ci DEFAULT NULL,
  `trainerName` varchar(40) COLLATE latin1_german1_ci NOT NULL,
  `link` varchar(100) COLLATE latin1_german1_ci NOT NULL,
  `remark` text COLLATE latin1_german1_ci,
  PRIMARY KEY (`videoID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

LOCK TABLES `video_course` WRITE;
/*!40000 ALTER TABLE `video_course` DISABLE KEYS */;

INSERT INTO `video_course` (`videoID`, `courseName`, `trainerName`, `link`, `remark`)
VALUES
	(1,'Joga für anfänger','Ali','jogahome.example/video=12345',''),
	(2,'Joga für profi','Ali','jogahome.example/video=54321','Hallo\n\nich teste \n\nwie weit es\n\ngehen kann');

/*!40000 ALTER TABLE `video_course` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
