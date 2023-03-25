-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: oumarketdb
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (5,'Vo Van Tan','97 Vo Van Tan, district 3, Ho Chi Minh city'),(6,'Nguyen Kiem','371 Nguyen Kiem, Go Vap district, Ho Chi Minh city'),(12,'Tan Ky Tan Quy','198 Tan Ky Tan Quy, Binh Tan district, Ho Chi Minh city'),(20,'Vuon Lai ','14 Vuon Lai, Tan Phu district, Ho Chi Minh city'),(23,'Tran Quoc Tuan','35 Tran Quoc Tuan, Go Vap district, Ho Chi Minh city'),(24,'Le Quang TUan','182 Le Quang Dinh, Go Vap district, Ho Chi Minh city'),(28,'Nguyen Van Dau','1 Nguyen Van Dau');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birhdate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `branch_id` int NOT NULL,
  PRIMARY KEY (`id`,`branch_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `branch_employee` (`branch_id`),
  CONSTRAINT `branch_employee` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Truong Thanh Dat','0123456789','thdat','996009f2374006606f4c0b0fda878af1',5),(2,'Le Minh Duc','0123457893','minhduc','250cf8b51c773f3f8dc8b4be867a9a02',6),(3,'Tran Quoc Hy','0123124124','qhy','202cb962ac59075b964b07152d234b70',5);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `totalPrice` float DEFAULT NULL,
  `moneyCus` float DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`,`employee_id`),
  KEY `order_emp_idx` (`employee_id`),
  KEY `order_cus_idx` (`customer_id`),
  CONSTRAINT `order_cus` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `order_emp` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2023-03-25',250,500,NULL,1),(2,'2023-03-26',260,300,NULL,2),(3,'2020-03-02',20,30,NULL,3),(4,'2020-02-02',2,3,NULL,1),(5,'2023-03-23',12,22,NULL,1),(6,'2023-03-23',1487,2000,NULL,1),(7,'2023-03-01',45.6,50,NULL,3),(8,'2023-03-22',15.1,17,NULL,1),(9,'2023-03-01',13,14,NULL,1),(10,'2023-02-28',4.1,5,NULL,1),(11,'2023-03-16',17.45,20,NULL,1),(12,'2023-03-25',392.2,400,NULL,3),(13,'2023-04-08',243.6,2000,NULL,1),(14,'2023-03-25',2000,20000,NULL,1),(15,'2023-04-01',122,444,NULL,1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` float DEFAULT NULL,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`,`product_id`,`order_id`),
  KEY `order_product_idx` (`product_id`),
  KEY `orderDetail_order_idx` (`order_id`),
  CONSTRAINT `orderDetail_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `orderDetail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,11,15),(2,1,11,28),(3,1,11,23),(4,1,11,24),(5,100,12,22),(6,12,12,23),(7,1,13,16),(8,4,13,17),(9,1,13,5),(10,155,13,18),(11,1,14,32),(12,122,15,33);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `origin` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `discountPrice` float DEFAULT NULL,
  `active` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Red Carrot','Vietnam',5.7,NULL,1),(2,'Eggplant','Vietnam',8,NULL,1),(3,'Banana','Malaysia',4.3,NULL,1),(4,'Tomato','Vietnam',6.6,NULL,1),(5,'Green Lemon','Vietnam',12,NULL,1),(15,'Pocari Sweat Drink 500ml','Japan',1.7,NULL,1),(16,'Dasani Drinking Water 600ml','Vietnam',1,NULL,1),(17,'Cheers Original Beer Can 330ml','Thailand',3.4,NULL,1),(18,'Fanta Orange 320ml','Vietnam',1.4,NULL,1),(19,'Monster Energy Zero Ultra 355ml','Netherland',3.4,NULL,1),(20,'MISTER POTATO BBQ 75G','Malaysia',2,NULL,1),(21,'Ding Dong Hot & Spicy (Red)100g','Philippines ',1.8,NULL,1),(22,'MISTER POTATO -ORIGINAL-130GM','Malaysia',3.1,NULL,1),(23,'Lays Cheddar & Sour Cream 170g','United States',6.85,NULL,1),(24,'Twisties Chipster Potato Chips - Sour Cream & Onion 130g','Malaysia',4.9,NULL,1),(28,'a','a',4,12,1),(31,'a','bc',123,1,0),(32,'b','bbb',2000,500,1),(33,'c','ccc',13,1,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-25 22:27:50
