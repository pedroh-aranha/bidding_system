CREATE DATABASE  IF NOT EXISTS `db_bidding_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `db_bidding_system`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: db_bidding_system
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `editais`
--

DROP TABLE IF EXISTS `editais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `editais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) NOT NULL,
  `descricao` text DEFAULT NULL,
  `data_fechamento` datetime NOT NULL,
  `status` enum('ABERTO','ENCERRADO') DEFAULT 'ABERTO',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editais`
--

LOCK TABLES `editais` WRITE;
/*!40000 ALTER TABLE `editais` DISABLE KEYS */;
INSERT INTO `editais` VALUES (1,'Compra de Notebooks','Lote de 50 unidades i7 16GB','2026-12-31 23:59:59','ABERTO'),(2,'redmi note 15','lote 10 unidades','2026-12-30 00:00:00','ABERTO'),(3,'Edital Urgente 1','Aquisição de materiais de escritório','2026-06-04 00:00:00','ABERTO'),(6,'Edital Urgente 3','Aquisição de materiais de ti','2026-06-03 00:00:00','ABERTO'),(7,'teste urg','tanaka da o bumbum(pohpoh)','2026-06-05 00:00:00','ABERTO');
/*!40000 ALTER TABLE `editais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lances`
--

DROP TABLE IF EXISTS `lances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valor` decimal(10,2) NOT NULL,
  `data_lance` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_edital` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_edital` (`id_edital`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `lances_ibfk_1` FOREIGN KEY (`id_edital`) REFERENCES `editais` (`id`),
  CONSTRAINT `lances_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lances`
--

LOCK TABLES `lances` WRITE;
/*!40000 ALTER TABLE `lances` DISABLE KEYS */;
/*!40000 ALTER TABLE `lances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `role` enum('COMPRADOR','FORNECEDOR') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Admin Compras','admin@empresa.com','123456','COMPRADOR'),(2,'Tech Solutions','contato@tech.com','123456','FORNECEDOR'),(3,'Global Insumos','vendas@global.com','123456','FORNECEDOR'),(4,'zanilds','zanil@gmail.com','696969','COMPRADOR'),(6,'culhao','cul@gmail.com','987654321','COMPRADOR'),(7,'joao rosseto','joo@gmail.com','comicapim','FORNECEDOR'),(8,'pedro','pa@gmail.com','miakhalifa','FORNECEDOR'),(9,'matteus','uriel@gmail.com','senha67','FORNECEDOR');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db_bidding_system'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-03 17:24:18
