/** 用户身份认证表 2018.01.29 未更新**/
DROP TABLE IF EXISTS `user_identity_auth`;
CREATE TABLE `user_identity_auth` (
  `auth_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '身份认证ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户UserID',
  `auth_identity` smallint(6) DEFAULT '0' COMMENT '认证身份0:普通用户;1:主播;2:企业;3托号;10:机器人',
  `auth_status` smallint(6) DEFAULT '0' COMMENT '身份认证审核状态:0审核中;1通过;2未通过',
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `mobile` char(11) DEFAULT NULL COMMENT '用户手机号',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `front_identity_url` varchar(150) DEFAULT NULL COMMENT '身份证正面照',
  `opposite_identity_url` varchar(150) DEFAULT NULL COMMENT '身份证反面照',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(150) DEFAULT NULL COMMENT '认证备注',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户身份认证表';
/** 礼物表 2018.02.26 未更新**/
ALTER TABLE gift_config_info ADD COLUMN gift_download varchar(128) DEFAULT '' COMMENT '大礼物资源包下载' AFTER gift_pic;
/** 回复配置 2018.02.28 未更新**/
DROP TABLE IF EXISTS `reply_config`;
CREATE TABLE `reply_config` (
  `reply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '回复ID',
  `reply_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '0其他;1意见反馈;2身份认证;3违规举报;4公会审核',
  `reply_content` varchar(150) DEFAULT '' COMMENT '回复内容',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回复意见配置';

-- act_config（活动配置）表，增加两个字段(other_parameter，other_parameter_explain)，用于保存活动用到的其他参数  hjy 2018年3月2日11:09:46
ALTER TABLE act_config ADD COLUMN other_parameter varchar(255) DEFAULT NULL COMMENT '其他参数' AFTER status;
ALTER TABLE act_config ADD COLUMN other_parameter_explain varchar(255) DEFAULT NULL COMMENT '其他参数说明' AFTER other_parameter;
