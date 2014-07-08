/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : localhost
 Source Database       : food_logistics

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : utf-8

 Date: 07/07/2014 19:15:58 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `fl_comment`
-- ----------------------------
DROP TABLE IF EXISTS `fl_comment`;
CREATE TABLE `fl_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `poster` decimal(19,2) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `merchant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_73ry757gnp917kfhfff5xub63` (`createdBy_id`),
  KEY `FK_s787jfs84r261scv1wsnptey6` (`lastModifiedBy_id`),
  KEY `FK_h9q081m99dk85gn5ipljsjit0` (`merchant_id`),
  CONSTRAINT `FK_h9q081m99dk85gn5ipljsjit0` FOREIGN KEY (`merchant_id`) REFERENCES `fl_merchant` (`id`),
  CONSTRAINT `FK_73ry757gnp917kfhfff5xub63` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_s787jfs84r261scv1wsnptey6` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_contact`
-- ----------------------------
DROP TABLE IF EXISTS `fl_contact`;
CREATE TABLE `fl_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `receipt` varchar(255) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_39mj6mhlbdpffmf9nbok3d36n` (`createdBy_id`),
  KEY `FK_9iis4ey2l23pfer7v90x2oosl` (`lastModifiedBy_id`),
  KEY `FK_s8q4cjw5svhpis4yem3w72em6` (`profile_id`),
  CONSTRAINT `FK_s8q4cjw5svhpis4yem3w72em6` FOREIGN KEY (`profile_id`) REFERENCES `fl_profile` (`id`),
  CONSTRAINT `FK_39mj6mhlbdpffmf9nbok3d36n` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_9iis4ey2l23pfer7v90x2oosl` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_forget`
-- ----------------------------
DROP TABLE IF EXISTS `fl_forget`;
CREATE TABLE `fl_forget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `request_time` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tr6f44xb3b9cwspyts1yk7hwk` (`createdBy_id`),
  KEY `FK_4euw2v3q0hdnc2c7ksclkik8h` (`lastModifiedBy_id`),
  CONSTRAINT `FK_4euw2v3q0hdnc2c7ksclkik8h` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_tr6f44xb3b9cwspyts1yk7hwk` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_group`
-- ----------------------------
DROP TABLE IF EXISTS `fl_group`;
CREATE TABLE `fl_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6j7bptqre9si4fbe1m9lyp9h9` (`name`),
  KEY `FK_5y5lwdtyvbgy1t2kvlbg99wu0` (`createdBy_id`),
  KEY `FK_ecj9ojtbcfkmkg9io2fbt86s1` (`lastModifiedBy_id`),
  CONSTRAINT `FK_ecj9ojtbcfkmkg9io2fbt86s1` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_5y5lwdtyvbgy1t2kvlbg99wu0` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `fl_group`
