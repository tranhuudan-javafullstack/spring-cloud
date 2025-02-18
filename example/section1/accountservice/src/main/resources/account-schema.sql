
--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (29,'huudan admin','$2a$10$JEoT2VQNRoXD9QRnBq7DhOcwtIh0GUf3Ip50Chrv0H9lg2K8JH.1q','jmasteradmin',NULL),(30,'admin','$2a$10$D2wR/lxXxS5mqJo9rIKgbuBTDpXL6EucA2iRsJ6y9yHEJV2LZJBxy','admin','trungtamjava.com@gmail.com'),(31,'user','$2a$10$0IyoOkgCet1Eh16rbUgma.TIRk6ZVAdz6KfY27f/mt3vOS3epXeOS','user',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `account_id` bigint NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  KEY `FK7ieyawxkuj3vu3sgdqphkqyw7` (`account_id`),
  CONSTRAINT `FK7ieyawxkuj3vu3sgdqphkqyw7` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (29,'ROLE_USER'),(29,'ROLE_ADMIN'),(30,'ROLE_ADMIN'),(31,'ROLE_USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;