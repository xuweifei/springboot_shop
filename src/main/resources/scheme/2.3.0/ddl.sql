-- 新建全服统计表 hjy 2017年12月21日17:09:46
DROP TABLE IF EXISTS `all_count`;
CREATE TABLE `all_count` (
  `id` bigint(20) NOT NULL COMMENT '全服统计id',
  `count_type` smallint(4) NOT NULL COMMENT '统计的类型 1-全服主播累计所获卡路里 2-全服用户累计竞猜收益 3-全服用户累计送出礼物金额',
  `anchor_id` bigint(20) DEFAULT NULL COMMENT '受礼人（主播）id',
  `sender_id` bigint(20) DEFAULT NULL COMMENT '送礼人（用户）id',
  `sort_num` int(11) NOT NULL COMMENT '序号',
  `coin_count` bigint(1) DEFAULT NULL COMMENT '统计龙猫币',
  `calorie_count` bigint(1) DEFAULT NULL COMMENT '统计卡路里',
  `rob_profit_count` bigint(1) DEFAULT NULL COMMENT '统计竞猜收益',
  `ctime` int(11) DEFAULT NULL COMMENT '统计时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全服统计表'

/* app_admin_sig 12.26未更新 */
DROP TABLE IF EXISTS `app_admin_sig`;
CREATE TABLE `app_admin_sig` (
  `sig_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '管理ID',
  `environment` varchar(10) DEFAULT 'dev' COMMENT '使用环境',
  `app_id` bigint(20) DEFAULT NULL COMMENT '应用APPID',
  `app_name` varchar(32) DEFAULT 'adminlm' COMMENT 'APP账户',
  `sig` varchar(350) DEFAULT NULL COMMENT 'SIG',
  `sig_create` datetime DEFAULT NULL COMMENT 'SIG创建时间',
  PRIMARY KEY (`sig_id`),
  UNIQUE KEY `uni_app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP管理员SIG';
/* live_play_info 2018.1.6未更新 */
ALTER TABLE live_play_info ADD COLUMN start_time int(11) DEFAULT 0 COMMENT '开始时间' AFTER guess_cover;
ALTER TABLE live_play_info ADD COLUMN end_time int(11) DEFAULT 0 COMMENT '结束时间' AFTER ctime;


/*新增加的 短信配置表  add by szy 2018.1.9*/
DROP TABLE IF EXISTS `lm_sms_config`;
CREATE TABLE `lm_sms_config` (
  `sms_id` tinyint(25) NOT NULL COMMENT '主键ID',
  `sms_mark` varchar(30) DEFAULT '' COMMENT '短信标识',
  `service_name` varchar(30) DEFAULT '' COMMENT '服务名称',
  `sms_name` varchar(30) DEFAULT '' COMMENT '短信名称',
  `sms_template` varchar(255) DEFAULT '' COMMENT '短信模版',
  `sms_addressee` varchar(255) DEFAULT '' COMMENT '收件人',
  `sms_receiver` varchar(255) DEFAULT '' COMMENT '短信接受者',
  `sms_status` smallint(2) DEFAULT '1' COMMENT '状态(1：启用，2：停用)',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(2) DEFAULT '1' COMMENT '是否删除(1.正常，-1.删除)',
  PRIMARY KEY (`sms_id`),
  UNIQUE KEY `unique_name` (`sms_name`) USING BTREE,
  KEY `index_id_status_delete` (`sms_id`,`sms_status`,`is_delete`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='龙猫运营后台短信配置表';

INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('1', 'anchor_loss_warning', 'totoro_tv', '主播日亏损报警线', '[nickName]（主播: [anchorId]）存在竞猜异常！请立即核对。', '13305716985', '梁总', '1', '[nickName]和[anchorId]不能修改', '1515549218', '1516094707', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('2', 'recharge_warning', 'totoro_pay', '充值金额预警', '龙猫直播温馨提醒：用户[userId]充值了[payPrice]元，金额大于[max]元,请熟知。', '18606892550,13456781709,13588295845', '曾金全,朱宏林,杨沈涛', '1', '[userId],[payPrice],[max]不能修改', '1515549218', '1516094699', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('3', 'exception_sms', 'totoro_pay', '异常短信通知', '异常短信参考信息：用户[userId]在订单[orderId]中充值了[price]元，加豆成功，后续步骤出现异常，请检查代码。', '18606892550,13456781709,13588295845', '曾金全,朱宏林,杨沈涛', '1', '[userId],[orderId],[price]不能修改', '1515549218', '1516094697', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('4', 'harvest_switch', 'totoro_manage', '收割开关短信通知', '[admin](time),当前游戏为[number]盘。', '13305716985', '梁总', '1', '[admin],(time),[number]不能修改', '1515549218', '1516095838', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('5', 'confraternity_check_pass', 'totoro_manage', '公会审核通过', '您申请的(confraternityName)的公会账号,已通过审核认证。请及时添加所属主播。谢谢! ', '操作人手机号', '操作人', '1', '(confraternityName)不能修改', '1515549218', '1516095813', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('6', 'confraternity_check_no_pass', 'totoro_manage', '公会审核不通过', '您申请的(confraternityName)的公会账号,未通过审核认证。请及时登录公会系统,修改并提交申请。谢谢!', '操作人手机号', '操作人', '1', '(confraternityName)不能修改', '1515549218', '1516095710', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('7', 'change_mobile', 'totoro_user', '更换手机号验证码短信通知', '提醒您，您的验证码：[messagecode]。如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '[messagecode]不能修改', '1515549218', '1516095722', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('8', 'common_sms', 'totoro_user', '通用短信发送', '提醒您，您的验证码：[messagecode]。如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '[messagecode]不能修改', '1515549218', '1516095735', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('9', 'find_back_pwd', 'totoro_user', '找回密码验证码通知', '提醒您，您正在找回密码，验证码：[messagecode]。如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '[messagecode]不能修改', '1515549218', '1516095747', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('10', 'login_totoro', 'totoro_user', '快速登录验证码通知', '提醒您，您正在登录{龙猫直播}，验证码： [messagecode]。如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '[messagecode]不能修改', '1515549218', '1516095752', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('11', 'manage_login', 'totoro_manage', 'manage登录短信通知', '龙猫直播提醒您，您本次登录验证码：[captcha],有效时间为两分钟。如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '[captcha]不能修改', '1515549218', '1516095761', '1');
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('12', 'channel_login', 'totoro_channel', '渠道系统登录短信通知', '龙猫直播提醒您，您本次登录验证码：[captcha],有效时间为两分钟。如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '[captcha]不能修改', '1515549218', '1516095768', '1');


-- act_extract_record（用户抽奖记录）表，增加一个字段(goods_id)，用于保存得到的商品的id  hjy 2018年1月11日15:09:46
ALTER TABLE act_extract_record ADD COLUMN goods_id bigint(20) DEFAULT NULL COMMENT '商品id' AFTER activity_type;


