<!--yst-2017-06-23-->
CREATE TABLE `act_extract_record` (
  `record_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `price` int(11) NOT NULL COMMENT '奖项金额',
  `name` varchar(32) NOT NULL COMMENT '奖项名字',
  `activity_type` smallint(8) DEFAULT '0' COMMENT '活动类型:0-龙猫豆抽奖',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户抽奖记录表';


CREATE TABLE `act_drawnum_record` (
  `draw_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `draw_num` int(11) NOT NULL COMMENT '抽奖次数',
  `activity_type` smallint(8) DEFAULT '0' COMMENT '活动类型:0-龙猫豆抽奖',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`draw_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户抽奖次数记录表';


CREATE TABLE `act_drawnum_source` (
  `source_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `draw_num` int(11) NOT NULL COMMENT '抽奖次数',
  `activity_type` smallint(8) DEFAULT '0' COMMENT '活动类型:0-龙猫豆抽奖',
  `source_type` smallint(8) DEFAULT '0' COMMENT '活动类型:0-每天新增  1-分享 2-微信口令',
  `create_time` varchar(32) NOT NULL COMMENT '创建时间yyyymmdd',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户抽奖次数来源表';

CREATE TABLE `app_on_off` (
  `app_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'APP开关ID',
  `code` bigint(10) NOT NULL DEFAULT '0' COMMENT 'APP开关CODE值',
  `classify` smallint(6) DEFAULT '0' COMMENT '分类(0:其他;1:版本;2:支付;3:直播)',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '属性名',
  `val` varchar(150) NOT NULL DEFAULT '' COMMENT '属性值',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP系统开关(版本)';


/** 2017-07-12  yst **/
ALTER TABLE live_h5 ADD share_title varchar(32)  COMMENT '分享标题' AFTER is_enable;
ALTER TABLE live_h5 ADD share_remark varchar(256)  COMMENT '分享描述' AFTER share_title;
ALTER TABLE live_h5 ADD share_pic_url varchar(256)  COMMENT '分享图标' AFTER share_remark;

ALTER TABLE tv_banner ADD is_share smallint(8) NOT NULL  COMMENT '是否分享（1是，2否）' AFTER app_sit;
ALTER TABLE tv_banner ADD share_title varchar(32)  COMMENT '分享标题' AFTER is_share;
ALTER TABLE tv_banner ADD share_remark varchar(256)  COMMENT '分享描述' AFTER share_title;
ALTER TABLE tv_banner ADD share_pic_url varchar(256)  COMMENT '分享图标' AFTER share_remark;

update tv_banner set is_share =1; 

CREATE TABLE `ui_exp_change_record` (
  `exp_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '经验值ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `change_type` smallint(6) DEFAULT NULL COMMENT '变更类型：0:增加；1:减少',
  `change_num` bigint(20) DEFAULT NULL COMMENT '变更数量',
  `biz_type` smallint(6) DEFAULT '0' COMMENT '业务类型：0:获取；1:消耗',
  `out_biz_id` bigint(20) DEFAULT NULL COMMENT '业务ID',
  `ctime` int(11) DEFAULT NULL COMMENT '行创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '行更新时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`exp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户经验值流水';

CREATE TABLE `ui_opal_change_record` (
  `opal_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '猫眼石ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `change_type` smallint(6) DEFAULT NULL COMMENT '变更类型：0:增加；1:减少',
  `change_num` bigint(20) DEFAULT NULL COMMENT '变更数量',
  `biz_type` smallint(6) DEFAULT '0' COMMENT '业务类型：0:获取；1:消耗',
  `out_biz_id` bigint(20) DEFAULT NULL COMMENT '业务ID',
  `ctime` int(11) DEFAULT NULL COMMENT '行创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '行更新时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`opal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播猫眼石流水';


CREATE TABLE `live_room_info` (
  `room_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_type` smallint(8) NOT NULL DEFAULT '1' COMMENT '私密房间 1-公开  2-私密',
  `room_tag` bigint(20) NOT NULL COMMENT '房间分数',
  `hot_type` smallint(8) NOT NULL DEFAULT '1' COMMENT '热门类型 1-自动热门  2-手动热门',
  `stream_id` varchar(128) DEFAULT NULL COMMENT '流id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `group_id` bigint(20) NOT NULL COMMENT '群组id',
  `play_id` bigint(20) DEFAULT NULL COMMENT '当前播放id',
  `play_status` smallint(8) NOT NULL DEFAULT '1' COMMENT '播放状态(0:在播；1:停播)',
  `live_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '直播标题',
  `live_cover` varchar(128) DEFAULT NULL COMMENT '直播封面',
  `lng` double DEFAULT NULL COMMENT '经度',
  `lat` double DEFAULT NULL COMMENT '纬度',
  `location` varchar(16) DEFAULT NULL COMMENT '位置信息',  
  `check_status` smallint(8) DEFAULT '0' COMMENT '未知',
  `forbidflag` smallint(8) DEFAULT '0' COMMENT '未知',
  `remark` varchar(256) DEFAULT NULL COMMENT '描述',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播房间表';


CREATE TABLE `live_play_info` (
  `play_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放id',
  `room_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_passwd` varchar(32) DEFAULT NULL COMMENT '私密房间口令',
  `live_label` smallint(8) DEFAULT '0' COMMENT '直播标签:0体育,1娱乐',
  `live_min_label` smallint(8) DEFAULT '0' COMMENT '直播小标签:0体育（0.足球1.篮球）,1娱乐（1.唱歌 2.二人转）',
  `like_count` int(20) DEFAULT '0' COMMENT '点赞人数',
  `viewer_count` int(20) DEFAULT '0' COMMENT '观看人数',
  `robot` int(11) DEFAULT '0' COMMENT '机器人数',
  `push_url` varchar(256) NOT NULL COMMENT '推流地址',
  `play_url` varchar(256) NOT NULL COMMENT '播放地址',
  `hls_play_url` varchar(256) NOT NULL COMMENT 'hls播放地址',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `phone_id` varchar(256) DEFAULT NULL COMMENT '头像',
  `guess_title` varchar(64) DEFAULT NULL COMMENT '竞猜标题',
  `guess_cover` varchar(64) DEFAULT NULL COMMENT '竞猜封面',
  `ctime` int(11) NOT NULL COMMENT '创建时间/开始时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间/结束时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`play_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播播放历史表';

CREATE TABLE `live_group_member` (
  `group_id` bigint(20) NOT NULL COMMENT '群组id',
  `user_id` bigint(20) NOT NULL COMMENT '主播id',
  `live_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `phone_id` varchar(256) DEFAULT NULL COMMENT '头像',
  `grade` int(6) DEFAULT '1' COMMENT '用户等级：默认1级',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`group_id`,`user_id`,`live_user_id`)
) ENGINE=InnoDB DEFAULT  CHARSET=utf8 COMMENT='直播群组真实用户表';

CREATE TABLE `live_group_info` (
  `group_info_id` bigint(20) NOT NULL COMMENT '群信息id',
  `group_id` bigint(20) NOT NULL COMMENT '群组id',
  `user_id` bigint(20) NOT NULL COMMENT '主播id',
  `live_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `is_enter` smallint(8) NOT NULL DEFAULT '1' COMMENT '进/出房间 1进  2出',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`group_info_id`)
) ENGINE=InnoDB DEFAULT  CHARSET=utf8 COMMENT='直播群组用户流水表';

CREATE TABLE `live_group_admin` (
  `admin_id` bigint(20) NOT NULL COMMENT '群信息id',
  `group_id` bigint(20) NOT NULL COMMENT '群组id',
  `user_id` bigint(20) NOT NULL COMMENT '主播id',
  `live_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `phone_id` varchar(256) DEFAULT NULL COMMENT '头像',
  `grade` int(6) DEFAULT '1' COMMENT '用户等级：默认1级',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT  CHARSET=utf8 COMMENT='直播群组管理员表';

CREATE TABLE `live_member_sit` (
  `sit_id` bigint(20) NOT NULL COMMENT '群信息id',
  `group_id` bigint(20) NOT NULL COMMENT '群组id',
  `user_id` bigint(20) NOT NULL COMMENT '主播id',
  `live_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `phone_id` varchar(256) DEFAULT NULL COMMENT '头像',
  `forbid_status` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否禁言 1是  2否',
  `grade` int(6) DEFAULT '1' COMMENT '用户等级：默认1级',
  `shutup_time` int(11) NOT NULL COMMENT '禁言时间',	
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`sit_id`)
) ENGINE=InnoDB DEFAULT  CHARSET=utf8 COMMENT='直播群组成员禁言列表';

ALTER TABLE ui_user MODIFY COLUMN user_state smallint(6) DEFAULT '0' COMMENT '用户状态:0开启;1封禁;2开启/禁言;3封禁/禁言';
ALTER TABLE ui_identity ADD base bigint(20) DEFAULT '0' COMMENT '主播直播分数基数' AFTER opal;
ALTER TABLE ui_identity ADD multiple smallint(6) DEFAULT '1' COMMENT '主播直播分数倍数' AFTER base;

CREATE TABLE `auth_info` (
  `auth_id` bigint(1) NOT NULL DEFAULT '0' COMMENT '授权ID',
  `app_id` varchar(50) DEFAULT NULL COMMENT '第三方AppId',
  `access_token` varchar(255) DEFAULT NULL COMMENT '第三方授权Token',
  `refresh_token` varchar(255) DEFAULT NULL COMMENT '第三方刷新Token',
  `invalid_time` int(11) DEFAULT NULL COMMENT '授权失效时间',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) DEFAULT NULL COMMENT '修改时间',
  `is_delete` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方授权信息';
/** 2017-08-28 未更新 **/
ALTER TABLE live_member_sit ADD grade int(6) DEFAULT '1' COMMENT '用户等级：默认1级' AFTER phone_id;
CREATE TABLE `gift_config_info` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '礼物id',
  `gift_name` varchar(32) NOT NULL COMMENT '虚拟礼物名称',
  `gift_desc` varchar(128) DEFAULT NULL COMMENT '礼物描述',
  `png_name` varchar(32) NOT NULL DEFAULT '' COMMENT '礼物图片名称',
  `coin` bigint(20) NOT NULL DEFAULT '0' COMMENT '龙猫币（礼物价值）',
  `calorie` bigint(20) NOT NULL DEFAULT '0' COMMENT '卡路里（礼物价值）',
  `exp_val` bigint(20) NOT NULL DEFAULT '0' COMMENT '经验值（礼物价值）',
  `experience` varchar(32) DEFAULT '' COMMENT '经验值描述',
  `gift_pic` varchar(128) DEFAULT '' COMMENT '礼物图片URL',
  `is_continuity` smallint(6) NOT NULL DEFAULT '2' COMMENT '是否连发(1:是，2:否)',
  `gift_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '礼物类型,1:小礼物;2:大礼物',
  `is_enable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用(1:是，2:否)',
  `ctime` int(11) DEFAULT '0' COMMENT '创建时间',
  `utime` int(11) DEFAULT '0' COMMENT '修改时间',
  `is_delete` smallint(6) DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟礼物配置信息表';

CREATE TABLE `gift_give_count` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '礼物统计流水id',
  `sender` bigint(20) NOT NULL DEFAULT '0' COMMENT '送礼人id',
  `receiver` bigint(20) NOT NULL DEFAULT '0' COMMENT '受礼人（主播）用户id',
  `live_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '直播id',
  `gift_count` int(11) NOT NULL DEFAULT '1' COMMENT '礼物数量',
  `coin` int(11) NOT NULL DEFAULT '0' COMMENT '龙猫币（礼物价值）',
  `calorie` int(11) NOT NULL DEFAULT '0' COMMENT '卡路里（礼物价值）',
  `exp_val` int(11) NOT NULL DEFAULT '0' COMMENT '经验值（礼物价值）',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='虚拟送礼赠送统计表';

CREATE TABLE `gift_give_snap` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '送礼流水id',
  `gift_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '礼物id',
  `sender` bigint(20) NOT NULL DEFAULT '0' COMMENT '送礼人id',
  `receiver` bigint(20) NOT NULL DEFAULT '0' COMMENT '受礼人（主播）用户id',
  `live_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '直播id',
  `gift_count` int(11) NOT NULL DEFAULT '1' COMMENT '礼物数量',
  `gift_name` varchar(32) NOT NULL COMMENT '虚拟礼物名称',
  `coin` bigint(20) NOT NULL DEFAULT '0' COMMENT '龙猫币（礼物价值）',
  `calorie` bigint(20) NOT NULL DEFAULT '0' COMMENT '卡路里（礼物价值）',
  `exp_val` bigint(20) NOT NULL DEFAULT '0' COMMENT '经验值（礼物价值）',
  `gift_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '礼物类型',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟送礼赠送快照表';


ALTER TABLE tv_banner ADD back_url varchar(256)  COMMENT '回调地址' AFTER share_pic_url;
update tv_banner set back_url='/actDrawnumRecord/updateActDrawnumRecord?userId=*&num=1&type=0&activityType=0&sourceType=1' where banner_id = 1498029885160333;

ALTER TABLE live_h5 ADD back_url varchar(256)  COMMENT '回调地址' AFTER share_pic_url;
update live_h5 set back_url='/actDrawnumRecord/updateActDrawnumRecord?userId=*&num=1&type=0&activityType=0&sourceType=1';

