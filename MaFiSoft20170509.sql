-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: mafisoftBD
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(40) COLLATE latin1_german1_ci NOT NULL,
  `trainer_name` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Fatburn','Shahl','17:00:00','20:00:00'),(2,'Joga','Ali','15:00:00','19:00:00'),(3,'Cycle','Schmidt','18:00:00','21:00:00'),(4,'sdjkf','skdfjhsdk','13:23:00','14:22:00'),(5,'Ploetz','Tester','12:00:00','21:00:00');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customer_firstname` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `customer_lastname` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `birthday` datetime NOT NULL,
  `email` varchar(255) COLLATE latin1_german1_ci NOT NULL,
  `mobilephone` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `zipCode` int(5) NOT NULL,
  `city` varchar(70) COLLATE latin1_german1_ci NOT NULL,
  `street` varchar(45) COLLATE latin1_german1_ci NOT NULL,
  `create_time` date NOT NULL,
  `end_time` date NOT NULL,
  `customer_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('Peter','Yermash','1993-09-04 00:00:00','peter.yermash@fh-dortmund.de','',44227,'Dortmund','Am Gardenkamp 45','2017-04-24','2018-04-02',1),('Anil Can','Afak','1993-02-19 00:00:00','can.afak@fh-dortmund.de','',44135,'Dortmund','Königswall 15','2017-04-24','2018-04-02',11),('Michael','Tjupalow','1990-12-12 00:00:00','typalow.michael@fh-dortmund.de','',44135,'Dortmund','irgendwo 1','2017-04-25','2018-04-03',13),('Max','Mustermann','2012-12-12 00:00:00','','',12345,'Dreamland','Wallstreet 1','2017-04-26','2018-04-04',14),('asdf','sadf','2017-05-09 00:00:00','asdf','asdf',23212,'dsf','sdf','2017-05-09','2017-11-04',15);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_course`
--

DROP TABLE IF EXISTS `customer_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_course` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_course`
--

LOCK TABLES `customer_course` WRITE;
/*!40000 ALTER TABLE `customer_course` DISABLE KEYS */;
INSERT INTO `customer_course` VALUES (1,11,2),(2,11,1),(3,11,3),(4,11,4),(5,11,5);
/*!40000 ALTER TABLE `customer_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_course`
--

DROP TABLE IF EXISTS `video_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video_course` (
  `videoID` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(30) COLLATE latin1_german1_ci DEFAULT NULL,
  `trainerName` varchar(40) COLLATE latin1_german1_ci NOT NULL,
  `link` varchar(100) COLLATE latin1_german1_ci NOT NULL,
  `remark` text COLLATE latin1_german1_ci,
  PRIMARY KEY (`videoID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_course`
--

LOCK TABLES `video_course` WRITE;
/*!40000 ALTER TABLE `video_course` DISABLE KEYS */;
INSERT INTO `video_course` VALUES (1,'Joga für anfänger','Ali','jogahome.example/video=12345',''),(2,'Joga für profi','Ali','jogahome.example/video=54321','Hallo\n\nich teste \n\nwie weit es\n\ngehen kann'),(4,'sadf','asdf','asdf','sadf');
/*!40000 ALTER TABLE `video_course` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-09 14:12:37
