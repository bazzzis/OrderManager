CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.13.10.1

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
-- Table structure for table `OrderDetails`
--

DROP TABLE IF EXISTS `OrderDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderDetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `PckagingType` varchar(45) DEFAULT NULL,
  `Descritption` varchar(75) DEFAULT NULL,
  `Weight` double(6,2) DEFAULT NULL,
  `OrderId` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderDetails`
--

LOCK TABLES `OrderDetails` WRITE;
/*!40000 ALTER TABLE `OrderDetails` DISABLE KEYS */;
INSERT INTO `OrderDetails` VALUES (1,'Pallet','fish bugs',345.45,1),(2,'Carton','Wines',400.00,1),(4,'324343','45',345.00,0),(5,'Cartons','Wines',454.00,2),(6,'99','Value product',665.88,2),(9,'Bags','construction wood',555.00,3),(10,'korobka','puf',1999.99,11),(11,'bidon','lapte',1999.99,10),(12,'yashik','mere',1599.60,13),(13,'butoi','vine',1900.00,17),(14,'octabins','lina',1788.00,18);
/*!40000 ALTER TABLE `OrderDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(45) NOT NULL,
  `StreetName` varchar(45) NOT NULL,
  `City` varchar(18) NOT NULL,
  `ZIPCode` int(11) DEFAULT NULL,
  `Country` varchar(45) NOT NULL,
  `VAT number` varchar(15) DEFAULT NULL,
  `block` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='trying to creat an address database';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (0,'Valcauti','Cozminului 10','Ocnita',7002,'Carolina de Sud','20443u2t38',0),(1,'Gara Auto Nord','Valea Crucii 8','Chisinau',2072,'MD || Moldova (Republic of)','',0),(2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',0),(3,'er','er','er',34,'NL || Netherlands','34345',1),(4,'Vinarie Bostavan','Calea iesilor 43/6','Chisinau',2000,'ZW || Zimbabwe','BE898634652',0),(24,'Posta','Independetei 32/4','Chisinau',2071,'MD || Moldova (Republic of)','',0),(25,'Valcauti','Cozminului 10','Ocnita',7002,'ZW || Zimbabwe','20443u2t38',0);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `pickup_relation` int(11) DEFAULT NULL,
  `pickup_CompanyName` varchar(45) DEFAULT NULL,
  `pickup_StreetName` varchar(45) DEFAULT NULL,
  `pickup_City` varchar(18) DEFAULT NULL,
  `pickup_ZIPCode` int(11) DEFAULT NULL,
  `pickup_Country` varchar(45) DEFAULT NULL,
  `pickup_reference` varchar(18) DEFAULT NULL,
  `delivery_relation` int(11) DEFAULT NULL,
  `delivery_CompanyName` varchar(45) DEFAULT NULL,
  `delivery_StreetName` varchar(45) DEFAULT NULL,
  `delivery_City` varchar(18) DEFAULT NULL,
  `delivery_ZIPCode` int(11) DEFAULT NULL,
  `delivery_Country` varchar(45) DEFAULT NULL,
  `delivery_reference` varchar(18) DEFAULT NULL,
  `invoice_relation` int(11) NOT NULL,
  `invoice_reference` varchar(18) DEFAULT NULL,
  `pickup_date` date DEFAULT NULL,
  `pickup_time` time DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `delivery_time` time DEFAULT NULL,
  `delivery_conditions` varchar(36) DEFAULT NULL,
  `block` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`order_id`),
  KEY `address_id_idx` (`pickup_relation`,`delivery_relation`,`invoice_relation`),
  KEY `location_idx` (`pickup_relation`),
  KEY `adress2_idx` (`delivery_relation`),
  KEY `adress3_idx` (`invoice_relation`),
  CONSTRAINT `adress` FOREIGN KEY (`pickup_relation`) REFERENCES `address` (`address_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `adress2` FOREIGN KEY (`delivery_relation`) REFERENCES `address` (`address_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `adress3` FOREIGN KEY (`invoice_relation`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,2,'Vasile Lupu LTD','Ozno','Chisisnau',2017,'Moldova','765875vf',4,'UntZavod','Valea Bikului 17/2','Bucucresti',20756,'Romania','22',2,'3543 bfd','2015-01-25','23:56:00','2015-01-30','14:50:00','DDU',0),(2,0,'Maria Lozavan','Alea garii 13c','Iasi',30574,'Romania','oas743',25,'sod','sdj','ospdfj',34985,'Zimbabwe','22',1,'ref.','2015-01-26','14:55:00','2015-02-02','17:30:00','EXW',1),(3,0,'Ciuboraria \"La mamuca\"','Spijkenise 45/11','Oslo',6345,'Norway','765',0,'Transoflex','Ciuadad de Cortes, industriezone 5','Barcelona',64363,'Spain','',2,'Do not pay!','2015-06-06','00:00:00','2015-06-26','00:00:00','DDU',0),(10,1,'Gara Auto Nord','Valea Crucii 8','Chisinau',2072,'MD || Moldova (Republic of)','',2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',4,'','2015-06-01','00:00:00','2015-06-15','00:00:00','FAS (Free Alongside Ship)',0),(11,1,'Gara Auto Nord','Valea Crucii 8','Chisinau',2072,'MD || Moldova (Republic of)','pick-up',2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','delivery',4,'invoice','2015-06-01','00:00:00','2015-06-15','00:00:00','FAS (Free Alongside Ship)',0),(12,1,'Gara Auto Nord','Valea Crucii 8','Chisinau',2072,'MD || Moldova (Republic of)','',2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',3,'','2015-01-01','00:00:00','2015-01-02','00:00:00','CIF (Cost, Insurance and Freight)',0),(13,2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',3,'er','er','er',34,'NL || Netherlands','',4,'','2016-09-30','00:00:00','2016-10-03','00:00:00','FAS (Free Alongside Ship)',0),(17,0,'Gara Auto Nord','Valea Crucii 8','Chisinau',2072,'MD || Moldova (Republic of)','',0,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',4,'','2015-01-01','00:00:00','2015-01-01','00:00:00','EXW (EX-Works)',0),(18,0,'Gara Auto Nord','Valea Crucii 8','Chisinau',2072,'MD || Moldova (Republic of)','',2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',2,'','2015-01-01','00:00:00','2016-01-01','00:00:00','EXW (EX-Works)',0),(19,0,' ruru','restw 23','Chiinau',2012,'MD || Moldova (Republic of)','ere',2,'Plaiul Barladean','Satul Birladeni','Barladeni',7112,'NL || Netherlands','',3,'','2015-04-04','00:00:00','2014-09-03','00:00:00','DAP (Delivered At Place)',0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `action` varchar(45) NOT NULL,
  `ok/nok` varchar(3) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,3,'loaded','ok','2015-05-12 11:44:37'),(2,3,'Cross dock in','ok','2015-05-12 18:04:30'),(3,2,'Pick-up','nok','2015-05-12 18:05:37'),(4,11,'Pick-up','ok','2015-05-12 18:07:46'),(5,11,'Cross dock in','ok','2015-05-12 18:07:50'),(6,11,'Cross dock out','ok','2015-05-12 18:07:54'),(7,11,'Delivery','ok','2015-05-12 18:07:59'),(8,13,'Order unblocked','ok','2015-05-12 19:12:03'),(9,13,'order line updated','ok','2015-05-12 19:12:25'),(10,13,'Order blocked','ok','2015-05-12 19:12:57'),(11,2,'Order blocked','ok','2015-05-12 19:19:41'),(12,13,'Order unblocked','ok','2015-05-12 19:19:44'),(13,13,'Pick-up','ok','2015-05-12 19:31:42'),(14,17,'order line updated','ok','2015-05-12 19:48:23'),(15,17,'Pick-up','ok','2015-05-12 19:51:18'),(16,18,'Order created','ok','2015-05-12 20:01:17'),(17,18,'order line updated','ok','2015-05-12 20:01:55'),(18,1,'Pick-up','ok','2015-05-12 20:07:56'),(19,1,'order line updated','ok','2015-05-12 20:08:08'),(20,19,'Order created','ok','2015-05-12 20:33:45'),(21,19,'Pick-up','ok','2015-05-12 20:34:14');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text`
--

DROP TABLE IF EXISTS `text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text` (
  `order` int(11) NOT NULL,
  `type` varchar(3) DEFAULT NULL,
  `text` varchar(45) NOT NULL,
  PRIMARY KEY (`order`),
  CONSTRAINT `ordder_number` FOREIGN KEY (`order`) REFERENCES `orders` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text`
--

LOCK TABLES `text` WRITE;
/*!40000 ALTER TABLE `text` DISABLE KEYS */;
/*!40000 ALTER TABLE `text` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-14 17:15:07
