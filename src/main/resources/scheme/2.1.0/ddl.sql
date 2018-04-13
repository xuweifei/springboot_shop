/** 20171102 机器人语句信息 yst **/
DROP TABLE IF EXISTS robot_talk_info;
CREATE TABLE `robot_talk_info` (
  `info_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对话id',
  `talk_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '对话类型(1.机器人与主播 2.机器人与用户 3.机器人与机器人 4.机器人与自己）',
  `is_group` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否语句组(1是 2否)', 
  `sentence_type` smallint(6) DEFAULT '0' COMMENT '语句类型(1简单句 2疑问句 3感叹句 )',
  `sentence_purpose` smallint(6) NOT NULL DEFAULT '0' COMMENT '语句用途(1关注 2分享 3再见 4赞美 5辱骂 6吐槽 7爱慕)',
	`sex` smallint(6) DEFAULT '0' COMMENT '性别：0男；1女；2未知',
  `isgroup_num` int(11) NOT NULL DEFAULT '0' COMMENT '语句组关联编号',
  `talk_content` varchar(128) DEFAULT NULL COMMENT '语句内容',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`info_id`),
  KEY `index_info_talk_is_sentence2` (`info_id`,`talk_type`,`is_group`,`sentence_purpose`,`sentence_type`,`is_delete`) USING BTREE,
  KEY `index_info_talk_is_sentence1` (`info_id`,`talk_type`,`is_group`,`sentence_type`,`is_delete`) USING BTREE,
  KEY `index_info_talk_is` (`info_id`,`talk_type`,`is_group`,`is_delete`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器人对话信息表';

/** 20171107 机器人对话记录表 **/
DROP TABLE IF EXISTS robot_talk_record;
CREATE TABLE `robot_talk_record` (
  `record_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录id',
  `group_id` bigint(20) NOT NULL COMMENT '群组id',
  `user_id` bigint(20) NOT NULL COMMENT '主播id',
  `live_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `info_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对话id',
  `talk_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '对话类型(1.机器人与主播 2.机器人与用户 3.机器人与机器人 4.机器人与自己)',
  `is_group` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否语句组 (1是 2否)',
  `sentence_type` smallint(6) DEFAULT '0' COMMENT '语句类型(1简单句 2疑问句 3感叹句 )',
  `sentence_purpose` smallint(6) NOT NULL DEFAULT '0' COMMENT '语句用途(1关注 2分享 3再见 4赞美 5辱骂 6吐槽 7爱慕)',
  `sex` smallint(6) DEFAULT '0' COMMENT '性别：0男；1女；2未知',
  `isgroup_num` int(11) NOT NULL DEFAULT '0' COMMENT '语句组关联编号',
  `talk_content` varchar(128) DEFAULT NULL COMMENT '语句内容',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`record_id`),
  KEY `index_info_talk_is` (`info_id`,`talk_type`,`is_group`,`is_delete`) USING BTREE,
  KEY `index_info_talk_is_sentence1` (`info_id`,`talk_type`,`is_group`,`sentence_type`,`is_delete`) USING BTREE,
  KEY `index_info_talk_is_sentence2` (`info_id`,`talk_type`,`is_group`,`sentence_purpose`,`sentence_type`,`is_delete`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器人对话记录表';

/** 20171107 机器人注册信息列表 **/
DROP TABLE IF EXISTS `robot_reg_info`;
CREATE TABLE `robot_reg_info` (
  `robot_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '机器人用户ID',
  `robot_login_id` varchar(11) DEFAULT NULL COMMENT '机器人的登录ID',
  `robot_name` varchar(32) DEFAULT NULL COMMENT '机器人昵称',
  `robot_sex` smallint(6) DEFAULT '0' COMMENT '机器人性别',
  `robot_photo` varchar(255) DEFAULT NULL COMMENT '机器人照片头像',
  `robot_grade` int(11) DEFAULT '1' COMMENT '机器人等级',
  `is_usable` smallint(6) DEFAULT '2' COMMENT '机器人信息是否可用:1是，2否',
  `is_use` smallint(6) DEFAULT '2' COMMENT '机器人信息是否使用:1是，2否',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`robot_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器人注册信息资料列表';

/**  20171107 xwf 用户进入群组流水增加看播时长字段  **/
ALTER TABLE `live_group_info` ADD COLUMN `watch_second`  int(11) NOT NULL DEFAULT 0 COMMENT '看播时长(秒)' AFTER `is_enter`;

/**  20171201 scj 礼物manage增加字段  **/
ALTER TABLE `gift_config_info`  ADD COLUMN `gift_value_type` smallint(6) NOT NULL DEFAULT '1'  COMMENT '礼物价值类型,1:收费礼物,2:赠送礼物';
ALTER TABLE `gift_config_info`  ADD COLUMN `sort` int(11) NOT NULL DEFAULT '1'  COMMENT '排序';

/** 20171124 robot_config 机器人运行参数配置表 **/
DROP TABLE IF EXISTS `robot_config`;
CREATE TABLE `robot_config` (
  `config_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '参数id',
  `type` smallint(6) DEFAULT '0' COMMENT '分类(0：机器人指令数量配置；1:机器人启动初始化配置)',
  `patent_ename` varchar(32) NOT NULL DEFAULT '' COMMENT '大类英文名',
  `child_ename` varchar(32) NOT NULL DEFAULT '' COMMENT '小类英文名(唯一)',
  `cname` varchar(32) NOT NULL DEFAULT '' COMMENT '中文名',
  `default_val` varchar(150) NOT NULL DEFAULT '' COMMENT '默认属性值',
  `val` varchar(150) NOT NULL DEFAULT '' COMMENT '自定义属性值',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  `ctime` int(16) NOT NULL COMMENT '创建时间',
  `utime` int(16) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器人参数配置表';

/** 20171201 `act_assignment` 任务信息表 **/
DROP TABLE IF EXISTS `act_assignment`;
CREATE TABLE `act_assignment` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `assignment_id` bigint(11) DEFAULT NULL COMMENT '任务id',
  `assignment_head_url` varchar(150) DEFAULT NULL COMMENT '任务图片url',
  `assignment_type` smallint(2) DEFAULT NULL COMMENT '任务类型：1签到任务、2每日任务',
  `assignment_level` int(11) DEFAULT NULL COMMENT '阶段任务阶段数',
  `assignment_name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `assignment_reward` int(11) DEFAULT NULL COMMENT '奖励数量',
  `execute_times` int(11) DEFAULT NULL COMMENT '执行次数',
  `is_enable` smallint(6) DEFAULT NULL COMMENT '是否启用：1启用、2不启用',
  `is_activity` smallint(6) DEFAULT NULL COMMENT '是否是活动任务：1是、2不是',
  `sorts` bigint(20) DEFAULT NULL COMMENT '排序值',
  `remark` varchar(150) DEFAULT NULL COMMENT '任务描述',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(6) DEFAULT NULL COMMENT '是否删除：1正常、-1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务信息表';

/** 20171201 `act_assignment_atemp` 修改任务信息暂存表 **/
DROP TABLE IF EXISTS `act_assignment_atemp`;
CREATE TABLE `act_assignment_atemp` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `assignment_id` bigint(11) DEFAULT NULL COMMENT '任务id',
  `assignment_head_url` varchar(150) DEFAULT NULL COMMENT '任务图片url',
  `assignment_type` smallint(2) DEFAULT NULL COMMENT '任务类型：1签到任务、2每日任务',
  `assignment_level` int(11) DEFAULT NULL COMMENT '阶段任务阶段数',
  `assignment_name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `assignment_reward` int(11) DEFAULT NULL COMMENT '奖励数量',
  `execute_times` int(11) DEFAULT NULL COMMENT '完成任务要求次数',
  `is_enable` smallint(6) DEFAULT NULL COMMENT '是否启用：1启用、2不启用',
  `is_activity` smallint(6) DEFAULT NULL COMMENT '是否是活动任务：1是、2不是',
  `sorts` bigint(20) DEFAULT NULL COMMENT '排序值',
  `remark` varchar(150) DEFAULT NULL COMMENT '任务描述',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(6) DEFAULT NULL COMMENT '是否删除：1正常、-1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='修改任务信息暂存表';


/** 20171201 `act_assignment_complete` 每日任务完成情况表 **/
DROP TABLE IF EXISTS `act_assignment_complete`;
CREATE TABLE `act_assignment_complete` (
  `complete_id` bigint(20) NOT NULL COMMENT '主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `assignment_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `assignment_name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `assignment_type` smallint(6) DEFAULT NULL COMMENT '任务类型',
  `creatdate` int(11) DEFAULT NULL COMMENT '创建日期，格式：20171010',
  `assignment_level` int(11) DEFAULT NULL COMMENT '循环任务level',
  `continue_sign` int(11) DEFAULT NULL COMMENT '连续签到天数',
  `complete_rate` int(11) DEFAULT NULL COMMENT '任务完成进度',
  `requered_times` int(11) DEFAULT NULL COMMENT '任务要求次数/累计时长',
  `is_receivable` smallint(6) DEFAULT NULL COMMENT '是否可领取：1可领取、2不可领取、3已领取',
  `total_reward` bigint(20) DEFAULT NULL COMMENT '已领取豆子总数',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(6) DEFAULT NULL COMMENT '是否删除：1正常、-1删除',
  PRIMARY KEY (`complete_id`),
  UNIQUE KEY `user_createdate_key` (`user_id`,`creatdate`,`assignment_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每日任务完成情况表';


/** 20171201 `act_assignment_detail` 任务完成明细表 **/
DROP TABLE IF EXISTS `act_assignment_detail`;
CREATE TABLE `act_assignment_detail` (
  `detail_id` bigint(20) NOT NULL COMMENT '主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `assignment_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `anchor_id` bigint(20) DEFAULT NULL COMMENT '主播id',
  `createdate` int(11) DEFAULT NULL COMMENT '创建时间',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除：1正常、-1删除',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务完成明细表';

/** anchor_permit_log 2017.12.12 未更新 **/
ALTER TABLE anchor_permit_log ADD handlers varchar(32) COMMENT '处理者' AFTER log_type;