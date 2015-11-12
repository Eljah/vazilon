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
  KEY `Индекс 1` (`id`),
  KEY `FK_excursion_plan_users` (`author_guide`),
  CONSTRAINT `FK_excursion_plan_users` FOREIGN KEY (`author_guide`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.excursion_plan: ~13 rows (приблизительно)
/*!40000 ALTER TABLE `excursion_plan` DISABLE KEYS */;
INSERT INTO `excursion_plan` (`id`, `explanation`, `short_explanation`, `author_guide`) VALUES
	(1, 'Обзорная экскурсия по Казани на автобусе. Туристы увидят старые и новые районы, посмотрят с двухэтажного автобуса на красоты города. Экскурсовод им расскажет Бла бла бла бла бла  бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла', 'Обзорная по Казани', 2),
	(2, 'Экскурсия в Булгар на "метеоре". Мимо сказочных гор Камского Устья, через рукотворное Куйбышевское Море поплывут наши туристы под страшные байки матерого речного волка.', 'Булгар', 2),
	(3, 'Экскурсия в Свияжск на теплоходе. В марте. В программе блины, сжигание чучела и дрейф на льдине. 2', 'Свияжск', 2),
	(4, 'Экскурсия в Раифу на автобусе. Для тех, кому понравится в дендрарии - в подарок гербарий! 2', 'Раифа', 2),
	(5, 'Место, где есть курган, вода и песок. А что, нужно что-то еще?', 'Атабаево', 2),
	(6, 'Тут нечего смотреть', 'Зеленодольск', 2),
	(7, 'Тут нечего смотреть', 'Зеленодольск', 2),
	(8, 'Музей Константина Васильева на автобусе', 'Васильево', 2),
	(9, 'Поездка в усадьбу, где отбывал ссылку Владимир Ильич Ленин', 'Ленино-Кокушкино', 2),
	(10, 'Поездка на север от Казани, в деревни Каймары и Альдемыш закончится дегустацией в Алатском спиртзаводе', 'Алатская дорога', 2),
	(11, 'На родине великого русского поэта вас ждет незабываемое приключение', 'Державино', 2),
	(12, 'Маленький городок "как бы на берегу моря" обладает древней историей', 'Лаишево', 2),
	(13, 'По пути в Тетюши посетим усадьбу Долгая Поляна, родник Михаила Убиенного и водопад в ущелье', 'Тетюши', 2);
/*!40000 ALTER TABLE `excursion_plan` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.excursion_trip
CREATE TABLE IF NOT EXISTS `excursion_trip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `leading_guide` int(11) NOT NULL,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `plan_id` int(11) NOT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK_excursion_trip_users` (`leading_guide`),
  KEY `FK_excursion_trip_excursion_plan` (`plan_id`),
  CONSTRAINT `FK_excursion_trip_excursion_plan` FOREIGN KEY (`plan_id`) REFERENCES `excursion_plan` (`id`),
  CONSTRAINT `FK_excursion_trip_users` FOREIGN KEY (`leading_guide`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы tourism.excursion_trip: ~40 rows (приблизительно)
/*!40000 ALTER TABLE `excursion_trip` DISABLE KEYS */;
INSERT INTO `excursion_trip` (`id`, `date`, `leading_guide`, `complete`, `plan_id`) VALUES
	(1, '2015-12-12', 2, b'0', 1),
	(2, '2015-12-26', 2, b'0', 1),
	(3, '2015-10-09', 2, b'0', 2),
	(4, '2015-10-04', 1, b'0', 3),
	(5, '2015-10-11', 2, b'0', 4),
	(6, '2015-11-29', 2, b'0', 4),
	(7, '2015-10-30', 2, b'0', 6),
	(8, '2015-10-17', 2, b'0', 12),
	(9, '2015-10-09', 2, b'0', 4),
	(10, '2015-10-05', 2, b'0', 12),
	(11, '2015-10-06', 2, b'0', 5),
	(12, '2015-12-04', 2, b'0', 9),
	(13, '2015-10-31', 2, b'0', 10),
	(14, '2015-10-16', 2, b'0', 8),
	(15, '2015-10-23', 2, b'0', 11),
	(16, '2015-10-15', 2, b'0', 10),
	(17, '2015-10-18', 2, b'0', 8),
	(18, '2016-01-10', 2, b'0', 1),
	(19, '2015-10-23', 2, b'0', 11),
	(20, '2015-10-31', 2, b'0', 4),
	(21, '2015-10-09', 2, b'0', 7),
	(22, '2015-10-01', 2, b'0', 9),
	(23, '2015-10-18', 2, b'0', 12),
	(24, '2015-10-31', 2, b'0', 7),
	(25, '2015-10-18', 2, b'0', 7),
	(26, '2015-10-31', 2, b'0', 5),
	(27, '2015-10-06', 2, b'0', 9),
	(28, '2015-10-03', 2, b'0', 4),
	(29, '2015-10-17', 2, b'0', 13),
	(30, '2015-10-15', 2, b'0', 3),
	(31, '2015-10-24', 2, b'0', 5),
	(32, '2015-10-09', 2, b'0', 10),
	(33, '2015-10-15', 2, b'0', 8),
	(34, '2015-10-02', 2, b'0', 10),
	(35, '2015-10-16', 2, b'0', 3),
	(36, '2015-10-08', 2, b'0', 7),
	(37, '2015-12-13', 2, b'0', 7),
	(38, '2015-10-02', 1, b'0', 4),
	(39, '2015-11-03', 2, b'0', 7),
	(40, '2016-01-30', 2, b'0', 1),
	(41, '2015-11-28', 2, b'0', 11),
	(42, '2015-12-31', 2, b'0', 10);
/*!40000 ALTER TABLE `excursion_trip` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `email` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` char(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `lastname` char(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `locale` char(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'en_US',
  `confirmed` bit(1) DEFAULT b'0',
  `active` bit(1) DEFAULT b'0',
  `uuid` char(50) DEFAULT NULL,
  `deletedate` date DEFAULT NULL,
  KEY `Индекс 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы tourism.users: ~18 rows (приблизительно)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `email`, `role`, `id`, `firstname`, `lastname`, `locale`, `confirmed`, `active`, `uuid`, `deletedate`) VALUES
	('user', 'user', 'user@tourism.tatar', 'ADMIN', 1, 'John', 'Smith', 'en_US', b'1', b'0', NULL, NULL),
	('guide', 'guide', 'guide@tourism.tatar', 'GUIDE', 2, 'Neo', 'фы', 'en_US', b'1', b'0', NULL, NULL),
	('sightseer', 'sightseer', 'sightseer@tourism.tatar', 'BUSDRIVER', 3, 'Trinity', 'Trinity', 'en_US', b'0', b'0', NULL, NULL),
	('guide1', 'guide1', 'guide1@t.ru', 'ADMIN', 4, 'Chief', 'Architect', 'en_US', b'0', b'0', NULL, NULL),
	('eljah', 'eljah', 'eljah@mail.ru', 'SIGHTSEER', 56, 'Илья', NULL, 'ru', b'1', b'0', NULL, NULL),
	('eljah2', 'eljah2', 'eljah@mail.ru', 'SIGHTSEER', 57, 'Илья', 'Евлампие', 'ru', b'0', b'0', NULL, NULL),
	('guide2', 'guide2', 'eljah@mail.ru', 'SIGHTSEER', 58, '', '', 'en_US', b'0', b'0', NULL, NULL),
	('guide3', 'guide3', 'eljah@mail.ru', 'SIGHTSEER', 59, 'Илья', 'Евлампиев', 'ru', b'0', b'0', NULL, NULL),
	('guide4', 'guide4', 'eljah@mail.ru', 'SIGHTSEER', 60, 'Илья', 'Евлампиев', 'ru', b'1', b'0', NULL, NULL),
	('guide5', 'guide5', 'eljah@mail.ru', 'SIGHTSEER', 61, 'i', 'ye', 'ru', b'1', b'0', NULL, NULL),
	('guide6', 'guide6', 'eljah@mail.ru', 'SIGHTSEER', 62, '6', '6', 'en_US', b'0', b'0', NULL, NULL),
	('guide7', 'guide7', 'eljah@mail.ru', 'SIGHTSEER', 63, '7', '7', 'en_US', b'1', b'0', NULL, NULL),
	('guide8', 'guide8', 'eljah@mail.ru', 'SIGHTSEER', 64, '8', '8', 'en_us', b'1', b'0', NULL, NULL),
	('guide10', 'guide10', 'eljah@mail.ru', 'SIGHTSEER', 66, '10', '10', 'en_US', b'1', b'0', NULL, NULL),
	('guide11', 'guide11', 'eljah@mail.ru', 'SIGHTSEER', 67, '11', '11', 'en_US', b'1', b'0', NULL, NULL),
	('guide12', 'guide12', 'eljah@mail.ru', 'SIGHTSEER', 68, '12', '12', 'en_US', b'1', b'0', NULL, NULL),
	('1391809730', 'facebook', 'eljah@mail.ru', 'SIGHTSEER', 69, 'Ilya', 'Yevlampiev', 'en', b'1', b'1', NULL, NULL),
	('guide14', 'guide14', 'eljah@mail.ru', 'SIGHTSEER', 71, '14', '14', 'en', b'1', b'0', NULL, NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;


-- Дамп структуры для таблица tourism.users_plan_trip
CREATE TABLE IF NOT EXISTS `users_plan_trip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `plan` int(11) NOT NULL,
  `trip` int(11) DEFAULT NULL,
  `feedback_trip` text COLLATE utf8_bin,
  `stars_trip` bit(1) DEFAULT NULL,
  `feedback_guide` text COLLATE utf8_bin,
  `stars_guide` bit(1) DEFAULT NULL,
  `feedback_plan` text COLLATE utf8_bin,
  `stars_plan` bit(1) DEFAULT NULL,
  KEY `Индекс 1` (`id`),
  KEY `FK__users` (`user`),
  KEY `FK__excursion_plan` (`plan`),
  KEY `FK__excursion_trip` (`trip`),
  CONSTRAINT `FK__excursion_plan` FOREIGN KEY (`plan`) REFERENCES `excursion_plan` (`id`),
  CONSTRAINT `FK__excursion_trip` FOREIGN KEY (`trip`) REFERENCES `excursion_trip` (`id`),
  CONSTRAINT `FK__users` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы tourism.users_plan_trip: ~17 rows (приблизительно)
/*!40000 ALTER TABLE `users_plan_trip` DISABLE KEYS */;
INSERT INTO `users_plan_trip` (`id`, `user`, `plan`, `trip`, `feedback_trip`, `stars_trip`, `feedback_guide`, `stars_guide`, `feedback_plan`, `stars_plan`) VALUES
	(23, 69, 7, 37, NULL, NULL, NULL, NULL, NULL, NULL),
	(24, 69, 7, 39, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `users_plan_trip` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
