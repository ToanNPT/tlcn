-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: tlcn
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--
CREATE schema tlcn;
USE tlcn;

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `user_name` varchar(255) NOT NULL,
  `role_id` varchar(10) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('testaccount','USER','$2a$10$NQyOZVhaQXp7mca5fFxEKesYocLWgJZVdtg8xeXY4V6jy1eNauUDe'),('toanpt','ROLE_01','$2a$10$5ZU3Yz8NzvNFrpOYeYgHoeNs4JlwFZCIaj2kSHTTwzJ9FcJDQ5Zi2'),('toanpt01','TEACHER','$2a$10$By.nUJ/D4rOcMnHue5cUJekUaG7Y97nG5cZMipB4uZVBEp.2OTkOK'),('toanpt02','ROLE_01','$2a$10$lJHUskaB90EnMNebbgidbOTNGekLRCD3hhUXNLA2SeKBoKACbpwdC');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `user_name` varchar(255) NOT NULL,
  `total_price` double DEFAULT NULL,
  `payment_price` double DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  CONSTRAINT `FKodsj0lur4auo8024jfecrxwn4` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES ('testaccount',0,0),('toanpt',10.01,10.01),('toanpt01',490000,490000),('toanpt02',0,0);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartdetail`
--

DROP TABLE IF EXISTS `cartdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartdetail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `course_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `cartdetail_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `cart` (`user_name`),
  CONSTRAINT `cartdetail_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartdetail`
--

