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
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('toanpt','ROLE_01','$2a$10$5ZU3Yz8NzvNFrpOYeYgHoeNs4JlwFZCIaj2kSHTTwzJ9FcJDQ5Zi2'),('toanpt01','USER','$2a$10$/4YtGiVHuM/PG2XRK8ymk.MS0W8vN2g6ONNqmbt3GZ7gDrj4609Xq');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cartdetail`
--

LOCK TABLES `cartdetail` WRITE;
/*!40000 ALTER TABLE `cartdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('C01','Web Developer'),('C02','Business'),('C03','Primary Student'),('C04','Language');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('1','SALE-OFF-8-8','%','2022-08-10','2022-08-08','2022-08-07',NULL,'toanpt','apply code to get discount in 8-8-2022',10),('2','NEW- CUSTOMER','$','2022-12-30','2022-01-01','2022-01-01',NULL,'toanpt','new customer ',20);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('C_01','C01','Vietnamese','Learn Spring Boot ','This lectures for beginners. We will guide you how to build web application with spring boot','toanpt',89.99,0,'2022-01-14','2022-02-20',100,1),('C_02','C02','Vietnamese','Learn Python','This lectures for beginners. We will guide you how to build web application with spring boot','toanpt',90,0,'2022-01-13','2022-10-10',190,1),('COU010','C01','English','Learn JS to master','JS for beginner to master','toanpt',190.9,0,'2022-01-09','2022-09-09',1900,1),('COU011','C02','English','Learn Ruby on rail basic for beginner','We will provide you how to build web application on ruby on rails. You also learn about Linux and How does it work? Join now','toanpt',90,0,'2022-08-30','2022-10-10',19,1),('COU03','C01','English','Japanese N5, zero to Hero ','Wait, The best course to learn Japanese for you. You dont know anything about Japanese, dont worry. This course will help you so much. After complete, you can communicate confident','toanpt',111,0,'2022-08-01','2022-08-08',222,1),('COU04','C01','Vietnames','Tieng Han danh cho nguoi moi','Ban thich nghe nhac Han, di du lich Han Quoc hay di cong tac tai day. Nhưng lại không biết tiếng Hàn? Đừng lo, Mọi việc sẽ được giải quyết sau khi hoàn thành khóa học này. Join ngay !!','toanpt',110,0,'2022-01-30','2022-07-07',2009,1),('COU05','C01','English','Class 11: Math subject foundation','Learn about math 11. It\'s so easy','toanpt',99.99,0,'2022-01-21','2022-10-30',45,1),('COU06','C01','English','Class 12: Chemistry tutorial','Learn how to master chemistry','toanpt',55.55,0,'2022-02-01','2022-09-03',2,1),('COU07','C01','Chinese','Magic of JavaScript an TS','Design for middle, you must knowledge basic JS','toanpt',10.08,0,'2022-06-01','2022-06-30',100,1),('COU08','C01','India','Improve sales performance','How to improve your profit compay','toanpt',22.89,1,'2022-03-30','2022-06-01',67,1),('COU09','C02','Japanese','How to marketing online','design pattern for beginner','toanpt',67.9,0,'2022-01-01','2022-01-20',0,1);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `courses_paid`
--

LOCK TABLES `courses_paid` WRITE;
/*!40000 ALTER TABLE `courses_paid` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses_paid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `coursesvideo`
--

LOCK TABLES `coursesvideo` WRITE;
/*!40000 ALTER TABLE `coursesvideo` DISABLE KEYS */;
INSERT INTO `coursesvideo` VALUES ('01','Introduce Spring Framework','C_01','https://www.youtube.com/embed/rJrSXYrHn5g?list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','What is Spring framework',1,'2022-09-09',NULL,'you will learn spring framework'),('02','Spring Template Engine','C_01','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','Learn about spring template',1,'2022-09-10',NULL,'you will learn spring framework'),('03','Spring Template Engine','C_01','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','JSP template',1,'2022-09-11',NULL,'you will learn spring framework'),('04','Spring Template Engine','C_01','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV','Thymeleaf',1,'2022-09-12',NULL,'you will learn spring framework');
/*!40000 ALTER TABLE `coursesvideo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES ('01','OR_01','C_01',100,'toanpt');
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('OR_01','toanpt01','01',100,100,NULL,1,'2022-12-12',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('01','PAYPAL'),('02','VISA GLOBAL'),('03','ZALO PAY');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `resourcescourse`
--

LOCK TABLES `resourcescourse` WRITE;
/*!40000 ALTER TABLE `resourcescourse` DISABLE KEYS */;
INSERT INTO `resourcescourse` VALUES ('01','C_01','Introduce Spring framework','https://www.youtube.com/embed/rJrSXYrHn5g?list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),('02','C_01','Setting up Spring boot in Intellij','https://www.youtube.com/watch?v=xtNQbzbBsy0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),('03','C_01','Lombok Dependency','https://www.youtube.com/watch?v=8tNgxtvROW0&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),('04','C_01','Template engine in Spring boot','https://www.youtube.com/watch?v=G7U2JVwGqPw&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),('05','C_01','Spring MVC ','https://www.youtube.com/watch?v=po5J1QRJQwk&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV'),('06','C_01','Spring Hibernate and JPA','https://www.youtube.com/watch?v=qFRwv3BJNpI&list=PLn9lhDYvf_3HS5RvfAfH4g1XHUN1gapFV');
/*!40000 ALTER TABLE `resourcescourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES ('1','C_01','So nice for beginner as me. Thanks so much',4.5,'2022-10-10',NULL,'toanpt01'),('10','COU08','Nice pro, I will buy your course for next times',5,'2022-09-30',NULL,'toanpt01'),('2','C_01','So bad, dont buy this',2,'2022-09-30',NULL,'toanpt01'),('3','C_02','Look good',3,'2022-10-01','2022-10-02','toanpt01'),('4','C_02','It\'s normal, dont by if you have basic foundation',3.5,'2022-08-30',NULL,'toanpt01'),('5','COU03','So great, I dont believe that I can learn so much ',5,'2022-09-09',NULL,'toanpt01'),('6','COU04','Hello, I\'m new and this ok with me, so easy for understand',4,'2022-10-02',NULL,'toanpt01'),('7','COU05','I got 10 points in class :)) great',5,'2022-09-04',NULL,'toanpt01'),('8','COU06','should have more example',3.5,'2022-08-01',NULL,'toanpt01'),('9','COU07','you public this course for only earn money, are you? BADDD',1,'2022-07-20',NULL,'toanpt01');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('ROLE_01','ADMIN'),('USER','USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `userdetail`
--

LOCK TABLES `userdetail` WRITE;
/*!40000 ALTER TABLE `userdetail` DISABLE KEYS */;
INSERT INTO `userdetail` VALUES ('toanpt','NguyenPhucThanhToan','2001-10-30','nam','to1a1an@gmail.com','0111021904'),('toanpt01','NguyenPhucThanhToan','2001-10-30','nam','user@gmail.com','0111022904');
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

-- Dump completed on 2022-10-03 16:44:39
