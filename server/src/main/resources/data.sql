DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` int NOT NULL,
                         `username` varchar(45) DEFAULT NULL,
                         `password` varchar(64) DEFAULT NULL,
                         `role` varchar(45) DEFAULT NULL,
                         `enabled` tinyint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'francois','ici','USER',1);
UNLOCK TABLES;