-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: localhost    Database: musicplayerudc
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `accounts` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(127) NOT NULL,
  `type` tinyint(4) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'Mar','123',0),(2,'Mark','123',1),(3,'Zsiih','123',0),(4,'Jude','123',1);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `albums`
--

DROP TABLE IF EXISTS `albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `albums` (
  `album_id` int(11) NOT NULL,
  `album_name` varchar(80) NOT NULL,
  `year` int(11) DEFAULT '0',
  `img_path` varchar(260) DEFAULT NULL,
  `img_blob` blob,
  `publish` tinyint(1) NOT NULL,
  `pk_album` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`pk_album`),
  KEY `fk_albumID_idx` (`album_id`),
  KEY `fk_user_id_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albums`
--

LOCK TABLES `albums` WRITE;
/*!40000 ALTER TABLE `albums` DISABLE KEYS */;
INSERT INTO `albums` VALUES (1,'kirito',1999,NULL,NULL,0,1,2),(2,'asuna',2000,NULL,NULL,1,2,4),(3,'heathcligg',2000,NULL,NULL,1,3,2),(4,'kaskdas',2001,NULL,NULL,1,4,4),(5,'asdasf',1899,NULL,NULL,1,5,2);
/*!40000 ALTER TABLE `albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artists`
--

DROP TABLE IF EXISTS `artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `artists` (
  `artist_id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_name` varchar(80) NOT NULL,
  PRIMARY KEY (`artist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artists`
--

LOCK TABLES `artists` WRITE;
/*!40000 ALTER TABLE `artists` DISABLE KEYS */;
INSERT INTO `artists` VALUES (1,'adsdss'),(2,'dasdasd'),(3,'sadas'),(4,'sxdcfgvhb '),(5,'rosy'),(6,'ghfghfg'),(7,'dsgsdg'),(8,'klfdjgdlfk'),(9,'beepbop'),(10,'gi'),(11,'nyerp'),(12,'tfhgjh'),(13,'fuigydifg'),(14,'hkjhjkj'),(15,'Jesse&Joy'),(16,'rosyy'),(17,'Hwasa');
/*!40000 ALTER TABLE `artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `favorite` (
  `fave_userid` int(11) NOT NULL,
  `fave_id` int(11) NOT NULL,
  `fave_type` int(1) NOT NULL,
  KEY `fuserid_idx` (`fave_userid`),
  CONSTRAINT `fuserid` FOREIGN KEY (`fave_userid`) REFERENCES `accounts` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (1,1,0),(2,3,1),(2,2,0),(3,1,0),(4,4,1);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `follow` (
  `follower` int(11) NOT NULL,
  `following` int(11) NOT NULL,
  `pk_follow` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pk_follow`),
  KEY `fk_me_idx` (`follower`),
  CONSTRAINT `fk_me` FOREIGN KEY (`follower`) REFERENCES `accounts` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,2,1),(1,3,2),(3,2,3),(2,1,4),(2,3,5);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `genres` (
  `genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `genre_name` varchar(45) NOT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'Pop'),(2,'Rock'),(3,'Kpop'),(4,'Jazz'),(5,'Country'),(6,'Instrumental'),(7,'Classic');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_songs`
--

DROP TABLE IF EXISTS `playlist_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `playlist_songs` (
  `playlist_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `pk_playlist` int(11) NOT NULL,
  PRIMARY KEY (`pk_playlist`),
  KEY `playlist_id_idx` (`playlist_id`),
  KEY `song_id_idx` (`song_id`),
  CONSTRAINT `playlist_id` FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`playlist_id`) ON DELETE CASCADE,
  CONSTRAINT `song_id` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_songs`
--

LOCK TABLES `playlist_songs` WRITE;
/*!40000 ALTER TABLE `playlist_songs` DISABLE KEYS */;
INSERT INTO `playlist_songs` VALUES (1,3,1),(2,2,2),(3,4,3),(4,1,4);
/*!40000 ALTER TABLE `playlist_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlists`
--

DROP TABLE IF EXISTS `playlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `playlists` (
  `playlist_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL,
  `user_id` int(11) NOT NULL,
  `img_path` varchar(260) DEFAULT NULL,
  `img_blob` blob,
  PRIMARY KEY (`playlist_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `accounts` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlists`
--

LOCK TABLES `playlists` WRITE;
/*!40000 ALTER TABLE `playlists` DISABLE KEYS */;
INSERT INTO `playlists` VALUES (1,'chill',1,NULL,NULL),(2,'a',2,NULL,NULL),(3,'b',3,NULL,NULL),(4,'c',4,NULL,NULL);
/*!40000 ALTER TABLE `playlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `songs` (
  `song_id` int(11) NOT NULL AUTO_INCREMENT,
  `genre_id` int(11) DEFAULT NULL,
  `album_id` int(11) DEFAULT NULL,
  `song_name` varchar(45) NOT NULL,
  `song_upload` date DEFAULT NULL,
  `song_year` int(4) DEFAULT NULL,
  `song_path` varchar(260) NOT NULL,
  `song_blob` blob,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`song_id`),
  KEY `fk_albumid_idx` (`album_id`),
  KEY `fk_genreid_idx` (`genre_id`),
  KEY `fk_artsit_id_idx` (`user_id`),
  CONSTRAINT `fk_albumid` FOREIGN KEY (`album_id`) REFERENCES `albums` (`pk_album`) ON DELETE CASCADE,
  CONSTRAINT `fk_artsit_id` FOREIGN KEY (`user_id`) REFERENCES `accounts` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_genreid` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES (1,4,2,'hahhaa','2018-09-23',1999,'src/mediaplayer/song/Alexander Hamilton (music lyrics) HD.mp3',NULL,2),(2,2,3,'nananan','2018-10-23',1999,'src/mediaplayer/song/¡Corre!-Jesse&Joy.mp3',NULL,2),(3,4,4,'wasup','2018-12-23',2000,'src/mediaplayer/song/BTS (방탄소년단) \'IDOL\' Official MV.mp3',NULL,4),(4,1,5,'n','2018-02-23',2019,'src/mediaplayer/song/rosy-Fever-Medley-Song.mp3',NULL,4),(5,3,1,'fk','2018-08-23',2018,'src/mediaplayer/song/XXXTENTACION - Jocelyn Flores (Audio).mp3',NULL,2),(17,2,1,'Song Try 1',NULL,NULL,'src/mediaPlayer/song/BTS (방탄소년단) \'IDOL\' Official MV.mp3',NULL,2),(18,2,1,'Song Try 1',NULL,NULL,'src/mediaPlayer/song/BTS (방탄소년단) \'IDOL\' Official MV.mp3',NULL,2),(19,2,1,'Song Try 1',NULL,NULL,'src/mediaPlayer/song/BTS (방탄소년단) \'IDOL\' Official MV.mp3',NULL,2),(26,2,1,'Ty2',NULL,NULL,'src/mediaPlayer/song/Daniel Powter-Bad Day (lyrics).mp3',NULL,2),(27,2,2,'Ty2',NULL,NULL,'src/mediaPlayer/song/Daniel Powter-Bad Day (lyrics).mp3',NULL,2),(29,2,2,'Coma',NULL,NULL,'src/mediaPlayer/song/-GUMI- COMA -original-.mp3',NULL,2),(30,2,2,'Coma',NULL,NULL,'src/mediaPlayer/song/-GUMI- COMA -original-.mp3',NULL,2),(33,2,1,'Coma',NULL,NULL,'src/mediaPlayer/song/-GUMI- COMA -original-.mp3',NULL,2);
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `times_played`
--

DROP TABLE IF EXISTS `times_played`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `times_played` (
  `song_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  KEY `user_id_idx` (`user_id`),
  KEY `songId_idx` (`song_id`),
  CONSTRAINT `songId` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE CASCADE,
  CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `accounts` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `times_played`
--

LOCK TABLES `times_played` WRITE;
/*!40000 ALTER TABLE `times_played` DISABLE KEYS */;
/*!40000 ALTER TABLE `times_played` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-20 12:56:17
