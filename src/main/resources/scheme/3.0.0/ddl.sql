/* 直播版块配置 live_forum_config 2018.03.06 未更新 */
DROP TABLE IF EXISTS `live_forum_config`;
CREATE TABLE `live_forum_config` (
  `forum_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '版块ID',
  `forum_rank` smallint(6) NOT NULL DEFAULT '1' COMMENT '版块排序',
  `forum_name` varchar(10) NOT NULL DEFAULT '娱乐' COMMENT '版块名称',
  `forum_label` varchar(10) DEFAULT '娱乐' COMMENT '版块标签',
  `forum_icon` varchar(100) DEFAULT '' COMMENT '版块图标',
  `forum_func` varchar(100) DEFAULT '' COMMENT '版块功能',
  `is_show` smallint(6) NOT NULL DEFAULT '2' COMMENT '是否显示:1是;2否',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='版块配置';

DROP TABLE IF EXISTS `live_forum_func`;
CREATE TABLE `live_forum_func` (
  `func_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '功能ID',
  `func_name` varchar(10) NOT NULL DEFAULT '竞猜' COMMENT '功能名称(竞猜,购物)',
  `is_use` smallint(6) NOT NULL DEFAULT '2' COMMENT '是否使用:1是;2否',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`func_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='直播版本功能';
/* 主播身份 ui_identity 2018.03.06 未更新 */
ALTER TABLE ui_identity ADD COLUMN live_forum smallint(6) DEFAULT '1' COMMENT '直播版块:1娱乐/2财经' AFTER anchor_type;
/* ui_identit表增加字段 add by szy 2018.3.13 */
ALTER TABLE ui_identity ADD experience int(32) DEFAULT '0' comment '经验值' AFTER credit_grade_explain;
ALTER TABLE ui_identity ADD grade int(1) DEFAULT '1' comment '主播等级(默认1级)' AFTER credit_grade_explain;
/* 主播等级规则表 anchor_grade_rule - add by szy 2018.3.13*/
DROP TABLE IF EXISTS `anchor_grade_rule`;
CREATE TABLE `anchor_grade_rule` (
  `id` bigint(20) NOT NULL COMMENT '规则ID',
  `grade` smallint(6) DEFAULT '0' COMMENT '主播等级',
  `grade_url` varchar(255) DEFAULT NULL COMMENT '等级图片地址链接',
  `honor_url` varchar(255) DEFAULT NULL COMMENT '头衔图片地址链接',
  `min_val` bigint(20) DEFAULT NULL COMMENT '等级经验值最小值',
  `max_val` bigint(20) DEFAULT NULL COMMENT '等级经验值最大值',
  `alias` varchar(10) DEFAULT NULL COMMENT '等级别名',
  `note` varchar(120) DEFAULT NULL COMMENT '等级说明',
  `is_max` smallint(2) DEFAULT '0' COMMENT '是否最大值，0否，1是',
  `ctime` int(11) DEFAULT '0' COMMENT '创建时间',
  `utime` int(11) DEFAULT '0' COMMENT '修改时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播等级规则表';
/* 帮助中心配置 ui_help_type 2018.03.15 未更新 */
DROP TABLE IF EXISTS `ui_help_type`;
CREATE TABLE `ui_help_type` (
  `type_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '问题类型ID',
  `type_name` varchar(10) DEFAULT '直播' COMMENT '问题类型名称',
  `type_icon` varchar(100) DEFAULT NULL COMMENT '问题类型图标',
  `type_rank` smallint(6) DEFAULT '1' COMMENT '类型排序',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='帮助中心问答类型';
/* 帮助中心配置 ui_help_question_answer 2018.03.15 未更新 */
DROP TABLE IF EXISTS `ui_help_question_answer`;
CREATE TABLE `ui_help_question_answer` (
  `question_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '问答ID',
  `help_type` bigint(20) DEFAULT '0' COMMENT '类型ID',
  `question_rank` smallint(6) DEFAULT '1' COMMENT '帮助中心问答-排序',
  `question` varchar(50) DEFAULT NULL COMMENT '帮助中心问答-问题',
  `question_label` varchar(10) DEFAULT '直播' COMMENT '帮助中心问答-标签',
  `answer` text COMMENT '帮助中心问答-答案',
  `answer_ios` text COMMENT '帮助中心问答-答案(IOS)',
  `is_common` smallint(6) DEFAULT '1' COMMENT '是否共用:1是;2否',
  `is_show` smallint(6) DEFAULT '2' COMMENT '是否显示:1是;2否',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='帮助中心问答内容';
-- 个推信息表 gexin_info --hjy 2018.03.16 已更新
DROP TABLE IF EXISTS `gexin_info`;
CREATE TABLE `gexin_info` (
  `id` bigint(20) NOT NULL COMMENT '个推id',
  `gexin_title` varchar(40) NOT NULL COMMENT '通知栏标题',
  `gexin_text` varchar(600) NOT NULL COMMENT '通知栏内容',
  `gexin_logourl` varchar(100) DEFAULT NULL COMMENT '通知栏左边的图标地址',
  `gexin_openurl` varchar(200) DEFAULT NULL COMMENT '打开的网址地址',
  `gexin_downloadurl` varchar(200) DEFAULT NULL COMMENT '下载的地址',
  `transmission_context` varchar(2048) DEFAULT NULL COMMENT '打开APP后再传给用户的数据（打开app模板中使用）',
  `pop_title` varchar(40) DEFAULT NULL COMMENT '弹出框标题(下载模板 必写)',
  `pop_content` varchar(600) DEFAULT NULL COMMENT '弹出框内容(下载模板 必写)',
  `gexin_group` smallint(1) NOT NULL COMMENT '推送群体（1：单个用户，2：列表用户，3：应用群）',
  `push_type` smallint(1) DEFAULT NULL COMMENT '需要推送的user类别',
  `push_id` varchar(10240) DEFAULT NULL COMMENT 'user的pushid',
  `gexin_phonetype` varchar(255) DEFAULT NULL COMMENT '发送的手机类型（IOS,ANDROID）',
  `gexin_city` varchar(1024) DEFAULT NULL COMMENT '发送的城市（使用逗号隔开）',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `send_corn` varchar(255) NOT NULL COMMENT '与发送时间对应的Cron表达式',
  `gexin_type` smallint(1) NOT NULL DEFAULT '1' COMMENT '个推的类型（1：打开网页；2：打开app；3：下载）',
  `is_finish` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否发送（1：未发送；2：已发送；3：发生错误）',
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`id`),
  KEY `send_time` (`send_time`),
  KEY `is_finish` (`is_finish`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个推信息表'
/* 敏感词汇sensitive_word 2018.03.19 ALTER */
ALTER TABLE sensitive_word ADD COLUMN is_use smallint(6) DEFAULT '2' COMMENT '是否使用:1是;2否' AFTER word;
ALTER TABLE sensitive_word ADD COLUMN ctime int(11) DEFAULT NULL COMMENT '创建时间' AFTER is_use;
ALTER TABLE sensitive_word ADD COLUMN utime int(11) DEFAULT NULL COMMENT '更新时间' AFTER ctime;
ALTER TABLE sensitive_word ADD COLUMN is_delete smallint(6) DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)' AFTER utime;
/* 敏感词汇分割符sensitive_pause 2018.03.19 ALTER */
ALTER TABLE sensitive_pause ADD COLUMN is_use smallint(6) DEFAULT '2' COMMENT '是否使用:1是;2否' AFTER symbol;
ALTER TABLE sensitive_pause ADD COLUMN ctime int(11) DEFAULT NULL COMMENT '创建时间' AFTER is_use;
ALTER TABLE sensitive_pause ADD COLUMN utime int(11) DEFAULT NULL COMMENT '更新时间' AFTER ctime;
ALTER TABLE sensitive_pause ADD COLUMN is_delete smallint(6) DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)' AFTER utime;

