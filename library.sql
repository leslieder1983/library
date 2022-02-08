/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : library

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2022-02-08 17:04:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `desription` varchar(255) NOT NULL,
  `content` longtext,
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('30', '26', '计算机学院缺书啦', '数据结构缺书500本', '数据结构（C语言版）', '2022-02-07 15:39:02', '0');
INSERT INTO `blog` VALUES ('31', '26', '计算机学院缺书啦', '缺书200本', '程序设计实践教程：C++语言版', '2022-02-08 15:43:54', '0');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) DEFAULT NULL COMMENT '书名',
  `book_kind` int(255) DEFAULT NULL COMMENT '教材分类',
  `book_price` decimal(10,2) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `isbn` varchar(255) DEFAULT NULL COMMENT '书号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `inventory` bigint(255) DEFAULT NULL COMMENT '库存量',
  `press` varchar(255) DEFAULT NULL COMMENT '出版社',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '程序设计实践教程：C++语言版', '2', '49.00', '苏小红', '100', '2022-01-30 17:16:29', '200', '机械工业出版社');
INSERT INTO `book` VALUES ('2', '基础化学实验', '3', '32.00', '范晖', '978-7-305-15483-6', '2022-01-30 17:19:19', '250', '南京大学出版社');
INSERT INTO `book` VALUES ('3', '马克思主义基本原理概论(2018年版)', '4', '23.00', '赵丽颖', '9787040494792', '2022-01-30 17:24:09', '150', '高等教育出版社');
INSERT INTO `book` VALUES ('4', '数据结构（C语言版）', '2', '49.80', '严蔚敏 ', '9787115576668', '2022-01-30 17:24:58', '320', ' 人民邮电出版社');
INSERT INTO `book` VALUES ('5', '小鲤鱼历险记', '1', null, 'kriswu', null, '2022-02-03 15:32:28', null, null);
INSERT INTO `book` VALUES ('6', '小鲤鱼险记', '1', null, null, null, '2022-02-03 15:26:08', null, null);
INSERT INTO `book` VALUES ('7', 'aaaa', '2', '32.00', '15402125691', '122545555', '2022-02-03 15:26:17', '20', null);
INSERT INTO `book` VALUES ('8', 'cc', '1', null, null, null, '2022-02-03 15:27:25', null, null);
INSERT INTO `book` VALUES ('9', '215211', '1', '52.00', 'fdgs', 'af', '2022-02-06 17:43:37', '22', '中医出版社');
INSERT INTO `book` VALUES ('10', 'adasd', '3', '50.00', 'sa', 'adasd', '2022-02-06 17:46:19', '300', 'aaaa');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `classes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '人文社科');
INSERT INTO `category` VALUES ('2', '计算机');
INSERT INTO `category` VALUES ('3', '经济管理');
INSERT INTO `category` VALUES ('4', '人文心理');
INSERT INTO `category` VALUES ('5', '工业技术');
INSERT INTO `category` VALUES ('6', '数理化');
INSERT INTO `category` VALUES ('7', '环境资源');
INSERT INTO `category` VALUES ('8', '机械材料');
INSERT INTO `category` VALUES ('12', '政治思想');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `count` bigint(20) NOT NULL DEFAULT '1',
  `sum` bigint(20) DEFAULT NULL,
  `agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '程序设计', '2022-01-29 18:08:35', '1', '230', 'kyle');
INSERT INTO `orders` VALUES ('5', '3', '2022-02-06 20:42:26', '5', '115', 'niko');
INSERT INTO `orders` VALUES ('6', '2', '2022-02-06 20:42:52', '2', '64', 'niko');
INSERT INTO `orders` VALUES ('7', '程序设计实践教程：C++语言版', '2022-02-06 20:45:24', '3', '147', 'niko');
INSERT INTO `orders` VALUES ('8', '基础化学实验', '2022-02-07 14:31:02', '4', '128', 'niko');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` longtext,
  `phone` varchar(11) DEFAULT NULL,
  `sexy` char(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'marker', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', '1145712302@qq.com', '96e79218965eb72c92a549dd5a330112', '2022-01-28 17:22:10', '天不生我李淳罡', '12334232124', '0');
INSERT INTO `user` VALUES ('13', '1234567', null, 'aqdd@qq.com', '92f4189e4333eb6fecbd270c8e95a6a4', '2022-01-24 20:57:43', null, '123434424', '1');
INSERT INTO `user` VALUES ('25', 'aay', null, '11234553232@qq.com', '9336ebf25087d91c818ee6e9ec29f8c1', '2022-01-25 17:56:30', null, 'xx', '0');
INSERT INTO `user` VALUES ('26', 'niko', null, '1145242434@qq.com', '827ccb0eea8a706c4c34a16891f84e7b', '2022-02-08 16:31:04', '天不生我李淳罡', '13567861235', '0');
INSERT INTO `user` VALUES ('29', 'wuqian', null, 'aaa', 'e10adc3949ba59abbe56e057f20f883e', '2022-01-29 18:31:16', null, '134664353', '0');
INSERT INTO `user` VALUES ('32', 'kriswu', null, '1245871240@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '2022-01-29 18:35:55', null, '18681262934', '0');
INSERT INTO `user` VALUES ('34', 'gao', null, '126274200@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '2022-02-06 16:11:15', null, '18452141320', '1');
INSERT INTO `user` VALUES ('35', '吴倩', null, null, 'e10adc3949ba59abbe56e057f20f883e', '2022-02-08 16:41:10', null, null, '0');
INSERT INTO `user` VALUES ('36', '123', null, null, 'e10adc3949ba59abbe56e057f20f883e', '2022-02-08 16:50:54', null, null, '0');
