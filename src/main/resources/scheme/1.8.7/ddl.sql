/** guess_amount_flow  yst**/
ALTER TABLE guess_amount_flow ADD amount_type smallint(8) DEFAULT NULL COMMENT '1.庄家注池冻结 2.用户本金返还 3.用户盈利 4.庄家本金返还 5.庄家盈利 6.平台手续费  7.主播分红 8.流局 9.用户抢庄本金返还' AFTER guess_id;

CREATE TABLE `bean_opal_transfor` (
  `transfor_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '豆转干果流水id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
	`app_id` bigint(20) NOT NULL COMMENT '应用id',
  `transfor_type` smallint(8) NOT NULL COMMENT '转化类型 1豆转干果',
  `transfor_num` double(20,0) NOT NULL DEFAULT '0' COMMENT '转化值',
	`transfor_remark` varchar(128)  DEFAULT NULL COMMENT '转化说明',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`transfor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='豆转干果流水表';

/** sensitive_word **/
CREATE TABLE `sensitive_word` (
  `w_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '敏感词汇ID',
  `word_type` smallint(6) DEFAULT '1' COMMENT '词汇类别(0其他/1脏话/2色情/3政治/4领导/5民生)',
  `word` varchar(20) DEFAULT NULL COMMENT '敏感词汇',
  PRIMARY KEY (`w_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/** sensitive_pause **/
CREATE TABLE `sensitive_pause` (
  `p_id` bigint(11) NOT NULL DEFAULT '0',
  `symbol` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** ui_identity **/
ALTER TABLE ui_identity ADD opal bigint(20) DEFAULT '0' COMMENT '主播猫眼石' AFTER islive;

/** ui_user **/
ALTER TABLE ui_user ADD user_state smallint(6) DEFAULT '0' COMMENT '用户状态:0开启;1封禁' AFTER user_type;
ALTER TABLE ui_user MODIFY COLUMN signed varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个性签名' AFTER grade;

/** tv_feedback **/
ALTER TABLE tv_feedback ADD app_system_version varchar(16) not null COMMENT '应用版本' AFTER channel_num;

/** tv_banner **/
ALTER TABLE tv_banner ADD app_sit smallint(6) not null DEFAULT '3' COMMENT '应用场景 1-ios 2-android 3-全部' AFTER banner_status;
