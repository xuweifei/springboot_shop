/**主播减分规则表 */
CREATE TABLE `anchor_deduction_rule` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主播信誉减分规则id',
  `deduction_item` varchar(100) DEFAULT NULL COMMENT '加分项',
  `condition` varchar(20) DEFAULT NULL COMMENT '加分条件值',
  `credit_val` varchar(30) DEFAULT NULL COMMENT '信誉值加分',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播减分规则表';

/**主播加分规则表*/
CREATE TABLE `anchor_plus_rule` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主播信誉加分规则id',
  `plus_item` varchar(100) DEFAULT NULL COMMENT '加分项',
  `condition` smallint(6) DEFAULT NULL COMMENT '加分条件值',
  `credit_val` varchar(15) DEFAULT NULL COMMENT '信誉值加分',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播加分规则表';

/**[全民主播]信誉等级提成规则表  */
CREATE TABLE `anchor_credit_rule` (
`id` bigint(20) NOT NULL COMMENT '主播等级id(极好:6;优秀:5;良好(初始等级):0;中等:3;较差:2;极差:1)',
`grade` varchar(20) DEFAULT NULL COMMENT '主播等级说明(极好:1;优秀:2;良好(初始等级):3;中等:4;较差:5;极差:6)',
`min_val` smallint(6) DEFAULT NULL COMMENT '信誉最小值',
`max_val` smallint(6) DEFAULT NULL COMMENT '信誉最大值',
`dist_ratio` smallint(6) DEFAULT NULL COMMENT '分成比例',
`remark` varchar(255) DEFAULT NULL COMMENT '规则说明',
`ctime` int(11) NOT NULL COMMENT '创建时间',
`utime` int(11) NOT NULL COMMENT '修改时间',
`is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[全民主播]信誉等级提成规则表';

/**主播信誉流水表  */
CREATE TABLE `anchor_credit_flow` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主播信誉流水id',
  `anchor_id` bigint(20) DEFAULT NULL COMMENT '主播id',
  `add_or_del` smallint(6) DEFAULT NULL COMMENT '加还是减（加分:1；减分:-1）',
  `remark` varchar(100) DEFAULT NULL COMMENT '加减分理由',
  `val` smallint(6) DEFAULT NULL COMMENT '分值',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则id',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='主播信誉流水表';

/** 用户等级规则表 **/
CREATE TABLE `user_grade_rule` (
`id` bigint(20) NOT NULL COMMENT '规则表ID',
`grade` smallint(2) DEFAULT '0' COMMENT '用户等级',
`min_val` bigint(20) DEFAULT NULL COMMENT '等级经验值最小值',
`max_val` bigint(20) DEFAULT NULL COMMENT '等级经验值最大值',
`alias` varchar(6) DEFAULT NULL COMMENT '等级别名',
`note` varchar(100) DEFAULT NULL COMMENT '等级说明',
`is_max` smallint(2) DEFAULT '0' COMMENT '是否最大值，0否，1是';
`ctime` int(11) DEFAULT NULL COMMENT '创建时间',
`utime` int(11) DEFAULT NULL COMMENT '修改时间',
`is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户等级规则表';

--举报情况表
create table tv_illegal_report(
	`report_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '举报id',
  `anchor_id` bigint(32) DEFAULT '0' COMMENT '主播id',
  `room_id` bigint(32) DEFAULT '0' COMMENT '房间id',
  `user_id` bigint(32) DEFAULT '0' COMMENT '举报人id',
  `report_type` smallint(8) DEFAULT '0' COMMENT '举报种类（0广告欺骗，1淫秽色情，2骚扰谩骂，3反动政治，4虚假竞猜，5其他内容）',
  `report_reason` varchar(256) DEFAULT NULL COMMENT '举报原因',
  `ctime` int(16) NOT NULL COMMENT '创建时间',
  `utime` int(16) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报情况表';
/**反馈意见表**/
CREATE TABLE `tv_feedback` (
  `feedback_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '意见反馈id',
  `user_id` bigint(32) DEFAULT '0' COMMENT '反馈人id',
  `feedback_type` smallint(8) DEFAULT '0' COMMENT '反馈种类（0功能需求，1报告错误，2其他）',
  `feedback_remark` varchar(256) DEFAULT NULL COMMENT '反馈说明',
  `contact_way` varchar(16) DEFAULT NULL COMMENT '联系方式 电话/QQ',
  `deal_status` smallint(8) DEFAULT '0' COMMENT '处理状态（0待处理，1已处理）',
  `deal_remark` varchar(256) DEFAULT NULL COMMENT '处理说明',
  `deal_user` bigint(32) DEFAULT '0' COMMENT '处理人',
  `deal_time` int(16) DEFAULT NULL COMMENT '处理时间',
  `ctime` int(16) NOT NULL COMMENT '创建时间',
  `utime` int(16) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='反馈意见表';

ALTER TABLE li_live ADD live_cover VARCHAR(100) DEFAULT NULL COMMENT '直播封面' AFTER live_title;

/* 用户基本信息表修改表  ui_user */
ALTER TABLE ui_user ADD wechat_id VARCHAR(100) DEFAULT NULL COMMENT '微信唯一ID,绑定登录' AFTER user_name;
ALTER TABLE ui_user ADD qq_id VARCHAR(100) DEFAULT NULL COMMENT 'QQ唯一ID,绑定登录' AFTER wechat_id;
ALTER TABLE ui_user ADD weibo_id VARCHAR(100) DEFAULT NULL COMMENT '微博唯一ID,绑定登录' AFTER qq_id;
ALTER TABLE ui_user ADD city VARCHAR(100) DEFAULT NULL COMMENT '用户城市';
ALTER TABLE ui_user ADD credit_grade smallint(6) DEFAULT '75' COMMENT '信誉等级分数' AFTER grade;
ALTER TABLE ui_user ADD credit_grade_explain varchar(10) DEFAULT '良好（初始等级）' COMMENT '信用等级说明' AFTER credit_grade;
/* 直播信息表修改表 li_live */
ALTER TABLE li_live ADD live_cover VARCHAR(100) DEFAULT NULL COMMENT '直播封面' AFTER live_title;
ALTER TABLE li_live ADD live_label SMALLINT(4) DEFAULT '0' COMMENT '直播标签:0体育,1娱乐' AFTER live_cover;
/* 关注信息表修改表  ui_attentions */
ALTER TABLE ui_attentions MODIFY COLUMN attentions_state SMALLINT(2) DEFAULT '2' COMMENT '1关注，2未关注'
ALTER TABLE ui_attentions ADD is_attention SMALLINT(2) DEFAULT '2' COMMENT '相互是否关注，1关注,2未关注' AFTER attentions_state;
/* 认证信息表修改表 ui_identity */
ALTER TABLE ui_identity MODIFY COLUMN id BIGINT(20) NOT NULL;
ALTER TABLE ui_identity MODIFY COLUMN real_name VARCHAR(32) DEFAULT NULL COMMENT '主播真实姓名';
ALTER TABLE ui_identity MODIFY COLUMN status SMALLINT(2) DEFAULT '0' COMMENT '审核通过标识：0未通过；1通过';
ALTER TABLE ui_identity MODIFY COLUMN gmt_create INT(11) DEFAULT NULL COMMENT '行创建时间戳';
ALTER TABLE ui_identity MODIFY COLUMN gmt_modified INT(11) DEFAULT NULL COMMENT '行更新时间戳';
ALTER TABLE ui_identity ADD anchor_type SMALLINT(2) DEFAULT '0' COMMENT '主播类型:0全民主播;1家族主播' AFTER user_id;
ALTER TABLE ui_identity ADD alipay_id VARCHAR(100) DEFAULT NULL COMMENT '支付宝ID，提现转账' AFTER status;
ALTER TABLE ui_identity ADD ratio VARCHAR(5) DEFAULT '0.50' COMMENT '提现比率' AFTER alipay_id;
ALTER TABLE ui_identity ADD credit_grade smallint(6) DEFAULT '75' COMMENT '信誉等级分数' AFTER ratio;
ALTER TABLE ui_identity ADD credit_grade_explain varchar(10) DEFAULT '良好（初始等级）' COMMENT '信用等级说明' AFTER credit_grade;
ALTER TABLE ui_identity ADD islive SMALLINT(2) DEFAULT '0' COMMENT '直播权限:0禁播；1开播' AFTER credit_grade;
ALTER TABLE ui_identity ADD remark VARCHAR(50) DEFAULT NULL COMMENT '审核备注' AFTER islive;
/** 登入记录表更新 ui_loginrecord **/
ALTER TABLE ui_loginrecord MODIFY COLUMN gmt_create datetime DEFAULT NULL COMMENT '行创建时间';
ALTER TABLE ui_loginrecord MODIFY COLUMN gmt_modified datetime DEFAULT NULL COMMENT '行更新时间';
ALTER TABLE ui_loginrecord ADD login_way SMALLINT(2) DEFAULT '1' COMMENT '登入直播途径' AFTER cid;
ALTER TABLE ui_loginrecord ADD login_user VARCHAR(50) DEFAULT NULL COMMENT '登入直播账户' AFTER login_way;
ALTER TABLE ui_loginrecord ADD login_last_time datetime DEFAULT NULL COMMENT '上次登入直播时间' AFTER login_user;
ALTER TABLE ui_loginrecord ADD login_site VARCHAR(50) DEFAULT NULL COMMENT '登入直播位置' AFTER login_last_time;

/********************************* SNAPSHOT-1.8.2 ************************************************************/
/**banner配置表**/
CREATE TABLE `tv_banner` (
  `banner_id` bigint(32) NOT NULL DEFAULT '0' COMMENT 'banner_id',
  `banner_name` varchar(128) NOT NULL COMMENT 'banner名字',
  `banner_pic_url` varchar(256) NOT NULL COMMENT 'banner图URL',
  `banner_url` varchar(256) DEFAULT NULL COMMENT 'banner超链接',
  `up_time` int(16) DEFAULT NULL COMMENT '上架时间',
  `down_time` int(16) DEFAULT NULL COMMENT '下架时间',
  `sort_num` smallint(8) DEFAULT '0' COMMENT '排序',
  `banner_status` smallint(8) DEFAULT '2' COMMENT '启用状态（1是，2否）',
  `ctime` int(16) NOT NULL COMMENT '创建时间',
  `utime` int(16) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner配置表';
/**活动配置表**/
CREATE TABLE `tv_activity` (
  `activity_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '活动id',
  `activity_name` varchar(128) NOT NULL COMMENT '活动名字',
  `activity_pic_url` varchar(256) NOT NULL COMMENT '活动图URL',
  `activity_url` varchar(256) DEFAULT NULL COMMENT '活动超链接',
  `up_time` int(16) DEFAULT NULL COMMENT '上架时间',
  `down_time` int(16) DEFAULT NULL COMMENT '下架时间',
  `sort_num` smallint(8) DEFAULT '0' COMMENT '排序',
  `activity_status` smallint(8) DEFAULT '2' COMMENT '启用状态（1是，2否）',
  `ctime` int(16) NOT NULL COMMENT '创建时间',
  `utime` int(16) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动配置表';
/**短信记录表**/
CREATE TABLE `lm_sms_message` (
  `sms_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '短信id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `mobile` varchar(16) NOT NULL COMMENT '手机号码',
  `content` varchar(256) NOT NULL COMMENT '短信内容',
  `ctime` int(16) NOT NULL COMMENT '创建时间',
  `utime` int(16) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信记录表';
/* 用户基本信息表修改表  ui_user */
ALTER TABLE ui_user ADD wechat_name VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信用户昵称' AFTER wechat_id;
ALTER TABLE ui_user ADD qq_name VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ用户昵称' AFTER qq_id;
ALTER TABLE ui_user ADD weibo_name VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微博用户昵称' AFTER weibo_id;
ALTER TABLE ui_user ADD push_id VARCHAR(50) DEFAULT NULL COMMENT '消息推送ID' AFTER city;
ALTER TABLE ui_user ADD is_delete SMALLINT(6) DEFAULT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)' AFTER push_id;
ALTER TABLE ui_user MODIFY COLUMN nick_name varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称' AFTER login_pwd;
/* 用户基本信息表修改表  ui_loginrecord */
ALTER TABLE ui_loginrecord ADD chl_id VARCHAR(32) DEFAULT NULL COMMENT '渠道ID' AFTER user_id;
ALTER TABLE ui_loginrecord ADD is_delete SMALLINT(6) DEFAULT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)' AFTER gmt_modified_user;
/** 靓号表 good_number **/
CREATE TABLE `good_number` (
  `good_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '靓号ID',
  `number` bigint(20) NOT NULL DEFAULT '0' COMMENT '靓号',
  `remark` varchar(20) DEFAULT NULL COMMENT '靓号说明备注',
  `is_retain` smallint(6) DEFAULT '1' COMMENT '是否保留:0否,1是',
  PRIMARY KEY (`good_id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*意见反馈表 增加图片字段2017-5-9 yst*/
ALTER TABLE tv_feedback ADD feedback_url VARCHAR(512) DEFAULT NULL COMMENT '反馈图片' AFTER feedback_remark;
/** 图片地址 **/
UPDATE ui_user set phone_id = concat('http://photo.tvlongmao.com/photo/', phone_id) where phone_id !='';
UPDATE ui_photo_album set photo_url = concat('http://photo.tvlongmao.com/photo/', photo_url);
/*意见反馈表 增加字段2017-5-12 yst*/
ALTER TABLE tv_feedback ADD system_type VARCHAR(16) DEFAULT NULL COMMENT '系统类型IOS/Android' AFTER feedback_url;
ALTER TABLE tv_feedback ADD system_version VARCHAR(16) DEFAULT NULL COMMENT '系统版本号' AFTER system_type;
ALTER TABLE tv_feedback ADD brand_type VARCHAR(16) DEFAULT NULL COMMENT '品牌类型' AFTER system_version;
ALTER TABLE tv_feedback ADD channel_num VARCHAR(32) DEFAULT NULL COMMENT '渠道号' AFTER brand_type;
/*删除guess_info的is_close字段*/
ALTER TABLE `guess_info` DROP COLUMN is_close;

/******************** sys_config ********************/
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '属性id',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '属性名',
  `val` varchar(150) NOT NULL DEFAULT '' COMMENT '属性名',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/******************** role_menu ********************/
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `menu_name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单uri',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`menu_id`),
  KEY `index_menu_name` (`menu_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

/******************** admin_info ********************/
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '后台用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `user_alias` varchar(30) DEFAULT NULL COMMENT '用户别名',
  `user_sex` smallint(6) DEFAULT NULL COMMENT '性别(1-男,2-女)',
  `user_age` varchar(8) DEFAULT NULL COMMENT '出生日期',
  `user_mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `user_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `user_login_time` int(11) DEFAULT NULL COMMENT '登录时间',
  `user_login_ip` varchar(20) DEFAULT NULL COMMENT '用户登录请求ip',
  `user_status` smallint(6) NOT NULL DEFAULT '1' COMMENT '用户状态(1-正常,2-冻结)',
  `rights` varchar(50) DEFAULT NULL COMMENT '权限id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`user_id`),
  KEY `index_user_name` (`user_name`,`user_status`,`is_delete`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='系统用户信息表';
/** li_live **/
ALTER TABLE li_live MODIFY COLUMN live_title varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '直播标题' AFTER user_id;
