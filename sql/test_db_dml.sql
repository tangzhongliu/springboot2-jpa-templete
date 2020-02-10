# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.5-10.3.14-MariaDB)
# Database: test_db
# Generation Time: 2019-11-19 06:52:44 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table sys_menu
# ------------------------------------------------------------

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `create_time`, `update_time`, `create_user`, `update_user`, `status`, `version`, `menu_url`, `menu_path`, `menu_component`, `menu_name`, `menu_title`, `menu_icon`, `menu_parent_id`)
VALUES
	(1,'2019-11-11 00:00:00','2019-11-11 00:00:00','admin','admin',1,0,'/sys/**','a','b','sys','系统','sys',NULL),
	(2,'2019-11-11 01:00:00','2019-11-11 01:00:00','admin','admin',1,0,'/sys/users/**','c','d','user','用户','user',1);

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role
# ------------------------------------------------------------

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `create_time`, `update_time`, `create_user`, `update_user`, `status`, `version`, `role_name`, `role_remark`)
VALUES
	(1,'2019-11-11 00:00:00','2019-11-11 00:00:00','admin','admin',1,0,'ROLE_ADMIN','管理员'),
	(2,'2019-11-11 01:00:00','2019-11-11 01:00:00','admin','admin',1,0,'ROLE_GUEST','访客');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_menu_rel
# ------------------------------------------------------------

LOCK TABLES `sys_role_menu_rel` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_rel` DISABLE KEYS */;

INSERT INTO `sys_role_menu_rel` (`id`, `create_time`, `update_time`, `create_user`, `update_user`, `status`, `version`, `role_id`, `menu_id`)
VALUES
	(1,'2019-11-11 00:00:00','2019-11-11 00:00:00','admin','admin',1,0,1,1),
	(2,'2019-11-11 01:00:00','2019-11-11 01:00:00','admin','admin',1,0,1,2),
	(3,'2019-11-11 02:00:00','2019-11-11 02:00:00','admin','admin',1,0,2,1);

/*!40000 ALTER TABLE `sys_role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `create_time`, `update_time`, `create_user`, `update_user`, `status`, `version`, `user_name`, `user_password`)
VALUES
	(1,'2019-11-11 00:00:00','2019-11-11 00:00:00','admin','admin',1,0,'admin','$2a$10$WeUHFxn/Rdk.kC0bynQAvuQmuXXyIHpIrgX6aZlgn5LkovvLidHay'),
	(2,'2019-11-11 01:00:00','2019-11-11 01:00:00','admin','admin',1,0,'guest','$2a$10$WeUHFxn/Rdk.kC0bynQAvuQmuXXyIHpIrgX6aZlgn5LkovvLidHay');

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user_role_rel
# ------------------------------------------------------------

LOCK TABLES `sys_user_role_rel` WRITE;
/*!40000 ALTER TABLE `sys_user_role_rel` DISABLE KEYS */;

INSERT INTO `sys_user_role_rel` (`id`, `create_time`, `update_time`, `create_user`, `update_user`, `status`, `version`, `user_id`, `role_id`)
VALUES
	(1,'2019-11-11 00:00:00','2019-11-11 00:00:00','admin','admin',1,0,1,1),
	(2,'2019-11-11 01:00:00','2019-11-11 01:00:00','admin','admin',1,0,1,2),
	(3,'2019-11-11 02:00:00','2019-11-11 02:00:00','admin','admin',1,0,2,1);

/*!40000 ALTER TABLE `sys_user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
