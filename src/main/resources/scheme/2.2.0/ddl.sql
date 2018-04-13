/** 20171211 活动配置表 yst **/
CREATE TABLE `act_config` (
  `config_id` bigint(20) NOT NULL COMMENT '活动配置id',
  `activity_type` smallint(4) DEFAULT NULL COMMENT '活动类型 0龙猫豆抽奖 1领红包 2送红包 4双蛋活动 5充值送',
  `name` varchar(255) NOT NULL COMMENT '活动名称',
  `content` varchar(1024) DEFAULT NULL COMMENT '活动内容',
  `pic_url` varchar(255) DEFAULT NULL COMMENT '活动图片URL',
  `reward_type` smallint(4) DEFAULT '3' COMMENT '活动奖励类型 1满减 2满送 3抽奖 4其他',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` smallint(4) NOT NULL DEFAULT '2' COMMENT '开关状态：1开 2关',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除：1正常、-1删除',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `uni_acticitytype` (`activity_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动配置表'

-- 新建累计押注表表 hjy 2017年12月12日15:09:46
CREATE TABLE `guess_accumulative_bets` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID，主键',
  `bean_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计的数量',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='累计押注表'

-- act_extract_record（用户抽奖记录）表，增加一个字段(create_time)，用于保存添加记录的时间（年月日） hjy 2017年12月12日15:09:46
	ALTER TABLE act_extract_record ADD COLUMN create_time varchar(32) DEFAULT NULL COMMENT '创建时间yyyymmdd' AFTER activity_type;
	





