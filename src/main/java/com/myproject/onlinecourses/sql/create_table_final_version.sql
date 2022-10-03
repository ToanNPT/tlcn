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
-- Table structure for table `cartdetail`
--

DROP TABLE IF EXISTS `cartdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartdetail` (
  `id` varchar(10) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `course_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `cartdetail_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `cart` (`user_name`),
  CONSTRAINT `cartdetail_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `expired_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `user_created` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_created` (`user_created`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`user_created`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  PRIMARY KEY (`id`),
  KEY `user_created` (`user_created`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`user_created`) REFERENCES `account` (`user_name`),
  CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `courses_paid`
--

DROP TABLE IF EXISTS `courses_paid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_paid` (
  `id` varchar(10) DEFAULT NULL,
  `user_name` varchar(10) DEFAULT NULL,
  `courses_id` varchar(10) DEFAULT NULL,
  `buy_date` date DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  KEY `courses_id` (`courses_id`),
  KEY `user_name` (`user_name`),
  CONSTRAINT `courses_paid_ibfk_1` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `courses_paid_ibfk_2` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coursesvideo`
--

DROP TABLE IF EXISTS `coursesvideo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coursesvideo` (
  `id` varchar(10) NOT NULL,
  `content_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `link` text,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `coursesvideo_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` varchar(10) NOT NULL,
  `order_id` varchar(10) DEFAULT NULL,
  `course_id` varchar(10) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `course_id` (`course_id`),
  KEY `FK2tw1bpwv3mg8115uo0na76gff` (`user_name`),
  CONSTRAINT `FK2tw1bpwv3mg8115uo0na76gff` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` varchar(10) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `payment_id` varchar(10) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `payment_price` double DEFAULT NULL,
  `coupon_id` varchar(10) DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  KEY `payment_id` (`payment_id`),
  KEY `FKa5ei0aklq6wrjl8vrr7ied3bx` (`coupon_id`),
  CONSTRAINT `FKa5ei0aklq6wrjl8vrr7ied3bx` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resourcescourse`
--

DROP TABLE IF EXISTS `resourcescourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resourcescourse` (
  `id` varchar(10) NOT NULL,
  `course_id` varchar(10) DEFAULT NULL,
  `title` text,
  `link` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `resourcescourse_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` varchar(10) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  PRIMARY KEY (`user_name`),
  CONSTRAINT `userdetail_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `account` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-03 16:44:02
