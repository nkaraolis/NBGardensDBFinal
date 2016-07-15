CREATE DATABASE  IF NOT EXISTS `nbgardensv0.2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `nbgardensv0.2`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: nbgardensv0.2
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderitems` (
  `OrderID` varchar(15) NOT NULL,
  `ProductID` varchar(45) NOT NULL,
  `Quantity` varchar(45) NOT NULL,
  `PorousRequired` varchar(3) NOT NULL,
  `Location` varchar(45) NOT NULL,
  PRIMARY KEY (`OrderID`,`ProductID`),
  KEY `orderitems_fk_2_idx` (`ProductID`),
  KEY `orderitems_fk_stock_idx` (`ProductID`),
  CONSTRAINT `orderitems_fk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `orderitems_fk_2` FOREIGN KEY (`ProductID`) REFERENCES `stock` (`ProductID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES ('OD0001','PR0003','1','No','CC'),('OD0002','PR0002','1','Yes','AB'),('OD0002','PR0009','1','No','BA'),('OD0003','PR0005','1','No','CA'),('OD0003','PR0006','1','No','CB'),('OD0003','PR0008','1','No','CC'),('OD0004','PR0001','1','No','AA'),('OD0004','PR0006','1','No','CB'),('OD0005','PR0002','1','Yes','AB'),('OD0005','PR0005','1','No','CA');
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `OrderID` varchar(15) NOT NULL,
  `Date` date NOT NULL,
  `Price` decimal(7,2) NOT NULL,
  `CustomerName` varchar(45) NOT NULL,
  `CustomerID` varchar(15) NOT NULL,
  `Address` varchar(75) NOT NULL,
  `Status` varchar(45) NOT NULL,
  `StaffAssigned` varchar(45) NOT NULL,
  PRIMARY KEY (`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('OD0001','2016-09-12',8.50,'John Smith','CU0007','1, The Street, Somewhere, ZZ12 9AB','Open','None'),('OD0002','2016-09-14',27.00,'Nick Karaolis','CU0001','45, That Place, Everywhere, AB99 7WE','Packaged','Mr Noob'),('OD0003','2016-09-13',16.00,'Nick Karaolis','CU0001','45, That Place, Everywhere, AB99 7WE','Open','None'),('OD0004','2016-09-13',16.00,'Bob Bobson','CU0003','21, The Lane, Nowhere, EW55 5SS','Open','None'),('OD0005','2016-09-13',20.00,'James Jameson','CU0005','12, The Road, Somewhere, QA11 1CC','Open','None');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `StaffID` varchar(15) NOT NULL,
  `StaffName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Role` varchar(45) NOT NULL,
  PRIMARY KEY (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('ST0001','Mr Boss','0d107d09f5bbe40cade3de5c71e9e9b7','The Boss');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `ProductID` varchar(45) NOT NULL,
  `ItemName` varchar(45) NOT NULL,
  `Price` decimal(7,2) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Location` varchar(45) NOT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES ('PR0001','Red Gnome',12.50,121,'AA'),('PR0002','Purple Gnome',15.00,45,'AB'),('PR0003','Super Compost',8.50,18,'CC'),('PR0004','Jon Snow Gnome',25.00,15,'AB'),('PR0005','Garden Trowel',5.00,15,'CA'),('PR0006','Watering Can',3.50,25,'CB'),('PR0007','Secateurs',4.10,11,'CA'),('PR0008','Spade',7.50,32,'CC'),('PR0009','Gloves',2.00,61,'BA'),('PR0010','Wheelbarrow',10.99,17,'BC');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stockorder`
--

DROP TABLE IF EXISTS `stockorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stockorder` (
  `StockOrderID` varchar(10) NOT NULL,
  `ItemName` varchar(45) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` decimal(8,2) NOT NULL,
  PRIMARY KEY (`StockOrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockorder`
--

LOCK TABLES `stockorder` WRITE;
/*!40000 ALTER TABLE `stockorder` DISABLE KEYS */;
INSERT INTO `stockorder` VALUES ('SO0001','Gloves',20,45.50);
/*!40000 ALTER TABLE `stockorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouseloc`
--

DROP TABLE IF EXISTS `warehouseloc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouseloc` (
  `Sector` varchar(45) NOT NULL,
  `AA` varchar(45) NOT NULL,
  `AB` varchar(45) NOT NULL,
  `AC` varchar(45) NOT NULL,
  `BA` varchar(45) NOT NULL,
  `BB` varchar(45) NOT NULL,
  `BC` varchar(45) NOT NULL,
  `CA` varchar(45) NOT NULL,
  `CB` varchar(45) NOT NULL,
  `CC` varchar(45) NOT NULL,
  PRIMARY KEY (`Sector`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouseloc`
--

LOCK TABLES `warehouseloc` WRITE;
/*!40000 ALTER TABLE `warehouseloc` DISABLE KEYS */;
INSERT INTO `warehouseloc` VALUES ('AA','0','5','10','10','15','20','20','25','30'),('AB','5','0','5','15','20','25','25','30','35'),('AC','10','5','0','20','25','30','30','35','40'),('BA','10','15','20','0','5','10','10','15','20'),('BB','15','20','25','5','0','5','15','20','25'),('BC','20','25','30','10','5','0','20','25','30'),('CA','20','25','30','10','15','20','0','5','10'),('CB','25','30','35','15','20','25','5','0','5'),('CC','30','35','40','20','25','30','10','5','0');
/*!40000 ALTER TABLE `warehouseloc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-29 14:27:43
