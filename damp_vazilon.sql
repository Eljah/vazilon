-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.5.36 - MySQL Community Server (GPL)
-- ОС Сервера:                   Win64
-- HeidiSQL Версия:              8.2.0.4675
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп структуры базы данных tourism
CREATE DATABASE IF NOT EXISTS `tourism` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `tourism`;


-- Дамп структуры для таблица tourism.excursion_plan
CREATE TABLE IF NOT EXISTS `excursion_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `explanation` text COLLATE utf8_bin NOT NULL,
  `short_explanation` char(200) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `author_guide` int(11) NOT NULL DEFAULT '0',
  `vote` float DEFAULT NULL,
  `vote_count` int(11) DEFAULT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK_excursion_plan_users` (`author_guide`),
  CONSTRAINT `FK_excursion_plan_users` FOREIGN KEY (`author_guide`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.excursion_plan: ~13 rows (приблизительно)
/*!40000 ALTER TABLE `excursion_plan` DISABLE KEYS */;
INSERT INTO `excursion_plan` (`id`, `explanation`, `short_explanation`, `author_guide`, `vote`, `vote_count`) VALUES
	(1, 'Обзорная экскурсия по Казани на автобусе. Туристы увидят старые и новые районы, посмотрят с двухэтажного автобуса на красоты города. Экскурсовод им расскажет Бла бла бла бла бла  бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла', 'Обзорная по Казани', 2, 3.25, 4),
	(2, 'Экскурсия в Булгар на "метеоре". Мимо сказочных гор Камского Устья, через рукотворное Куйбышевское Море поплывут наши туристы под страшные байки матерого речного волка.', 'Булгар', 2, NULL, NULL),
	(3, 'Экскурсия в Свияжск на теплоходе. В марте. В программе блины, сжигание чучела и дрейф на льдине. 2', 'Свияжск', 2, NULL, NULL),
	(4, 'Экскурсия в Раифу на автобусе. Для тех, кому понравится в дендрарии - в подарок гербарий! 2', 'Раифа', 2, 4, 1),
	(5, 'Место, где есть курган, вода и песок. А что, нужно что-то еще?', 'Атабаево', 2, NULL, NULL),
	(6, 'Тут нечего смотреть', 'Зеленодольск', 2, NULL, NULL),
	(7, 'Тут нечего смотреть', 'Зеленодольск', 2, 4, 1),
	(8, 'Музей Константина Васильева на автобусе', 'Васильево', 2, NULL, NULL),
	(9, 'Поездка в усадьбу, где отбывал ссылку Владимир Ильич Ленин', 'Ленино-Кокушкино', 2, 4, 1),
	(10, 'Поездка на север от Казани, в деревни Каймары и Альдемыш закончится дегустацией в Алатском спиртзаводе', 'Алатская дорога', 2, 3, 2),
	(11, 'На родине великого русского поэта вас ждет незабываемое приключение', 'Державино', 2, 3.5, 2),
	(12, 'Маленький городок "как бы на берегу моря" обладает древней историей', 'Лаишево', 2, NULL, NULL),
	(13, 'По пути в Тетюши посетим усадьбу Долгая Поляна, родник Михаила Убиенного и водопад в ущелье', 'Тетюши', 2, NULL, NULL);
/*!40000 ALTER TABLE `excursion_plan` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.excursion_trip
CREATE TABLE IF NOT EXISTS `excursion_trip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `leading_guide` int(11) NOT NULL,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `plan_id` int(11) NOT NULL,
  `vote` float DEFAULT NULL,
  `vote_count` int(11) DEFAULT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK_excursion_trip_users` (`leading_guide`),
  KEY `FK_excursion_trip_excursion_plan` (`plan_id`),
  CONSTRAINT `FK_excursion_trip_excursion_plan` FOREIGN KEY (`plan_id`) REFERENCES `excursion_plan` (`id`),
  CONSTRAINT `FK_excursion_trip_users` FOREIGN KEY (`leading_guide`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы tourism.excursion_trip: ~43 rows (приблизительно)
/*!40000 ALTER TABLE `excursion_trip` DISABLE KEYS */;
INSERT INTO `excursion_trip` (`id`, `date`, `leading_guide`, `complete`, `plan_id`, `vote`, `vote_count`) VALUES
	(1, '2015-12-13', 2, b'0', 1, NULL, NULL),
	(2, '2015-12-26', 2, b'0', 1, 3, 2),
	(3, '2015-10-09', 2, b'0', 2, NULL, NULL),
	(4, '2015-10-04', 1, b'0', 3, NULL, NULL),
	(5, '2015-10-11', 2, b'0', 4, NULL, NULL),
	(6, '2015-11-29', 2, b'0', 4, 4, 1),
	(7, '2015-10-30', 2, b'0', 6, NULL, NULL),
	(8, '2015-10-17', 2, b'0', 12, NULL, NULL),
	(9, '2015-10-09', 2, b'0', 4, NULL, NULL),
	(10, '2015-10-05', 2, b'0', 12, NULL, NULL),
	(11, '2015-10-06', 2, b'0', 5, NULL, NULL),
	(12, '2015-12-04', 2, b'0', 9, 4, 1),
	(13, '2015-10-31', 2, b'0', 10, NULL, NULL),
	(14, '2015-10-16', 2, b'0', 8, NULL, NULL),
	(15, '2015-10-23', 2, b'0', 11, NULL, NULL),
	(16, '2015-10-15', 2, b'0', 10, NULL, NULL),
	(17, '2015-10-18', 2, b'0', 8, NULL, NULL),
	(18, '2016-01-10', 2, b'0', 1, 3, 2),
	(19, '2015-10-23', 2, b'0', 11, NULL, NULL),
	(20, '2015-10-31', 2, b'0', 4, NULL, NULL),
	(21, '2015-10-09', 2, b'0', 7, NULL, NULL),
	(22, '2015-10-01', 2, b'0', 9, NULL, NULL),
	(23, '2015-10-18', 2, b'0', 12, NULL, NULL),
	(24, '2015-10-31', 2, b'0', 7, NULL, NULL),
	(25, '2015-10-18', 2, b'0', 7, NULL, NULL),
	(26, '2015-10-31', 2, b'0', 5, NULL, NULL),
	(27, '2015-10-06', 2, b'0', 9, NULL, NULL),
	(28, '2015-10-03', 2, b'0', 4, NULL, NULL),
	(29, '2015-10-17', 2, b'0', 13, NULL, NULL),
	(30, '2015-10-15', 2, b'0', 3, NULL, NULL),
	(31, '2015-10-24', 2, b'0', 5, NULL, NULL),
	(32, '2015-10-09', 2, b'0', 10, NULL, NULL),
	(33, '2015-10-15', 2, b'0', 8, NULL, NULL),
	(34, '2015-10-02', 2, b'0', 10, NULL, NULL),
	(35, '2015-10-16', 2, b'0', 3, NULL, NULL),
	(36, '2015-10-08', 2, b'0', 7, NULL, NULL),
	(37, '2015-12-13', 2, b'0', 7, 4, 1),
	(38, '2015-10-02', 1, b'0', 4, NULL, NULL),
	(39, '2015-11-03', 2, b'0', 7, NULL, NULL),
	(40, '2016-01-30', 2, b'0', 1, 4, 1),
	(41, '2015-11-28', 2, b'0', 11, 4, 2),
	(42, '2015-12-31', 2, b'0', 10, 3, 1),
	(43, '2015-11-28', 1, b'0', 10, 4, 1);
/*!40000 ALTER TABLE `excursion_trip` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `text` char(255) COLLATE utf8_bin NOT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK_notification_users` (`user_id`),
  CONSTRAINT `FK_notification_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.notification: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.points
CREATE TABLE IF NOT EXISTS `points` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `name` char(255) COLLATE utf8_bin DEFAULT NULL,
  `description` text COLLATE utf8_bin,
  `author` int(11) DEFAULT NULL,
  KEY `Индекс 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.points: ~36 rows (приблизительно)
/*!40000 ALTER TABLE `points` DISABLE KEYS */;
INSERT INTO `points` (`id`, `latitude`, `longitude`, `name`, `description`, `author`) VALUES
	(1, 55.55, 44.44, 'fb.test point 1', 'fb.test point 1 description', NULL),
	(2, 55.8002, 49.1049, 'Кремль', 'fb.test point 2 description', 2),
	(3, 55.8136, 49.0869, 'hfgf', 'jhfhf', 2),
	(4, 55.7955, 49.1139, 'Старейший в Казани общественный туалет', 'Построен в 1820, обветшал к 1840м. ВОт', 2),
	(10, 55.7955, 49.1139, 'Старейший в Казани общественный туалет', 'Построен в 1820, обветшал к 1840м', 2),
	(13, 55.7963, 49.1102, 'Мэрия города', 'До революции городская управа', 2),
	(14, 55.7963, 49.1102, 'Мэрия города', 'До революции городская управа', 2),
	(15, 55.7955, 49.1139, 'Старейший в Казани общественный туалет', 'Построен в 1820, обветшал к 1840м', 2),
	(16, 55, 44, 'hfgf', 'jhfhf', 2),
	(17, 55, 44, 'neighboring fb.test point', 'fb.test point 2 description', 2),
	(18, 55.7943, 49.1124, '1', '1', 2),
	(19, 55.7943, 49.1124, '1', '1', 2),
	(20, 55.7955, 49.1139, 'Старейший в Казани общественный туалет', 'Построен в 1820, обветшал к 1840м', 2),
	(21, 55.8136, 49.0649, 'hfgf', 'jhfhf', 2),
	(22, 55.826, 49.0649, 'neighboring fb.test point', 'fb.test point 2 description', 2),
	(23, 55.7964, 49.1101, 'Мэрия города', 'до революции городская управа', 2),
	(24, 55.7964, 49.1101, 'Мэрия города', 'до революции городская управа', 2),
	(25, 55.797, 49.1007, 'Стадион-2', 'Рубин-2', 2),
	(26, 55.7968, 49.0989, 'Стадион', 'Рубин', 2),
	(27, 55.7911, 49.1216, 'Казанский федеральный университет', 'Тут Лобачевский, ИТИС, все дела', 2),
	(28, 55.7976, 49.1227, 'фыв', 'фыв', 2),
	(29, 55.7963, 49.1245, 'ыфв', 'фыв', 2),
	(30, 55.7873, 49.0912, 'Стекольня', 'Тут собирались староверы', 2),
	(31, 55.7821, 49.0886, 'Яхт клуб', 'Он расположился на месте старой железнодорожной дамбы', 2),
	(32, 55.7792, 49.0663, 'Вороний Куст', 'пролив через старое русло Ичка', 2),
	(33, 55.7796, 49.0605, 'Дальнее Устье - место старых пристаней', 'Пристани располагались в заливаемой пойме Волги', 2),
	(34, 55.7876, 49.0403, 'Остров Каменный', 'по данным справочника трамвайных остановок - тут располагалось предприятие - СК-4', 2),
	(35, 55.802, 49.1023, 'Кремлевская дамба', 'бывшая Хижницкая', 2),
	(36, 55.8092, 49.1022, 'Верхне-Зареченская дамба', 'Построена в 1957 году, отделяет "нидерланды" от Казанки', 2),
	(37, 55.8179, 49.0988, 'Козья', 'козья слобода', 2),
	(38, 55.8132, 49.1063, 'Чаша', 'она же вантус', 2),
	(39, 55.8177, 49.1103, 'Сквер Стамбул', 'Скверр новый такой', 2),
	(40, 55.8199, 49.1049, 'Тут ничо интересного', 'Ну так, пара забавностей', 2),
	(41, 55.8189, 49.0994, 'Церковь тут была', 'Пока забыл какая, гляну', 2),
	(42, 55.8162, 49.0927, 'Харовое Озеро', 'Тут хотели строить дом, но жильцы отбили его и добились организации парка.', 2),
	(43, 55.7903, 49.1098, 'Мост через Булак', 'Старейший каменный', 2);
/*!40000 ALTER TABLE `points` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.routes
CREATE TABLE IF NOT EXISTS `routes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) DEFAULT NULL,
  `point_id` int(11) NOT NULL,
  `point_order` int(11) NOT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK_routes_excursion_plan` (`plan_id`),
  KEY `FK_routes_points` (`point_id`),
  KEY `FK_routes_points_2` (`point_order`),
  CONSTRAINT `FK_routes_excursion_plan` FOREIGN KEY (`plan_id`) REFERENCES `excursion_plan` (`id`),
  CONSTRAINT `FK_routes_points` FOREIGN KEY (`point_id`) REFERENCES `points` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.routes: ~27 rows (приблизительно)
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` (`id`, `plan_id`, `point_id`, `point_order`) VALUES
	(2, 1, 2, 4),
	(3, 1, 3, 3),
	(4, 1, 4, 2),
	(18, 1, 20, 5),
	(19, 1, 21, 6),
	(20, 1, 22, 7),
	(21, 1, 23, 8),
	(22, 1, 24, 9),
	(23, 1, 25, 10),
	(24, 1, 26, 11),
	(25, 1, 27, 12),
	(26, 1, 28, 13),
	(27, 1, 29, 14),
	(28, 2, 30, 1),
	(29, 2, 31, 2),
	(30, 2, 32, 3),
	(31, 2, 33, 4),
	(32, 2, 34, 5),
	(33, 2, 35, 6),
	(34, 2, 36, 7),
	(35, 2, 37, 8),
	(36, 2, 38, 9),
	(37, 2, 39, 10),
	(38, 2, 40, 11),
	(39, 2, 41, 12),
	(40, 2, 42, 13),
	(41, 2, 43, 14);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `email` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `firstname` char(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `lastname` char(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `locale` char(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'en_US',
  `confirmed` bit(1) DEFAULT b'0',
  `active` bit(1) DEFAULT b'0',
  `uuid` char(50) DEFAULT NULL,
  `deletedate` date DEFAULT NULL,
  `vote` float DEFAULT NULL,
  `vote_count` int(11) DEFAULT NULL,
  UNIQUE KEY `Индекс 2` (`username`),
  KEY `Индекс 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы tourism.users: ~18 rows (приблизительно)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`, `password`, `email`, `role`, `firstname`, `lastname`, `locale`, `confirmed`, `active`, `uuid`, `deletedate`, `vote`, `vote_count`) VALUES
	(69, '1391809730', 'facebook', 'eljah@mail.ru', 'SIGHTSEER', 'Ilya', 'Yevlampiev', 'en', b'1', b'1', NULL, NULL, NULL, NULL),
	(56, 'eljah', 'eljah', 'eljah@mail.ru', 'SIGHTSEER', 'Илья', NULL, 'ru', b'1', b'0', NULL, NULL, NULL, NULL),
	(57, 'eljah2', 'eljah2', 'eljah@mail.ru', 'SIGHTSEER', 'Илья', 'Евлампие', 'ru', b'0', b'0', NULL, NULL, NULL, NULL),
	(2, 'guide', 'guide', 'guide@tourism.tatar', 'GUIDE', 'Neo', 'фы', 'en_US', b'1', b'0', NULL, NULL, 3, 5),
	(4, 'guide1', 'guide1', 'guide1@t.ru', 'ADMIN', 'Chief', 'Architect', 'en_US', b'0', b'0', NULL, NULL, NULL, NULL),
	(66, 'guide10', 'guide10', 'eljah@mail.ru', 'SIGHTSEER', '10', '10', 'en_US', b'1', b'0', NULL, NULL, NULL, NULL),
	(67, 'guide11', 'guide11', 'eljah@mail.ru', 'SIGHTSEER', '11', '11', 'en_US', b'1', b'0', NULL, NULL, NULL, NULL),
	(68, 'guide12', 'guide12', 'eljah@mail.ru', 'SIGHTSEER', '12', '12', 'en_US', b'1', b'0', NULL, NULL, NULL, NULL),
	(71, 'guide14', 'guide14', 'eljah@mail.ru', 'SIGHTSEER', '14', '14', 'en', b'1', b'0', NULL, NULL, NULL, NULL),
	(58, 'guide2', 'guide2', 'eljah@mail.ru', 'SIGHTSEER', '', '', 'en_US', b'0', b'0', NULL, NULL, NULL, NULL),
	(59, 'guide3', 'guide3', 'eljah@mail.ru', 'SIGHTSEER', 'Илья', 'Евлампиев', 'ru', b'0', b'0', NULL, NULL, NULL, NULL),
	(60, 'guide4', 'guide4', 'eljah@mail.ru', 'SIGHTSEER', 'Илья', 'Евлампиев', 'ru', b'1', b'0', NULL, NULL, NULL, NULL),
	(61, 'guide5', 'guide5', 'eljah@mail.ru', 'SIGHTSEER', 'i', 'ye', 'ru', b'1', b'0', NULL, NULL, NULL, NULL),
	(62, 'guide6', 'guide6', 'eljah@mail.ru', 'SIGHTSEER', '6', '6', 'en_US', b'0', b'0', NULL, NULL, NULL, NULL),
	(63, 'guide7', 'guide7', 'eljah@mail.ru', 'SIGHTSEER', '7', '7', 'en_US', b'1', b'0', NULL, NULL, NULL, NULL),
	(64, 'guide8', 'guide8', 'eljah@mail.ru', 'SIGHTSEER', '8', '8', 'en_us', b'1', b'0', NULL, NULL, NULL, NULL),
	(3, 'sightseer', 'sightseer', 'sightseer@tourism.tatar', 'BUSDRIVER', 'Trinity', 'Trinity', 'en_US', b'0', b'0', NULL, NULL, NULL, NULL),
	(1, 'user', 'user', 'user@tourism.tatar', 'ADMIN', 'John', 'Smith', 'en_US', b'1', b'0', NULL, NULL, 2, 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.users_plan_trip
CREATE TABLE IF NOT EXISTS `users_plan_trip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `plan` int(11) NOT NULL,
  `trip` int(11) DEFAULT NULL,
  `feedback_trip` text COLLATE utf8_bin,
  `stars_trip` int(11) DEFAULT NULL,
  `feedback_guide` text COLLATE utf8_bin,
  `stars_guide` int(11) DEFAULT NULL,
  `feedback_plan` text COLLATE utf8_bin,
  `stars_plan` int(11) DEFAULT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK__users` (`user`),
  KEY `FK__excursion_plan` (`plan`),
  KEY `FK__excursion_trip` (`trip`),
  CONSTRAINT `FK__excursion_plan` FOREIGN KEY (`plan`) REFERENCES `excursion_plan` (`id`),
  CONSTRAINT `FK__excursion_trip` FOREIGN KEY (`trip`) REFERENCES `excursion_trip` (`id`),
  CONSTRAINT `FK__users` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.users_plan_trip: ~19 rows (приблизительно)
/*!40000 ALTER TABLE `users_plan_trip` DISABLE KEYS */;
INSERT INTO `users_plan_trip` (`id`, `user`, `plan`, `trip`, `feedback_trip`, `stars_trip`, `feedback_guide`, `stars_guide`, `feedback_plan`, `stars_plan`) VALUES
	(24, 69, 7, 39, NULL, NULL, NULL, NULL, NULL, NULL),
	(122, 1, 4, 6, NULL, NULL, NULL, NULL, NULL, NULL),
	(123, 1, 9, 12, NULL, NULL, NULL, NULL, NULL, NULL),
	(125, 69, 11, 41, NULL, 4, NULL, 1, NULL, 4),
	(126, 69, 1, 40, NULL, NULL, NULL, NULL, NULL, NULL),
	(127, 69, 7, 37, NULL, NULL, NULL, NULL, NULL, NULL),
	(128, 69, 1, 18, NULL, 2, NULL, 4, NULL, 1),
	(134, 1, 1, 18, NULL, NULL, NULL, NULL, NULL, NULL),
	(138, 2, 10, 42, NULL, 3, NULL, 2, NULL, 4),
	(139, 2, 10, 43, NULL, 4, NULL, 2, NULL, 2),
	(141, 69, 10, 42, NULL, NULL, NULL, NULL, NULL, NULL),
	(142, 2, 11, 41, NULL, 4, NULL, 4, NULL, 3),
	(143, 1, 1, 2, NULL, 2, NULL, 4, NULL, 3),
	(145, 69, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL),
	(149, 2, 1, 40, NULL, NULL, NULL, NULL, NULL, NULL),
	(150, 2, 1, 18, NULL, NULL, NULL, NULL, NULL, NULL),
	(151, 2, 4, 6, NULL, NULL, NULL, NULL, NULL, NULL),
	(152, 69, 4, 6, NULL, NULL, NULL, NULL, NULL, NULL),
	(153, 69, 4, 6, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `users_plan_trip` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
