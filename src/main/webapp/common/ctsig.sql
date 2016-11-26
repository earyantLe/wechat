/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.45-log : Database - ctsig
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ctsig` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ctsig`;

/*Table structure for table `privilege` */

DROP TABLE IF EXISTS `privilege`;

CREATE TABLE `privilege` (
  `id` varchar(60) DEFAULT NULL,
  `privilegeName` varchar(60) DEFAULT NULL,
  `page` varchar(300) DEFAULT NULL,
  `pId` varchar(60) DEFAULT NULL,
  `isMenu` varchar(3) DEFAULT NULL,
  `privilegeCode` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `privilege` */

insert  into `privilege`(`id`,`privilegeName`,`page`,`pId`,`isMenu`,`privilegeCode`) values ('1421389853794','用户管理',NULL,'1','1',''),('1421392265041','用户新增','/oper/user/addUserInfo.do','1421389853794','0','userAdd'),('1421392276376','用户删除','/oper/user/deleteUserInfo.do','1421389853794','0','userDel'),('1423646216090','有权限展示',NULL,'1','1',''),('1423646265882','有部分权限展示','/oper/user/topermissionInfo.do','1423646216090','1','permission'),('1429069621021','测试',NULL,'1','1',NULL),('1429069669413','测试','/oper/user/tolistUserInfo.do','1429069621021','1','test'),('1459480695583','用户列表','/oper/user/tolistUserInfo.do','1421389853794','1','userlist'),('1459480762905','权限管理','/oper/privilege/toprivilegeInfo.do','1421389853794','1','privilegelist'),('1459480797329','角色管理','/oper/role/toListRoleInfo.do','1421389853794','1','rolelsit'),('1460354528774','菜单1460354528774',NULL,'1','0',NULL);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` double DEFAULT NULL,
  `roleName` varchar(60) DEFAULT NULL,
  `roleCode` varchar(60) DEFAULT NULL,
  `roleDesc` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`roleName`,`roleCode`,`roleDesc`) values (19,'管理员角色','admin','admin'),(25,'普通角色','nomal','nomal'),(26,'部分权限角色','permission','permission');

/*Table structure for table `role_privilege` */

DROP TABLE IF EXISTS `role_privilege`;

CREATE TABLE `role_privilege` (
  `id` double DEFAULT NULL,
  `pId` varchar(60) DEFAULT NULL,
  `privilegeId` varchar(60) DEFAULT NULL,
  `roleId` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_privilege` */

insert  into `role_privilege`(`id`,`pId`,`privilegeId`,`roleId`) values (130,'1423646216090','1423646265882',26),(131,'1421389853794','1421392265041',19),(132,'1421389853794','1421392276376',19),(133,'1421389853794','1459480695583',19),(134,'1421389853794','1459480762905',19),(135,'1421389853794','1459480797329',19),(136,'1423646216090','1423646265882',19),(137,'1429069621021','1429069669413',19),(138,'','',25);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` double DEFAULT NULL,
  `loginname` varchar(60) DEFAULT NULL,
  `loginpwd` varchar(600) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `sex` varchar(3) DEFAULT NULL,
  `isAdmin` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`loginname`,`loginpwd`,`username`,`sex`,`isAdmin`) values (11,'admin','81dc9bdb52d04dc20036dbd8313ed055','admin','0','1'),(12,'nomal','81dc9bdb52d04dc20036dbd8313ed055','普通角色','0','0'),(13,'permission','81dc9bdb52d04dc20036dbd8313ed055','有部分权限','0','0'),(14,'cs','81dc9bdb52d04dc20036dbd8313ed055','测试','0','0');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` double DEFAULT NULL,
  `roleId` double DEFAULT NULL,
  `userId` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`roleId`,`userId`) values (3,25,6),(4,19,9),(5,26,10),(6,25,12),(7,26,13),(8,19,11);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
