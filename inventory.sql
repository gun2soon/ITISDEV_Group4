CREATE DATABASE  IF NOT EXISTS `inventory` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `inventory`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: inventory
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `coffeebeans`
--

DROP TABLE IF EXISTS `coffeebeans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coffeebeans` (
  `idCoffeeBeans` int NOT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`idCoffeeBeans`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coffeebeans`
--

LOCK TABLES `coffeebeans` WRITE;
/*!40000 ALTER TABLE `coffeebeans` DISABLE KEYS */;
INSERT INTO `coffeebeans` VALUES (1001,10.00,1000,'g');
/*!40000 ALTER TABLE `coffeebeans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cups`
--

DROP TABLE IF EXISTS `cups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cups` (
  `idCups` int NOT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `unit` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`idCups`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cups`
--

LOCK TABLES `cups` WRITE;
/*!40000 ALTER TABLE `cups` DISABLE KEYS */;
INSERT INTO `cups` VALUES (2001,100.00,1000,'16 oz Cold','pcs'),(2002,100.00,1000,'16 oz Hot','pcs');
/*!40000 ALTER TABLE `cups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lids`
--

DROP TABLE IF EXISTS `lids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lids` (
  `idLids` int NOT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `unit` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`idLids`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lids`
--

LOCK TABLES `lids` WRITE;
/*!40000 ALTER TABLE `lids` DISABLE KEYS */;
INSERT INTO `lids` VALUES (3001,100.00,1000,'Cold','pcs'),(3002,100.00,1000,'Hot','pcs');
/*!40000 ALTER TABLE `lids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milk`
--

DROP TABLE IF EXISTS `milk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `milk` (
  `idMilk` int NOT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`idMilk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milk`
--

LOCK TABLES `milk` WRITE;
/*!40000 ALTER TABLE `milk` DISABLE KEYS */;
INSERT INTO `milk` VALUES (4001,100.00,1000,'ml');
/*!40000 ALTER TABLE `milk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `straws`
--

DROP TABLE IF EXISTS `straws`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `straws` (
  `idStraws` int NOT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`idStraws`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `straws`
--

LOCK TABLES `straws` WRITE;
/*!40000 ALTER TABLE `straws` DISABLE KEYS */;
INSERT INTO `straws` VALUES (5001,100.00,1000,'pcs');
/*!40000 ALTER TABLE `straws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syrup`
--

DROP TABLE IF EXISTS `syrup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `syrup` (
  `idSyrup` int NOT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `unit` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`idSyrup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syrup`
--

LOCK TABLES `syrup` WRITE;
/*!40000 ALTER TABLE `syrup` DISABLE KEYS */;
INSERT INTO `syrup` VALUES (6001,10.00,1000,'Chocolate','ml'),(6002,20.00,1000,'Vanilla','ml'),(6003,30.00,1000,'Caramel','ml'),(6004,400.00,1000,'Simple','ml');
/*!40000 ALTER TABLE `syrup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-14  9:44:41