LOCK TABLES `cartdetail` WRITE;
/*!40000 ALTER TABLE `cartdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('C01','Web Developer',_binary ''),('C02','Business',_binary ''),('C03','Primary Student',_binary ''),('C04','Language',_binary ''),('C05','Instructment',_binary '\0');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapters`
--

DROP TABLE IF EXISTS `chapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chapter_name` varchar(255) DEFAULT NULL,
  `head_chapter` bit(1) DEFAULT NULL,
  `next_chapter_id` int DEFAULT NULL,
  `num_videos` int DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6h1m0nrtdwj37570c0sp2tdcs` (`course_id`),
  CONSTRAINT `FK6h1m0nrtdwj37570c0sp2tdcs` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapters`
--

LOCK TABLES `chapters` WRITE;
/*!40000 ALTER TABLE `chapters` DISABLE KEYS */;
INSERT INTO `chapters` VALUES (2,'Software Setup',_binary '\0',4,2,'C_01'),(3,'Spring With Micro service',_binary '\0',14,11,'C_01'),(4,'Micro service concept',_binary '\0',11,11,'C_01'),(11,'What is Spring WebFlux',_binary '\0',3,10,'C_01'),(12,'Overview our course',_binary '',13,0,'C_01'),(13,'Introduction IDE and Tools',_binary '\0',2,0,'C_01'),(14,'Thanks',_binary '\0',-1,0,'C_01');
/*!40000 ALTER TABLE `chapters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `expired_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `user_created` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `num` int DEFAULT '0',
  `number_of_remain` int DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`code`),
  KEY `user_created` (`user_created`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`user_created`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('Discount 5% for web course','%','2022-12-12','2022-10-10','2022-08-10','2022-10-08','toanpt','Allow discount when buy web development course on onlinecourse.com',1,'BOO_90_SALE_CL_11',1000,90,1),('Big sale electronic 8-8','%','2022-10-08','2022-08-08','2022-08-01','2022-08-05','toanpt','A sale for electric booth',20,'SALE_882022_EL',100,10,1),('Sale 50% for clothes',NULL,'2022-10-10','2022-10-09','2022-10-01','2022-10-02','toanpt','A sale',50,'SALE_CO_10_22',0,0,1);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` varchar(255) NOT NULL,
  `category_id` varchar(10) NOT NULL,
  `language` varchar(50) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text,
  `user_created` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `is_public` tinyint(1) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `num_students` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_created` (`user_created`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`user_created`) REFERENCES `account` (`user_name`),
  CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('C_01','C01','Vietnamese','Learn Spring Boot ','This lectures for beginners. We will guide you how to build web application with spring boot','toanpt',590000,0,'2022-01-14','2022-02-20',122,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('C_02','C02','Vietnamese','Learn Python','This lectures for beginners. We will guide you how to build web application with spring boot','toanpt',699000,0,'2022-01-13','2022-10-10',193,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU010','C01','Vietnamese','Learn JavaScript ES6 and TypeScript ','JS for beginner to master','toanpt',0,1,'2022-01-09','2022-11-22',1903,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU011','C02','English','Learn Ruby on rail basic for beginner','We will provide you how to build web application on ruby on rails. You also learn about Linux and How does it work? Join now','toanpt',2110000,0,'2022-08-30','2022-10-10',28,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU012','C01','English','Event Driven in Micro service with Spring boot','course for beginner to learn how to design BE with event drivent','toanpt',1599000,0,NULL,NULL,2,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU013','C01','English','Event Driven in Micro service with Spring boot','course for beginner to learn how to design BE with event drivent','toanpt',270000,0,NULL,NULL,5,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU014','C01','English','Event Driven in Micro service with Spring boot','course for beginner to learn how to design BE with event drivent','toanpt',99000,0,NULL,NULL,0,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU015','C01','English','Event Driven in Micro service with Spring boot','course for beginner to learn how to design BE with event drivent','toanpt',1190000,0,NULL,NULL,1,1,'https://avatar-course.s3.us-west-2.amazonaws.com/toanpt-Event%20Driven%20in%20Micro%20service%20with%20Spring%20boot'),('COU03','C01','English','Japanese N5, zero to Hero ','Wait, The best course to learn Japanese for you. You dont know anything about Japanese, dont worry. This course will help you so much. After complete, you can communicate confident','toanpt',899000,0,'2022-08-01','2022-08-08',222,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU04','C01','Vietnames','Tieng Han danh cho nguoi moi','Ban thich nghe nhac Han, di du lich Han Quoc hay di cong tac tai day. Nhưng lại không biết tiếng Hàn? Đừng lo, Mọi việc sẽ được giải quyết sau khi hoàn thành khóa học này. Join ngay !!','toanpt',978000,0,'2022-01-30','2022-07-07',2010,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU05','C01','English','Class 11: Math subject foundation','Learn about math 11. It\'s so easy','toanpt',2350000,0,'2022-01-21','2022-10-30',46,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU06','C01','English','Class 12: Chemistry tutorial','Learn how to master chemistry','toanpt',789000,0,'2022-02-01','2022-09-03',3,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU07','C01','Chinese','Magic of JavaScript an TS','Design for middle, you must knowledge basic JS','toanpt',190000,0,'2022-06-01','2022-06-30',106,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU08','C01','India','Improve sales performance','How to improve your profit compay','toanpt',0,1,'2022-03-30','2022-06-01',67,1,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20'),('COU09','C02','Japanese','How to marketing online','design pattern for beginner','toanpt',999000,0,'2022-01-01','2022-01-20',0,0,'https://avatar-course.s3.us-west-2.amazonaws.com/Learn%20JavaScript%20ES6%20and%20TypeScript%20_COU010Learn%20JavaScript%20ES6%20and%20TypeScript%20');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_paid`
--

DROP TABLE IF EXISTS `courses_paid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_paid` (
  `id` int NOT NULL AUTO_INCREMENT,
  `buy_date` date DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKguc67un11oblcfj9bhpg5u6ws` (`username`),
  KEY `FKfdix6nht1m2u5wbbu904nt081` (`course_id`),
  CONSTRAINT `FKfdix6nht1m2u5wbbu904nt081` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKguc67un11oblcfj9bhpg5u6ws` FOREIGN KEY (`username`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_paid`
--

LOCK TABLES `courses_paid` WRITE;
/*!40000 ALTER TABLE `courses_paid` DISABLE KEYS */;
INSERT INTO `courses_paid` VALUES (32,'2022-12-14','COU07','toanpt01'),(33,'2022-12-14','COU013','toanpt01'),(34,'2022-12-20','C_01','toanpt01');
/*!40000 ALTER TABLE `courses_paid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursesvideo`
--

DROP TABLE IF EXISTS `coursesvideo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coursesvideo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` varchar(255) DEFAULT NULL,
  `link` text,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `next_video_id` int DEFAULT NULL,
  `chapter_id` int DEFAULT NULL,
  `is_head_video` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  KEY `FK777ictcscret8incxewcqsax2` (`chapter_id`),
  CONSTRAINT `coursesvideo_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FK777ictcscret8incxewcqsax2` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursesvideo`
--

LOCK TABLES `coursesvideo` WRITE;
/*!40000 ALTER TABLE `coursesvideo` DISABLE KEYS */;
INSERT INTO `coursesvideo` VALUES (1,'C_01','https://www.youtube.com/embed/rJrSXYrHn5g?list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','How to learn Spring Boot fast and deep?Alo',1,'2022-09-09','2022-11-27','11Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',4,2,_binary '\0'),(2,'C_01','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','Learn about spring template',1,'2022-09-10',NULL,'you will learn spring framework',5,2,_binary '\0'),(3,'C_01','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','JSP template',1,'2022-09-11',NULL,'you will learn spring framework',2,2,_binary ''),(4,'C_01','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','Thymeleaf',1,'2022-09-12',NULL,'you will learn spring framework',10,2,_binary '\0'),(5,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt-C_01-Introduce Spring boot-How to learn Spring Boot fast and deep?','How to learn Spring Boot fast and deep?',1,'2022-10-22',NULL,'Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',1,2,_binary '\0'),(6,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt-C_01-Introduce%20Spring%20boot-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F','How to learn Spring Boot fast and deep?',1,'2022-10-23',NULL,'Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',-1,3,_binary ''),(7,'C_02','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt-C_02-Introduce%20Spring%20boot-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F','How to learn Spring Boot fast and deep?',1,'2022-10-25',NULL,'Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',-1,4,_binary ''),(9,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt01-C_01-2-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F11','How to learn Spring Boot fast and deep?Alo',0,'2022-11-26','2022-11-27','11Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',4,2,_binary '\0'),(10,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt01-C_01-2-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F11ss','How to learn Spring Boot fast and deep?Alo',1,'2022-11-26','2022-11-26','11Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',-1,2,_binary '\0'),(11,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt01-C_01-11-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F11ss','How to learn Spring Boot fast and deep?11ss',1,'2022-12-04',NULL,'Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',-1,11,_binary ''),(12,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt01-C_01-11-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F11ss','How to learn Spring Boot fast and deep?11ss',1,'2022-12-27',NULL,'Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',13,11,_binary '\0'),(13,'C_01','https://toannpt-onlinecourses.s3.amazonaws.com/toanpt01-C_01-11-Testoot%20fast%20and%20deep%3F11ss','Testoot fast and deep?11ss',1,'2022-12-27',NULL,'Hey, Go back bro. In this video, I will explain how do you learn new framework, Spring boot so fast and deep. And What are the magics of Spring.',2,11,_binary '\0');
/*!40000 ALTER TABLE `coursesvideo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (125);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `at_time` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `video_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (1,'1:10','can get Intellij Ultimate IDE by school mail','toanpt01',6),(2,'1:10','can get Intellij Ultimate IDE by school mail','toanpt01',6),(3,'1:10','can get Intellij Ultimate IDE by school mail','toanpt01',6),(4,'1:10','can get Intellij Ultimate IDE by school mail','toanpt01',6);
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` varchar(10) NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `course_id` varchar(10) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  KEY `FK2tw1bpwv3mg8115uo0na76gff` (`user_name`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  CONSTRAINT `FK2tw1bpwv3mg8115uo0na76gff` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES ('108','36086495','COU07',190000,'toanpt01'),('109','36086495','COU013',270000,'toanpt01'),('110','64552910','COU07',190000,'toanpt01'),('111','64552910','COU013',270000,'toanpt01'),('112','99078688','COU07',190000,'toanpt01'),('113','99078688','COU013',270000,'toanpt01'),('114','98195131','COU07',190000,'toanpt01'),('115','98195131','COU013',270000,'toanpt01'),('116','65329679','COU07',190000,'toanpt01'),('117','65329679','COU013',270000,'toanpt01'),('118','45783248','C_01',590000,'toanpt01'),('119','68127410','C_01',590000,'toanpt01'),('120','17723938','C_01',590000,'toanpt01'),('121','58598133','C_01',590000,'toanpt01'),('122','61932543','C_01',590000,'toanpt01'),('123','34663446','C_01',590000,'toanpt01'),('124','04202529','C_01',590000,'toanpt01');
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `payment_id` varchar(10) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `payment_price` double DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  KEY `payment_id` (`payment_id`),
  KEY `orders_coupon_code_fk` (`coupon_code`),
  CONSTRAINT `orders_coupon_code_fk` FOREIGN KEY (`coupon_code`) REFERENCES `coupon` (`code`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('04202529','toanpt01','01',590000,590000,1,'2022-12-20',1,NULL),('17723938','toanpt01','01',590000,590000,1,'2022-12-20',0,NULL),('34663446','toanpt01','01',590000,590000,1,'2022-12-20',0,NULL),('36086495','toanpt01','01',460000,460000,2,'2022-12-14',1,NULL),('45783248','toanpt01','01',590000,590000,1,'2022-12-20',0,NULL),('58598133','toanpt01','01',590000,590000,1,'2022-12-20',0,NULL),('61932543','toanpt01','01',590000,590000,1,'2022-12-20',0,NULL),('64552910','toanpt01','01',460000,460000,2,'2022-12-14',1,NULL),('65329679','toanpt01','01',460000,460000,2,'2022-12-14',1,NULL),('68127410','toanpt01','01',590000,590000,1,'2022-12-20',0,NULL),('98195131','toanpt01','01',460000,460000,2,'2022-12-14',0,NULL),('99078688','toanpt01','01',460000,460000,2,'2022-12-02',1,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('01','PAYPAL',_binary ''),('02','VNPAY',_binary '');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register_teacher_form`
--

DROP TABLE IF EXISTS `register_teacher_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `register_teacher_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `current_job` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `exp_describe` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `submit_time` varchar(255) DEFAULT NULL,
  `teaching_topic` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd2t0wj60x5evw85osg15me798` (`username`),
  CONSTRAINT `FKd2t0wj60x5evw85osg15me798` FOREIGN KEY (`username`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register_teacher_form`
--

LOCK TABLES `register_teacher_form` WRITE;
/*!40000 ALTER TABLE `register_teacher_form` DISABLE KEYS */;
INSERT INTO `register_teacher_form` VALUES (1,'59/25 Nguyen Van Rop, Trang Bang, Tay Ninh','Giao vien tai THPT Nguyen Trai',NULL,'3 nam lam viec tai THPT Nguyen Trai','0376690904','2022-12-24 03:01:04','Giang day bo mon Hoa hoc khoi THPT va THCS, luyen thi dai hoc','toanpt01','OPENING'),(2,'59/25 Nguyen Van Rop, Trang Bang, Tay Ninh','Giao vien tai THPT Nguyen Trai','toannpt0810@gmail.com','3 nam lam viec tai THPT Nguyen Trai','0376690904','2022-12-24 03:04:41','Giang day bo mon Hoa hoc khoi THPT va THCS, luyen thi dai hoc','toanpt01','REJECTED'),(3,'59/25 Nguyen Van Rop, Trang Bang, Tay Ninh','Giao vien tai THPT Nguyen Trai','toannpt0810@gmail.com','3 nam lam viec tai THPT Nguyen Trai','0376690904','2022-12-24 03:53:16','Giang day bo mon Hoa hoc khoi THPT va THCS, luyen thi dai hoc','toanpt01','ACCEPTED');
/*!40000 ALTER TABLE `register_teacher_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcescourse`
--

DROP TABLE IF EXISTS `resourcescourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resourcescourse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` varchar(10) DEFAULT NULL,
  `title` text,
  `link` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `resourcescourse_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcescourse`
--

LOCK TABLES `resourcescourse` WRITE;
/*!40000 ALTER TABLE `resourcescourse` DISABLE KEYS */;
INSERT INTO `resourcescourse` VALUES (11,'C_01','Get start Srping Framework','https://www.youtube.com/embed/rJrSXYrHn5g?list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),(12,'C_01','Setting up Spring boot in Intellij','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),(14,'C_01','Lombok Dependency','https://www.youtube.com/watch?v=8tNgxtvROW0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),(15,'C_01','Template engine in Spring boot','https://www.youtube.com/watch?v=G7U2JVwGqPw&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),(17,'C_01','Spring MVC ','https://www.youtube.com/watch?v=po5J1QRJQwk&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),(19,'C_01','Spring Hibernate and JPA','https://www.youtube.com/watch?v=qFRwv3BJNpI&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),(20,'C_01','Source code for Spring Hello World','https://resource-course.s3.us-west-2.amazonaws.com/toanpt-Learn%20Spring%20Boot%20-Source%20code%20for%20Spring%20Hello%20World');
/*!40000 ALTER TABLE `resourcescourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` varchar(255) DEFAULT NULL,
  `content` text,
  `rate` double DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  KEY `FKscwflnvoytyyrn1u5e3a4neda` (`user_name`),
  CONSTRAINT `FKscwflnvoytyyrn1u5e3a4neda` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'C_01','So nice for beginner as me. Thanks so much',4.5,'2022-10-10',NULL,'toanpt01'),(2,'C_01','So bad, dont buy this',2,'2022-09-30',NULL,'toanpt01'),(3,'C_02','Look good',3,'2022-10-01','2022-10-02','toanpt01'),(4,'C_02','It\'s normal, dont by if you have basic foundation',3.5,'2022-08-30',NULL,'toanpt01'),(5,'COU03','So great, I dont believe that I can learn so much ',5,'2022-09-09',NULL,'toanpt01'),(6,'COU04','Hello, I\'m new and this ok with me, so easy for understand',4,'2022-10-02',NULL,'toanpt01'),(7,'COU05','I got 10 points in class :)) great',5,'2022-09-04',NULL,'toanpt01'),(8,'COU06','should have more example',3.5,'2022-08-01',NULL,'toanpt01'),(9,'COU07','you public this course for only earn money, are you? BADDD',1,'2022-07-20',NULL,'toanpt01'),(10,'COU08','Nice pro, I will buy your course for next times',5,'2022-09-30',NULL,'toanpt01');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('ROLE_01','ADMIN'),('TEACHER','TEACHER'),('USER','USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` int NOT NULL,
  `exp` datetime(6) DEFAULT NULL,
  `iat` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnhax84p1leyyl4ldgpa0l4yyn` (`username`),
  CONSTRAINT `FKnhax84p1leyyl4ldgpa0l4yyn` FOREIGN KEY (`username`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdetail`
--

DROP TABLE IF EXISTS `userdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userdetail` (
  `user_name` varchar(255) NOT NULL,
  `fullname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  CONSTRAINT `userdetail_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdetail`
--

LOCK TABLES `userdetail` WRITE;
/*!40000 ALTER TABLE `userdetail` DISABLE KEYS */;
INSERT INTO `userdetail` VALUES ('testaccount','Toan NPT','2002-10-10','nam','toan@gmail.com','0933469781','https://users-avatars-online-courses.s3.us-west-2.amazonaws.com/man-1.png'),('toanpt','NguyenPhucThanhToan','2001-10-30','nam','to1a1an@gmail.com','0111021904',NULL),('toanpt01','La Thi Yen Nhu','2001-09-30','nam','toannpt0810@gmail.com','0371026904','https://users-avatars-online-courses.s3.amazonaws.com/avatar-toanpt01Thu%20Nov%2024%2022%3A25%3A34%20ICT%202022'),('toanpt02','La Thi Yen','2001-10-30','nam','w@19i29m12w1ail.com','0173826904',NULL);
/*!40000 ALTER TABLE `userdetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-28 19:21:54
