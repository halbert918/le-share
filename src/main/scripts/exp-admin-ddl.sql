/** db_rdm */
CREATE TABLE `t_exp_center_main` (
  `id` char(36) NOT NULL DEFAULT '',
  `title` text COMMENT '标题',
  `download_style` text,
  `exp_style_url` varchar(255) DEFAULT NULL COMMENT 'wap体验方式对应的URL',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '发布时间',
  `background` text,
  `platform_desc` text COMMENT '平台描述',
  `feature` text COMMENT '特性',
  `feedback_style` text COMMENT '反馈人',
  `content` text,
  `feedback_user` varchar(200) DEFAULT NULL,
  `logo_id` char(36) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '-2' COMMENT '-1表示禁用，-2表示草稿，1表示正常, 2表示new,3审批中（level为0）,4体验已过期',
  `product_id` char(36) NOT NULL DEFAULT '',
  `notify_style` varchar(20) DEFAULT '0' COMMENT '体验提醒的方式（短信、RTX和邮件），目前只有短信，1代表发送短信，0代表不发送',
  `secret` tinyint(4) DEFAULT '0' COMMENT '是否保密或私有，1代表保密，0代表公开',
  `level` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0表示BU级别体验,2代表产品级体验',
  `globalExpNum` varchar(20) DEFAULT NULL,
  `article_id` char(36) DEFAULT NULL,
  `creator` varchar(100) NOT NULL DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `start_time` varchar(50) DEFAULT '' COMMENT '更新时间',
  `end_time` varchar(100) DEFAULT '',
  `wapId` varchar(20) DEFAULT NULL COMMENT 'wapId',
  `syncKM` varchar(10) DEFAULT NULL,
  `exp_type` varchar(30) DEFAULT '' COMMENT '''fromCiPackage：从ci拉取报.另外两种类型，保持以前方式',
  `defaultExp` int(1) NOT NULL DEFAULT '0' COMMENT '是否为产品缺省体验,与status字段共同决定',
  `download_times` int(11) DEFAULT '0',
  `download_users` int(10) DEFAULT NULL,
  `quality` int(1) DEFAULT '1' COMMENT '1:稳定  2：测试',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`),
  KEY `index_productId` (`product_id`),
  KEY `index_level` (`level`),
  KEY `index_creator` (`creator`),
  KEY `index_secret` (`secret`),
  KEY `index_creatTime` (`create_time`),
  KEY `index_updateTime` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_exp_center` (
  `exp_id` char(36) NOT NULL DEFAULT '',
  `project_id` varchar(36) NOT NULL,
  `platform_id` varchar(36) NOT NULL,
  `bulletin_f_id` int(11) NOT NULL,
  `feature` text NOT NULL,
  `owner` varchar(200) NOT NULL,
  `release_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '发布时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`exp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_exp_center_build` (
  `package_id` char(36) NOT NULL DEFAULT '',
  `package_name` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0' COMMENT '是否可用，0代表可用，-1代表被删除',
  `package_type` tinyint(4) DEFAULT '0' COMMENT '0表示手机，1表示url，2表示pc,3表示ci获取安装包',
  `exp_id` char(36) DEFAULT NULL,
  `wap_build_id` char(20) DEFAULT NULL,
  `hudson_build_id` char(36) DEFAULT NULL,
  `file_path` varchar(300) DEFAULT NULL COMMENT '文件在ci处的相对路径',
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `has_parse_package` int(1) DEFAULT '0' COMMENT '是否已经解析入库',
  PRIMARY KEY (`package_id`),
  KEY `index_expId` (`exp_id`),
  KEY `index_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_exp_group_user` (
  `id` char(36) NOT NULL,
  `level` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0代表全局体验组，1代表缺省体验群，2代表产品体验群',
  `enable` enum('1','0') NOT NULL DEFAULT '1' COMMENT '0代表不可用，1代表可用',
  `name` varchar(200) NOT NULL,
  `comment` text,
  `user_ids` text,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_exp_product_group` (
  `product_id` varchar(36) NOT NULL,
  `group_user_id` varchar(100) NOT NULL,
  PRIMARY KEY (`product_id`,`group_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
