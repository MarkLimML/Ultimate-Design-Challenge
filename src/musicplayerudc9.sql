CREATE DATABASE  IF NOT EXISTS `musicplayer` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `musicplayer`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: musicplayer
-- ------------------------------------------------------
-- Server version	8.0.12

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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `album` (
  `AlbumId` int(11) NOT NULL AUTO_INCREMENT,
  `AlbumName` varchar(45) NOT NULL,
  `AlbumImage` varchar(45) NOT NULL,
  `AlbumGenre` varchar(45) NOT NULL,
  `UserID` int(11) NOT NULL,
  `Share` int(1) NOT NULL,
  PRIMARY KEY (`AlbumId`),
  KEY `aid_idx` (`UserID`),
  CONSTRAINT `aid` FOREIGN KEY (`UserID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `favorite` (
  `favoriteID` int(11) NOT NULL AUTO_INCREMENT,
  `fave_userid` int(11) NOT NULL,
  `fave_id` int(11) NOT NULL,
  `fave_type` int(1) NOT NULL,
  PRIMARY KEY (`favoriteID`),
  KEY `fkd2_idx` (`fave_id`),
  KEY `fuserid_idx` (`fave_userid`),
  CONSTRAINT `fid1` FOREIGN KEY (`fave_id`) REFERENCES `song` (`songid`) ON DELETE CASCADE,
  CONSTRAINT `fid2` FOREIGN KEY (`fave_id`) REFERENCES `playlist` (`playlistid`) ON DELETE CASCADE,
  CONSTRAINT `fuserid` FOREIGN KEY (`fave_userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (1,2,1,0),(2,2,3,0),(3,2,3,1),(4,1,3,1);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mostplayed`
--

DROP TABLE IF EXISTS `mostplayed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mostplayed` (
  `mID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `SongID` int(11) NOT NULL,
  `counter` int(11) DEFAULT NULL,
  PRIMARY KEY (`mID`),
  KEY `msid_idx` (`SongID`),
  KEY `muid_idx` (`UserID`),
  CONSTRAINT `msid` FOREIGN KEY (`SongID`) REFERENCES `song` (`songid`) ON DELETE CASCADE,
  CONSTRAINT `muid` FOREIGN KEY (`UserID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mostplayed`
--

LOCK TABLES `mostplayed` WRITE;
/*!40000 ALTER TABLE `mostplayed` DISABLE KEYS */;
INSERT INTO `mostplayed` VALUES (1,1,1,5),(3,2,3,1),(4,1,3,6);
/*!40000 ALTER TABLE `mostplayed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `playlist` (
  `PlaylistID` int(11) NOT NULL AUTO_INCREMENT,
  `PlaylistName` varchar(45) NOT NULL,
  `UserID` int(11) NOT NULL,
  PRIMARY KEY (`PlaylistID`),
  KEY `play_userid_idx` (`UserID`),
  CONSTRAINT `play_userid` FOREIGN KEY (`UserID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES (1,'Green',1),(2,'Any',4),(3,'My Hero',2),(4,'Chill',3);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `song` (
  `SongID` int(11) NOT NULL AUTO_INCREMENT,
  `SongName` varchar(100) NOT NULL,
  `SongArtist` varchar(100) DEFAULT NULL,
  `SongAlbum` varchar(100) DEFAULT NULL,
  `SongImage` varchar(500) DEFAULT NULL,
  `SongYear` int(4) DEFAULT NULL,
  `SongGenre` varchar(45) DEFAULT NULL,
  `SongMusic` varchar(500) NOT NULL,
  PRIMARY KEY (`SongID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song`
--

LOCK TABLES `song` WRITE;
/*!40000 ALTER TABLE `song` DISABLE KEYS */;
INSERT INTO `song` VALUES (1,'MyHeroAcademiaOST-YouSayRun','Yuki','Mk2','Screenshot (113).png',2073,'Isekai','MyHeroAcademiaOST-YouSayRun.mp3'),(2,'Testing','Yuki',NULL,NULL,2016,'Anime','Testing.mp3'),(3,'TestingV.2','Green HAir Dudde','',NULL,2016,'Anime','TestingV.2.mp3'),(7,'7 Years Old - By- Lucas Graham (LYRICS)',NULL,NULL,NULL,NULL,NULL,'7 Years Old - By- Lucas Graham (LYRICS).mp3');
/*!40000 ALTER TABLE `song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song_has_playlist`
--

DROP TABLE IF EXISTS `song_has_playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `song_has_playlist` (
  `song_SongID` int(11) NOT NULL,
  `playlist_PlaylistID` int(11) NOT NULL,
  PRIMARY KEY (`song_SongID`,`playlist_PlaylistID`),
  KEY `idsong_idx` (`song_SongID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song_has_playlist`
--

LOCK TABLES `song_has_playlist` WRITE;
/*!40000 ALTER TABLE `song_has_playlist` DISABLE KEYS */;
INSERT INTO `song_has_playlist` VALUES (3,3);
/*!40000 ALTER TABLE `song_has_playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(45) NOT NULL,
  `UserPassword` varchar(16) NOT NULL,
  `isArtist` int(1) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Mark','123',0),(2,'Jude','123',0),(3,'Zishi ','123',0),(4,'Aaron','123',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-22 10:54:35
