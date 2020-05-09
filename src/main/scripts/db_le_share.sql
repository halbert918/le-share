/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 127.0.0.1:3306
 Source Schema         : db_rock

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 09/05/2020 17:07:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `author_user_id` bigint(20) NOT NULL COMMENT '作者ID',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `like_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '浏览数',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '文章类型 0:默认',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0:正常 1:删除 2:待开放',
  `is_hot` tinyint(1) DEFAULT '0' COMMENT '是否热门文章 0:普通 1:热门',
  `is_swiper` tinyint(1) DEFAULT '0' COMMENT '是否首页滑动0:否 1:是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_author_user_id` (`author_user_id`),
  KEY `idx_title` (`title`),
  KEY `idx_type_swiper` (`type`,`is_swiper`,`status`),
  FULLTEXT KEY `idx_content` (`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_article_image
-- ----------------------------
DROP TABLE IF EXISTS `t_article_image`;
CREATE TABLE `t_article_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `art_id` bigint(20) NOT NULL COMMENT '文章ID',
  `image_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0 正常 1删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_art_id_status` (`art_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章中的图片信息';

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL COMMENT '评论ID',
  `art_id` bigint(20) NOT NULL COMMENT '文章ID',
  `content` varchar(256) NOT NULL COMMENT '评论内容',
  `comment_user_id` bigint(20) DEFAULT NULL COMMENT '评论用户ID',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `reply_count` int(11) DEFAULT '0' COMMENT '回复数量',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0 正常 1 删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_art_id_status` (`art_id`,`status`),
  KEY `idx_user_id` (`comment_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

-- ----------------------------
-- Table structure for t_fans
-- ----------------------------
DROP TABLE IF EXISTS `t_fans`;
CREATE TABLE `t_fans` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '当前用户ID',
  `fans_user_id` bigint(20) NOT NULL COMMENT '粉丝用户ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态0 正常 1 删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`,`fans_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `followd_user_id` bigint(20) NOT NULL COMMENT '关注对象user_id',
  `user_id` bigint(20) NOT NULL COMMENT '当前用户',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0 关注 1 取消关注(取消关注暂时物理删除，防止关注表过大)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`,`followd_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关注表';

-- ----------------------------
-- Table structure for t_like_user
-- ----------------------------
DROP TABLE IF EXISTS `t_like_user`;
CREATE TABLE `t_like_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `target_id` bigint(20) NOT NULL COMMENT '点赞对象ID（文章、评论、回复）',
  `like_user_id` bigint(32) NOT NULL COMMENT '点赞用户',
  `like_type` tinyint(1) DEFAULT '0' COMMENT '点赞类型 0 文章 1 文章评论 2 评论回复',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0:正常 1:删除',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_target_id` (`target_id`,`status`),
  KEY `idx_user_id` (`like_user_id`),
  KEY `idx_type` (`like_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件点赞用户列表';

-- ----------------------------
-- Table structure for t_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `id` bigint(20) NOT NULL COMMENT '回复ID',
  `art_id` bigint(20) NOT NULL COMMENT '文章ID',
  `comment_id` bigint(20) DEFAULT '0' COMMENT '评论ID',
  `reply_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '回复类型 0 回复评论 1 回复某条回复',
  `reply_id` bigint(20) DEFAULT '0' COMMENT '回复ID',
  `content` varchar(256) NOT NULL COMMENT '回复内容',
  `from_user_id` bigint(20) NOT NULL COMMENT '回复用户',
  `to_user_id` bigint(20) NOT NULL COMMENT '被回复用户',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `status` tinyint(1) NOT NULL COMMENT '状态 0 正常 1 删除',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`from_user_id`),
  KEY `idx_comment_id` (`comment_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='回复表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `nick_name` varchar(32) NOT NULL COMMENT '用户别名',
  `avatar_url` varchar(128) DEFAULT '' COMMENT '用户头像地址',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别 0：未知、1：男、2：女\n性别 0：未知、1：男、2：女\n性别 0：未知、1：男、2：女\n性别 0：未知、1：男、2：女',
  `province` varchar(16) DEFAULT NULL COMMENT '省份',
  `city` varchar(16) DEFAULT NULL COMMENT '市',
  `country` varchar(32) DEFAULT NULL COMMENT '区',
  `openid` varchar(64) DEFAULT NULL COMMENT '用户唯一标识',
  `session_key` varchar(64) DEFAULT NULL COMMENT '会话密钥',
  `unionid` varchar(64) DEFAULT NULL COMMENT '用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回',
  `status` tinyint(1) DEFAULT '0' COMMENT '0:正常 1:禁用',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
