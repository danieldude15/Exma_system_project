-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: aes
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `courseid` int(2) NOT NULL,
  `coursename` varchar(45) CHARACTER SET latin1 NOT NULL,
  `fieldid` int(2) NOT NULL,
  PRIMARY KEY (`courseid`,`fieldid`),
  KEY `coursefieldid_idx` (`fieldid`),
  CONSTRAINT `coursefieldif` FOREIGN KEY (`fieldid`) REFERENCES `fields` (`fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Conducts',1),(1,'Calculus',2),(1,'Operating Systems',3),(2,'Hashmal Esh',1),(2,'Calculus-2',2),(2,'Object Oriented Programing',3);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exams` (
  `examid` int(2) NOT NULL AUTO_INCREMENT,
  `timeduration` int(11) NOT NULL,
  `fieldid` int(2) NOT NULL,
  `courseid` int(2) NOT NULL,
  `teacherid` int(11) NOT NULL,
  PRIMARY KEY (`examid`,`courseid`,`fieldid`),
  KEY `examscourseid_idx` (`courseid`),
  KEY `teacherrefids_idx` (`fieldid`,`teacherid`),
  KEY `examref_report` (`examid`,`fieldid`,`courseid`,`teacherid`),
  CONSTRAINT `examscourseid` FOREIGN KEY (`courseid`) REFERENCES `courses` (`courseid`) ON UPDATE NO ACTION,
  CONSTRAINT `teacherrefids` FOREIGN KEY (`fieldid`, `teacherid`) REFERENCES `teacher_fields` (`fieldid`, `teacherid`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (1,15,1,1,302218136),(1,30,2,1,302218136),(1,20,3,1,302218136),(1,8,1,2,302218136),(1,35,2,2,302218136),(1,25,3,2,302218136),(2,20,3,1,204360317),(3,80,3,1,204360317);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams_report`
--

DROP TABLE IF EXISTS `exams_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exams_report` (
  `examid` int(2) NOT NULL,
  `courseid` int(2) NOT NULL,
  `fieldid` int(2) NOT NULL,
  `autherid` int(11) NOT NULL,
  `activatorid` int(11) NOT NULL,
  `code` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `type` tinyint(4) NOT NULL,
  `dateactivated` datetime NOT NULL,
  `date_time_locked` datetime NOT NULL,
  `participated` int(11) NOT NULL,
  `not_in_time_submitters` int(11) NOT NULL,
  `submitted` int(11) NOT NULL,
  PRIMARY KEY (`examid`,`courseid`,`fieldid`,`autherid`,`code`,`type`,`dateactivated`),
  KEY `examrefIndex` (`examid`,`fieldid`,`courseid`,`autherid`),
  KEY `user_ref_idx` (`autherid`),
  KEY `user_ref8_idx` (`activatorid`),
  KEY `report_solved_index` (`examid`,`fieldid`,`courseid`,`code`),
  CONSTRAINT `exam_ref22` FOREIGN KEY (`examid`, `courseid`, `fieldid`) REFERENCES `exams` (`examid`, `courseid`, `fieldid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_ref7` FOREIGN KEY (`autherid`) REFERENCES `users` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_ref8` FOREIGN KEY (`activatorid`) REFERENCES `users` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams_report`
--

LOCK TABLES `exams_report` WRITE;
/*!40000 ALTER TABLE `exams_report` DISABLE KEYS */;
INSERT INTO `exams_report` VALUES (1,1,1,302218136,302218136,'int8',1,'2018-06-23 00:00:00','2018-06-23 00:00:00',6,0,6),(1,1,3,302218136,302218136,'abc1',1,'2018-06-23 00:00:00','2018-06-23 00:00:00',6,0,6),(1,2,1,302218136,302218136,'ele0',1,'2018-06-23 00:00:00','2018-06-23 00:00:00',6,0,6),(1,2,3,302218136,302218136,'ddd1',1,'2018-06-23 00:00:00','2018-06-23 00:00:00',6,0,6);
/*!40000 ALTER TABLE `exams_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fields`
--

DROP TABLE IF EXISTS `fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fields` (
  `fieldid` int(2) NOT NULL,
  `fieldname` varchar(45) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`fieldid`),
  UNIQUE KEY `fieldid_UNIQUE` (`fieldid`),
  UNIQUE KEY `fieldname_UNIQUE` (`fieldname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
INSERT INTO `fields` VALUES (3,'Computer Science'),(1,'Electrical Engineering'),(2,'Mathematics');
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionid` int(3) NOT NULL AUTO_INCREMENT,
  `question` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `answerindex` int(11) NOT NULL,
  `fieldid` int(2) NOT NULL,
  `teacherid` int(11) NOT NULL,
  PRIMARY KEY (`questionid`,`fieldid`),
  KEY `teachersfieldsid_idx` (`fieldid`,`teacherid`),
  CONSTRAINT `teacherfieldid` FOREIGN KEY (`fieldid`, `teacherid`) REFERENCES `teacher_fields` (`fieldid`, `teacherid`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'how does a NOT gate work in a semi conductor?','there is a small elf there that return the oposite of what it gets ','there is a small transistor that does magic like harry potter and returns not!','its simple...','42',3,1,302218136),(1,'how many bits in a byte','4','8','16','32',2,3,302218136),(2,'why do 2 electrons are pulled towards eachother?','because that the way the cookie crubmbles','42','because electrons and nutrons are cool','non of the above',4,1,302218136),(2,'what is the nearest value of the suqare root of 2???','1.112','1.22','1.334','1.44',1,2,302218136),(2,'what is NP?','its Hard!','its Complete!','its No Problem','its No Power In My Smartphone!',2,3,302218136),(3,'how many volts are in your wall?','110','220','5','12',2,1,302218136),(3,'how long does a soccer game last?','untill the ref dies','91 minutes','110 minutes','90 minutes',4,2,302218136),(3,'what is a context switch?','its when you have nothing to talk about and want to change the subject','its when an OS switches between 2 applications its running','its when an OS thinkgs its smart','all answers are correct',2,3,302218136),(4,'when does a basketball game end?','when the 4th quarter is over','when the ref feels liek it','when Bar Refaeli start dancing in the rain','42',1,2,302218136),(4,'nivs first questio	','na','v','b','c',2,3,204360317),(5,'how much wood can a wood chuck chuck if a wood chuck could chuck wood?','42','depending on the tyope of wood','maybe 98','0',2,2,302218136),(5,'niv is niv??','yes','no','yeeeees','no!!!',3,3,204360317),(6,'can you proove La Grange sentence?','Sure','you wish','Google can...','nop',3,2,302218136),(6,'we get 100 in the project??','yes','no','i dont think so','100%',4,3,204360317),(7,'How much is 2 + 2','1','4','5','i dont know...',2,2,302218136),(7,'niv is?','perfect','god','king','All the answers are correct',4,3,204360317);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_in_course`
--

DROP TABLE IF EXISTS `questions_in_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions_in_course` (
  `questionid` int(3) NOT NULL,
  `fieldid` int(2) NOT NULL,
  `courseid` int(2) NOT NULL,
  PRIMARY KEY (`questionid`,`fieldid`,`courseid`),
  KEY `courseref_idx` (`courseid`,`fieldid`),
  CONSTRAINT `courseref` FOREIGN KEY (`courseid`, `fieldid`) REFERENCES `courses` (`courseid`, `fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `questionsref` FOREIGN KEY (`questionid`, `fieldid`) REFERENCES `questions` (`questionid`, `fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_in_course`
--

LOCK TABLES `questions_in_course` WRITE;
/*!40000 ALTER TABLE `questions_in_course` DISABLE KEYS */;
INSERT INTO `questions_in_course` VALUES (1,1,1),(2,1,1),(3,1,1),(2,2,1),(3,2,1),(4,2,1),(5,2,1),(6,2,1),(1,3,1),(2,3,1),(3,3,1),(4,3,1),(5,3,1),(6,3,1),(7,3,1),(2,1,2),(3,1,2),(2,2,2),(3,2,2),(4,2,2),(5,2,2),(6,2,2),(2,3,2),(4,3,2);
/*!40000 ALTER TABLE `questions_in_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_in_exam`
--

DROP TABLE IF EXISTS `questions_in_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions_in_exam` (
  `questionid` int(3) NOT NULL,
  `examid` int(2) NOT NULL,
  `pointsvalue` int(11) NOT NULL,
  `courseid` int(2) NOT NULL,
  `fieldid` int(2) NOT NULL,
  `innernote` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `studentnote` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `qieexamid_idx` (`examid`,`courseid`,`fieldid`),
  KEY `questionid_idx` (`questionid`,`fieldid`),
  CONSTRAINT `qieexamid` FOREIGN KEY (`examid`, `courseid`, `fieldid`) REFERENCES `exams` (`examid`, `courseid`, `fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `questionid` FOREIGN KEY (`questionid`, `fieldid`) REFERENCES `questions` (`questionid`, `fieldid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_in_exam`
--

LOCK TABLES `questions_in_exam` WRITE;
/*!40000 ALTER TABLE `questions_in_exam` DISABLE KEYS */;
INSERT INTO `questions_in_exam` VALUES (1,1,15,1,3,'',''),(2,1,12,1,3,'',''),(7,1,20,1,3,'',''),(3,1,13,1,3,'',''),(4,1,25,1,3,'',''),(5,1,8,1,3,'',''),(6,1,7,1,3,'',''),(2,1,40,2,3,'','This is an NP Question. pay attention'),(4,1,60,2,3,'retorical question (teachers note)','Becasue this is niv\'s first question he had a mispele'),(3,1,50,1,1,'',''),(2,1,20,1,1,'',''),(1,1,30,1,1,'its actualy a big gate','a Not gate is a simple gate'),(3,1,20,2,1,'',''),(2,1,80,2,1,'',''),(5,1,20,1,2,'',''),(4,1,19,1,2,'',''),(3,1,1,1,2,'',''),(2,1,20,1,2,'gift question','you may use your calculator'),(6,1,40,1,2,'',''),(5,1,25,2,2,'',''),(4,1,30,2,2,'',''),(3,1,20,2,2,'',''),(2,1,15,2,2,'haa','this is mroe like a calculus 1 question! gifted to you!'),(6,1,10,2,2,'',''),(1,2,100,1,3,'',''),(1,3,25,1,3,'',''),(2,3,25,1,3,'',''),(3,3,50,1,3,'','');
/*!40000 ALTER TABLE `questions_in_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solved_exams`
--

DROP TABLE IF EXISTS `solved_exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solved_exams` (
  `examid` int(2) NOT NULL,
  `score` int(11) NOT NULL,
  `teacherapproved` tinyint(4) NOT NULL,
  `answers` varchar(1024) CHARACTER SET latin1 DEFAULT NULL,
  `studentid` int(11) NOT NULL,
  `courseid` int(2) NOT NULL,
  `fieldid` int(2) NOT NULL,
  `teacherschangescorenote` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `minutescompleted` int(11) DEFAULT NULL,
  `code` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `teacherquestionnote` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dateinitiated` datetime NOT NULL,
  PRIMARY KEY (`studentid`,`examid`,`courseid`,`fieldid`,`code`,`dateinitiated`),
  KEY `reportref1_idx` (`examid`,`courseid`,`fieldid`,`code`),
  KEY `reportref23_idx` (`examid`,`courseid`,`fieldid`,`code`),
  CONSTRAINT `ref_to_report123` FOREIGN KEY (`examid`, `courseid`, `fieldid`) REFERENCES `exams_report` (`examid`, `courseid`, `fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solved_exams`
--

LOCK TABLES `solved_exams` WRITE;
/*!40000 ALTER TABLE `solved_exams` DISABLE KEYS */;
INSERT INTO `solved_exams` VALUES (1,80,1,'q01003a2q01002a1q01001a3',1,1,1,'',1,'int8','','2018-06-23 00:00:00'),(1,55,1,'q03007a4q03006a1q03005a3q03004a1q03002a2q03003a3q03001a2',1,1,3,'',1,'abc1','','2018-06-23 00:00:00'),(1,100,1,'q01003a2q01002a4',1,2,1,'',0,'ele0','','2018-06-23 00:00:00'),(1,40,1,'q03002a2q03004a3',1,2,3,'',0,'ddd1','','2018-06-23 00:00:00'),(1,80,1,'q01003a2q01002a2q01001a3',2,1,1,'',2,'int8','','2018-06-23 00:00:00'),(1,62,1,'q03007a4q03006a4q03005a3q03004a3q03002a2q03003a1q03001a2',2,1,3,'',0,'abc1','','2018-06-23 00:00:00'),(1,20,1,'q01003a2q01002a2',2,2,1,'',2,'ele0','','2018-06-23 00:00:00'),(1,60,1,'q03002a2q03004a1',2,2,3,'factor',1,'ddd1','','2018-06-23 00:00:00'),(1,100,1,'q01003a2q01002a4q01001a3',3,1,1,'',4,'int8','','2018-06-23 00:00:00'),(1,42,1,'q03007a3q03006a4q03005a3q03004a3q03002a2q03003a4q03001a2',3,1,3,'',3,'abc1','','2018-06-23 00:00:00'),(1,20,1,'q01003a2q01002a2',3,2,1,'',0,'ele0','','2018-06-23 00:00:00'),(1,40,1,'q03002a2q03004a1',3,2,3,'',0,'ddd1','','2018-06-23 00:00:00'),(1,30,1,'q01003a1q01002a3q01001a3',4,1,1,'',3,'int8','','2018-06-23 00:00:00'),(1,67,1,'q03007a1q03006a4q03005a3q03004a2q03002a2q03003a3q03001a2',4,1,3,'',2,'abc1','','2018-06-23 00:00:00'),(1,80,1,'q01003a4q01002a4',4,2,1,'',2,'ele0','','2018-06-23 00:00:00'),(1,40,1,'q03002a2q03004a4',4,2,3,'',1,'ddd1','','2018-06-23 00:00:00'),(1,80,1,'q01003a2q01002a3q01001a3',5,1,1,'',2,'int8','','2018-06-23 00:00:00'),(1,59,1,'q03006a4q03005a1q03004a2q03002a2q03003a1q03001a2',5,1,3,'',2,'abc1','','2018-06-23 00:00:00'),(1,100,1,'q01003a2q01002a4',5,2,1,'',1,'ele0','','2018-06-23 00:00:00'),(1,80,1,'q03002a2q03004a3',5,2,3,'factor',1,'ddd1','<QID>03004<TEACHER-NOTE>hard question i agree','2018-06-23 00:00:00'),(1,80,1,'q01003a2q01002a1q01001a3',305112732,1,1,'',4,'int8','','2018-06-23 00:00:00'),(1,42,1,'q03007a3q03006a4q03005a3q03004a1q03002a2q03003a3q03001a2',305112732,1,3,'',2,'abc1','','2018-06-23 00:00:00'),(1,100,1,'q01003a2q01002a4',305112732,2,1,'',4,'ele0','','2018-06-23 00:00:00'),(1,80,1,'q03002a2q03004a1',305112732,2,3,'factor',0,'ddd1','<QID>03004<TEACHER-NOTE>hard question','2018-06-23 00:00:00');
/*!40000 ALTER TABLE `solved_exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_in_course`
--

DROP TABLE IF EXISTS `student_in_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_in_course` (
  `studentid` int(11) NOT NULL,
  `courseid` int(2) NOT NULL,
  `fieldid` int(2) NOT NULL,
  PRIMARY KEY (`studentid`,`courseid`,`fieldid`),
  KEY `studentcourseref_idx` (`courseid`,`fieldid`),
  CONSTRAINT `studentcourseref` FOREIGN KEY (`courseid`, `fieldid`) REFERENCES `courses` (`courseid`, `fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `studentidref` FOREIGN KEY (`studentid`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_in_course`
--

LOCK TABLES `student_in_course` WRITE;
/*!40000 ALTER TABLE `student_in_course` DISABLE KEYS */;
INSERT INTO `student_in_course` VALUES (1,1,1),(2,1,1),(3,1,1),(4,1,1),(5,1,1),(305112732,1,1),(1,1,2),(2,1,2),(3,1,2),(4,1,2),(5,1,2),(305112732,1,2),(1,1,3),(2,1,3),(3,1,3),(4,1,3),(5,1,3),(305112732,1,3),(1,2,1),(2,2,1),(3,2,1),(4,2,1),(5,2,1),(305112732,2,1),(1,2,2),(2,2,2),(3,2,2),(4,2,2),(5,2,2),(305112732,2,2),(1,2,3),(2,2,3),(3,2,3),(4,2,3),(5,2,3),(305112732,2,3);
/*!40000 ALTER TABLE `student_in_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_fields`
--

DROP TABLE IF EXISTS `teacher_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_fields` (
  `teacherid` int(11) NOT NULL,
  `fieldid` int(2) NOT NULL,
  PRIMARY KEY (`teacherid`,`fieldid`),
  KEY `fieldid_idx` (`fieldid`),
  CONSTRAINT `fieldid` FOREIGN KEY (`fieldid`) REFERENCES `fields` (`fieldid`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `teacheridrefid` FOREIGN KEY (`teacherid`) REFERENCES `users` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_fields`
--

LOCK TABLES `teacher_fields` WRITE;
/*!40000 ALTER TABLE `teacher_fields` DISABLE KEYS */;
INSERT INTO `teacher_fields` VALUES (302218136,1),(204360317,2),(302218136,2),(204360317,3),(302218136,3);
/*!40000 ALTER TABLE `teacher_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userid` int(11) NOT NULL,
  `username` varchar(45) CHARACTER SET latin1 NOT NULL,
  `password` varchar(45) CHARACTER SET latin1 NOT NULL,
  `fullname` varchar(90) CHARACTER SET latin1 NOT NULL,
  `usertype` int(1) NOT NULL,
  PRIMARY KEY (`userid`,`usertype`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'moshe','1','Moshe Cohen',0),(2,'maayan','1','Maayan Tibi',0),(3,'rivka','1','Rivka Shiff',0),(4,'sara','1','Sara Ruckin',0),(5,'chaya','1','Chaya Berkman',0),(204360317,'niv','mizrahi','Niv Mizrahi',1),(302218136,'daniel','tibi','Daniel Tibi',1),(305112732,'itzik','1234','Itzik Mizrahi',0),(307975052,'nathan','1234','Nathan Khutorskoy',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-24 10:41:26