-- ----------------------------
BEGIN;
INSERT INTO `fl_group` VALUES ('1', '2014-06-30 22:13:09', '2014-06-30 22:13:11', b'0', 'Unknown', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `fl_group_member`
-- ----------------------------
DROP TABLE IF EXISTS `fl_group_member`;
CREATE TABLE `fl_group_member` (
  `group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`,`user_id`),
  KEY `FK_siguhw96mtudkju20rwg2488t` (`user_id`),
  CONSTRAINT `FK_2t46ns8wxtdmsgv65tjb0gbt4` FOREIGN KEY (`group_id`) REFERENCES `fl_group` (`id`),
  CONSTRAINT `FK_siguhw96mtudkju20rwg2488t` FOREIGN KEY (`user_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `fl_group_role`;
CREATE TABLE `fl_group_role` (
  `group_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`,`role_id`),
  KEY `FK_6jt6rgquu2x2tsc9409qemm8` (`role_id`),
  CONSTRAINT `FK_p3octt20t5g444p1til36ucpe` FOREIGN KEY (`group_id`) REFERENCES `fl_group` (`id`),
  CONSTRAINT `FK_6jt6rgquu2x2tsc9409qemm8` FOREIGN KEY (`role_id`) REFERENCES `fl_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_image`
-- ----------------------------
DROP TABLE IF EXISTS `fl_image`;
CREATE TABLE `fl_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `content` longblob,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_amf3wxkd761lfquql0mi5ghas` (`createdBy_id`),
  KEY `FK_nersxt7r4952p0uhtj7luff7l` (`lastModifiedBy_id`),
  CONSTRAINT `FK_nersxt7r4952p0uhtj7luff7l` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_amf3wxkd761lfquql0mi5ghas` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `fl_merchant`;
CREATE TABLE `fl_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `business` varchar(255) DEFAULT NULL,
  `consume` decimal(10,2) DEFAULT NULL,
  `delivery_fee` decimal(10,2) DEFAULT NULL,
  `delivery_time` int(11) DEFAULT NULL,
  `location` longtext,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_613esblufasimy7uywsxpbp4k` (`createdBy_id`),
  KEY `FK_5y3gwt06dk6qiqpiq45e5ur2t` (`lastModifiedBy_id`),
  KEY `FK_drpes7grrsbfnsgfnig0097fk` (`user_id`),
  CONSTRAINT `FK_drpes7grrsbfnsgfnig0097fk` FOREIGN KEY (`user_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_5y3gwt06dk6qiqpiq45e5ur2t` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_613esblufasimy7uywsxpbp4k` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_order`
-- ----------------------------
DROP TABLE IF EXISTS `fl_order`;
CREATE TABLE `fl_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `addition_info` varchar(255) NOT NULL,
  `delivery_fee` decimal(10,2) DEFAULT NULL,
  `meetTime` datetime NOT NULL,
  `order_number` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `submit_time` datetime DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `cavalier_id` bigint(20) DEFAULT NULL,
  `contact_id` bigint(20) NOT NULL,
  `merchant_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j0qf08wryb9lbiledri0geqbj` (`order_number`),
  KEY `FK_d1e0x3d7hx5oeq6jerlqou65c` (`createdBy_id`),
  KEY `FK_ru7447v0tmrmx7i8vae0p0kjs` (`lastModifiedBy_id`),
  KEY `FK_9s73jmkwfu36x21t82p3rf0hn` (`cavalier_id`),
  KEY `FK_a5kskqjks05khx08i2tulve8r` (`contact_id`),
  KEY `FK_nsptqagm5tetycrj0oy0ysf04` (`merchant_id`),
  KEY `FK_6npronr9hb025jpbdn7g4372g` (`user_id`),
  CONSTRAINT `FK_6npronr9hb025jpbdn7g4372g` FOREIGN KEY (`user_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_9s73jmkwfu36x21t82p3rf0hn` FOREIGN KEY (`cavalier_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_a5kskqjks05khx08i2tulve8r` FOREIGN KEY (`contact_id`) REFERENCES `fl_contact` (`id`),
  CONSTRAINT `FK_d1e0x3d7hx5oeq6jerlqou65c` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_nsptqagm5tetycrj0oy0ysf04` FOREIGN KEY (`merchant_id`) REFERENCES `fl_merchant` (`id`),
  CONSTRAINT `FK_ru7447v0tmrmx7i8vae0p0kjs` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_persistent_token`
-- ----------------------------
DROP TABLE IF EXISTS `fl_persistent_token`;
CREATE TABLE `fl_persistent_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `ip_address` varchar(39) DEFAULT NULL,
  `token_date` datetime DEFAULT NULL,
  `token_value` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g7e0cy5midkfvbwg86b7ci8c1` (`createdBy_id`),
  KEY `FK_slh46kk5jiv7ardw4t5tdhddq` (`lastModifiedBy_id`),
  KEY `FK_4q0fpv18m31qbqjq5w10d8vfu` (`user_id`),
  CONSTRAINT `FK_4q0fpv18m31qbqjq5w10d8vfu` FOREIGN KEY (`user_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_g7e0cy5midkfvbwg86b7ci8c1` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_slh46kk5jiv7ardw4t5tdhddq` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_product`
-- ----------------------------
DROP TABLE IF EXISTS `fl_product`;
CREATE TABLE `fl_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `available` bit(1) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `merchant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ii7dggb1iqar8a5uibmrclfuq` (`createdBy_id`),
  KEY `FK_sui80tsc67sgms6dqpq8blh89` (`lastModifiedBy_id`),
  KEY `FK_lxo52l3f78f6lwomubhyityyy` (`merchant_id`),
  CONSTRAINT `FK_lxo52l3f78f6lwomubhyityyy` FOREIGN KEY (`merchant_id`) REFERENCES `fl_merchant` (`id`),
  CONSTRAINT `FK_ii7dggb1iqar8a5uibmrclfuq` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_sui80tsc67sgms6dqpq8blh89` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_profile`
-- ----------------------------
DROP TABLE IF EXISTS `fl_profile`;
CREATE TABLE `fl_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `display_name` varchar(48) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `vip` bit(1) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_eq8dgaqrvvjr3r5blgla3med2` (`createdBy_id`),
  KEY `FK_apuper619ytn2uebh1hk95kr6` (`lastModifiedBy_id`),
  CONSTRAINT `FK_apuper619ytn2uebh1hk95kr6` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_eq8dgaqrvvjr3r5blgla3med2` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_purchase_item`
-- ----------------------------
DROP TABLE IF EXISTS `fl_purchase_item`;
CREATE TABLE `fl_purchase_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `purchase_number` int(11) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e8mabh8vuuxdyy02hltkskfil` (`createdBy_id`),
  KEY `FK_d4ctjq8bbxa0jqv21hnca44s4` (`lastModifiedBy_id`),
  KEY `FK_1lxcf5gxwmxpqfhkk0xjyl494` (`order_id`),
  KEY `FK_q75n1hre27g5gxwlvllogseoe` (`product_id`),
  CONSTRAINT `FK_q75n1hre27g5gxwlvllogseoe` FOREIGN KEY (`product_id`) REFERENCES `fl_product` (`id`),
  CONSTRAINT `FK_1lxcf5gxwmxpqfhkk0xjyl494` FOREIGN KEY (`order_id`) REFERENCES `fl_order` (`id`),
  CONSTRAINT `FK_d4ctjq8bbxa0jqv21hnca44s4` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_e8mabh8vuuxdyy02hltkskfil` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_role`
-- ----------------------------
DROP TABLE IF EXISTS `fl_role`;
CREATE TABLE `fl_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j2pcjl2bwy9wu1acpnhcdoubr` (`name`),
  KEY `FK_4p9j8umvgs7lfj2e4qv9g4vyi` (`createdBy_id`),
  KEY `FK_s5sd368f3mvqjenpc1ctr1jrh` (`lastModifiedBy_id`),
  CONSTRAINT `FK_s5sd368f3mvqjenpc1ctr1jrh` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_4p9j8umvgs7lfj2e4qv9g4vyi` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `fl_role`
-- ----------------------------
BEGIN;
INSERT INTO `fl_role` VALUES ('1', '2014-06-30 22:09:11', '2014-06-30 22:09:14', b'1', 'USER', '1', '1'), ('2', '2014-06-30 22:11:02', '2014-06-30 22:11:06', b'1', 'CAVALIER', '1', '1'), ('3', '2014-06-30 22:12:02', '2014-06-30 22:12:04', b'1', 'MERCHANT', '1', '1'), ('4', '2014-06-30 22:12:31', '2014-06-30 22:12:33', b'1', 'ADMIN', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `fl_shopping_car`
-- ----------------------------
DROP TABLE IF EXISTS `fl_shopping_car`;
CREATE TABLE `fl_shopping_car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bws9nyjmy3dnfytvl92c7esce` (`createdBy_id`),
  KEY `FK_fxj8u2a96pbcbcg36o81ukw0d` (`lastModifiedBy_id`),
  CONSTRAINT `FK_fxj8u2a96pbcbcg36o81ukw0d` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_bws9nyjmy3dnfytvl92c7esce` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_shopping_car_item`
-- ----------------------------
DROP TABLE IF EXISTS `fl_shopping_car_item`;
CREATE TABLE `fl_shopping_car_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `shopping_car_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7v0jgqc11s6dngkqs31eykka` (`createdBy_id`),
  KEY `FK_f2fiqkr88i4uvmaetp8pjp77t` (`lastModifiedBy_id`),
  KEY `FK_gfckow605qcwluksu2x53j4by` (`product_id`),
  KEY `FK_fq27nito078iryt0kr0f8e108` (`shopping_car_id`),
  CONSTRAINT `FK_fq27nito078iryt0kr0f8e108` FOREIGN KEY (`shopping_car_id`) REFERENCES `fl_shopping_car` (`id`),
  CONSTRAINT `FK_7v0jgqc11s6dngkqs31eykka` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_f2fiqkr88i4uvmaetp8pjp77t` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_gfckow605qcwluksu2x53j4by` FOREIGN KEY (`product_id`) REFERENCES `fl_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fl_user`
-- ----------------------------
DROP TABLE IF EXISTS `fl_user`;
CREATE TABLE `fl_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `password` varchar(128) NOT NULL,
  `user_number` varchar(21) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  `lastModifiedBy_id` bigint(20) DEFAULT NULL,
  `profile_id` bigint(20) DEFAULT NULL,
  `shopping_car_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tpr0rliv2klhl9gkbcbexi8rg` (`email`),
  KEY `FK_qn80qgtj5yp6msgv3uxxps71e` (`createdBy_id`),
  KEY `FK_bskcmfy9m5tc0it93uyu0ix5` (`lastModifiedBy_id`),
  KEY `FK_ba6mhmee8bso4g4unf8mon0hh` (`profile_id`),
  KEY `FK_8xlc9fpd55smufy9gr6bwknke` (`shopping_car_id`),
  CONSTRAINT `FK_8xlc9fpd55smufy9gr6bwknke` FOREIGN KEY (`shopping_car_id`) REFERENCES `fl_shopping_car` (`id`),
  CONSTRAINT `FK_ba6mhmee8bso4g4unf8mon0hh` FOREIGN KEY (`profile_id`) REFERENCES `fl_profile` (`id`),
  CONSTRAINT `FK_bskcmfy9m5tc0it93uyu0ix5` FOREIGN KEY (`lastModifiedBy_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_qn80qgtj5yp6msgv3uxxps71e` FOREIGN KEY (`createdBy_id`) REFERENCES `fl_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `fl_user`
-- ----------------------------
BEGIN;
INSERT INTO `fl_user` VALUES ('1', '2014-06-30 22:09:44', '2014-06-30 22:09:47', 'SYSTEM', b'1', '', 'SYSTEM', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `fl_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `fl_user_role`;
CREATE TABLE `fl_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_3eqo6l8fhmospiybl5t672h6e` (`role_id`),
  CONSTRAINT `FK_s8webjxwqjdhbl2pp8ib6wnp0` FOREIGN KEY (`user_id`) REFERENCES `fl_user` (`id`),
  CONSTRAINT `FK_3eqo6l8fhmospiybl5t672h6e` FOREIGN KEY (`role_id`) REFERENCES `fl_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
