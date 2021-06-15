-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versi�n del servidor:         10.4.17-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versi�n:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `nube_proy`;
USE `nube_proy`;

CREATE TABLE IF NOT EXISTS `comentarios` (
  `idComentario` int(11) NOT NULL AUTO_INCREMENT,
  `idVideo` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  `comentario` text NOT NULL,
  `fechaComentario` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idComentario`),
  KEY `idVideo` (`idVideo`),
  KEY `idCuenta` (`idCuenta`),
  CONSTRAINT `id_cuenta_comentarios` FOREIGN KEY (`idCuenta`) REFERENCES `cuenta` (`idCuenta`),
  CONSTRAINT `id_video_comentarios` FOREIGN KEY (`idVideo`) REFERENCES `video` (`idVideo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `comentarios`;

CREATE TABLE IF NOT EXISTS `cuenta` (
  `idCuenta` int(11) NOT NULL AUTO_INCREMENT,
  `idProveedor` int(11) NOT NULL,
  `correo` varchar(128) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idCuenta`),
  KEY `idProveedor` (`idProveedor`),
  CONSTRAINT `id_proveedor_cuenta` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `cuenta`;

CREATE TABLE IF NOT EXISTS `interacciones` (
  `idInteracciones` int(11) NOT NULL AUTO_INCREMENT,
  `idVideo` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  `interaccion` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idInteracciones`),
  KEY `idVideo` (`idVideo`),
  KEY `idCuenta` (`idCuenta`),
  CONSTRAINT `id_cuenta_interaccion` FOREIGN KEY (`idCuenta`) REFERENCES `cuenta` (`idCuenta`),
  CONSTRAINT `id_video_interaccion` FOREIGN KEY (`idVideo`) REFERENCES `video` (`idVideo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `interacciones`;

CREATE TABLE IF NOT EXISTS `proveedor` (
  `idProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `nombreProveedor` varchar(128) NOT NULL,
  `urlProveedor` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `proveedor`;

CREATE TABLE IF NOT EXISTS `tipo_usuario` (
  `idTipo` int(11) NOT NULL AUTO_INCREMENT,
  `tipoUsuario` varchar(50) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `tipo_usuario`;

CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `idTipo` int(11) NOT NULL,
  `nombreUsuario` varchar(128) NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(255) NOT NULL,
  `correo` varchar(128) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `idTipo` (`idTipo`),
  CONSTRAINT `id_tipo_usuario` FOREIGN KEY (`idTipo`) REFERENCES `tipo_usuario` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `usuario`;

CREATE TABLE IF NOT EXISTS `video` (
  `idVideo` int(11) NOT NULL AUTO_INCREMENT,
  `idCuenta` int(11) NOT NULL,
  `idProveedor` int(11) NOT NULL,
  `nombreVideo` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idVideo`),
  KEY `idCuenta` (`idCuenta`),
  KEY `idProveedor` (`idProveedor`),
  CONSTRAINT `id_cuenta_video` FOREIGN KEY (`idCuenta`) REFERENCES `cuenta` (`idCuenta`),
  CONSTRAINT `id_proveedor_video` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM `video`;