<!--xwf-2017-09-06-->
ALTER TABLE ui_identity ADD bigint int(20) DEFAULT 0 comment '直播间机器人数量' AFTER opal;
ALTER TABLE ui_identity ADD isguess smallint(11) DEFAULT 1 comment '竞猜权限:1可以开盘 2不可以开盘' AFTER islive;
ALTER TABLE guess_subject ADD sorts smallint(11) NOT NULL comment '排序字段' AFTER option_four;
update guess_subject g set g.sorts = g.sub_id;
ALTER TABLE live_h5 ADD is_share smallint(11) NOT NULL DEFAULT 1 comment '是否分享：1分享，2不分享' AFTER share_remark;

CREATE TABLE `live_config` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键id',
  `classify` varchar(30) DEFAULT '0' COMMENT '分类()',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '属性名',
  `val` varchar(150) NOT NULL DEFAULT '' COMMENT '属性值',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `live_config` (`id`, `classify`, `name`, `val`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('1', '1', 'room_share_describe', '看直播，玩竞猜，豆子还能换话费，你还不快来!', '', '1505450776', '1505450776', '1');

<!--yst-2017-09-06-->
ALTER TABLE guess_subject ADD is_man smallint(8) NOT NULL DEFAULT 1 comment '是否人控 1是 2否' AFTER option_four;

INSERT INTO `guess_subject` (`sub_id`, `type_id`, `category_id`, `guess_title`, `guess_content`, `option_one`, `option_two`, `option_three`, `option_four`, `sorts`, `is_man`, `dis_time`, `is_rob`, `use_count`, `pool_bean`, `is_enable`, `ctime`, `utime`, `is_delete`) VALUES ('35', '6', '23', '硬币是正面还是反面？', '', '正面', '反面', '', '', '0', '1', '60', '2', '0', '500000', '2', '1491449440', '1491449440', '1');
INSERT INTO `guess_subject` (`sub_id`, `type_id`, `category_id`, `guess_title`, `guess_content`, `option_one`, `option_two`, `option_three`, `option_four`, `sorts`, `is_man`, `dis_time`, `is_rob`, `use_count`, `pool_bean`, `is_enable`, `ctime`, `utime`, `is_delete`) VALUES ('36', '6', '23', '龙猫在A纸牌还是B纸牌？', '', 'A纸牌', 'B纸牌', '', '', '1', '1', '60', '2', '0', '500000', '2', '1491449440', '1491449440', '1');
INSERT INTO `guess_subject` (`sub_id`, `type_id`, `category_id`, `guess_title`, `guess_content`, `option_one`, `option_two`, `option_three`, `option_four`, `sorts`, `is_man`, `dis_time`, `is_rob`, `use_count`, `pool_bean`, `is_enable`, `ctime`, `utime`, `is_delete`) VALUES ('37', '6', '23', '主播抽到真心话还是大冒险？', '', '真心话', '大冒险', '', '', '2', '1', '60', '2', '0', '500000', '2', '1491449440', '1491449440', '1');
INSERT INTO `guess_subject` (`sub_id`, `type_id`, `category_id`, `guess_title`, `guess_content`, `option_one`, `option_two`, `option_three`, `option_four`, `sorts`, `is_man`, `dis_time`, `is_rob`, `use_count`, `pool_bean`, `is_enable`, `ctime`, `utime`, `is_delete`) VALUES ('38', '6', '23', '被击中的是主播还是我？', '', '主播', '我', '', '', '4', '1', '60', '2', '0', '500000', '2', '1491449440', '1491449440', '1');

ALTER TABLE guess_info ADD is_man smallint(8) NOT NULL DEFAULT 1 comment '是否人控 1是 2否' AFTER option_four;

ALTER TABLE guess_record ADD is_man smallint(8) NOT NULL DEFAULT 1 comment '是否人控 1是 2否' AFTER guess_id;

INSERT INTO `sys_config` (`id`, `classify`, `name`, `val`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('36', '3', 'win_machine_random', '2', '是否开启利好倾向开关（1：开启；2：不开启）', '1488972528', '1496888651', '1');

INSERT INTO `sys_config` (`id`, `classify`, `name`, `val`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('37', '3', 'user_single_bet', '50000', '用户单局最高可押注', '1488972528', '1496888651', '1');
INSERT INTO `sys_config` (`id`, `classify`, `name`, `val`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('38', '3', 'anchor_daily_loss', '300000', '主播日亏损报警线', '1488972528', '1496888651', '1');
INSERT INTO `sys_config` (`id`, `classify`, `name`, `val`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('39', '3', 'machine_game_poolbean', '500000', '机控游戏注池金额', '1488972528', '1496888651', '1');
INSERT INTO `sys_config` (`id`, `classify`, `name`, `val`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('40', '3', 'man_daily_beans', '300000', '主播日龙猫豆基准 ', '1488972528', '1496888651', '1');

CREATE TABLE `guess_guard_info` (
  `guard_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '守护id',
  `anchor_id` bigint(20) DEFAULT NULL COMMENT '主播id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `is_man` smallint(8) NOT NULL DEFAULT '1' COMMENT '是否人控 1是 2否',
	`guess_id` bigint(20) DEFAULT NULL COMMENT '竞猜id',
  `is_custom` smallint(6) DEFAULT '0' COMMENT '是否自定义(1-是 2-否)',
  `sub_id` bigint(20) DEFAULT '0' COMMENT '竞猜主题id',
  `guess_title` varchar(150) DEFAULT NULL COMMENT '竞猜标题',
  `guess_content` varchar(255) DEFAULT NULL COMMENT '竞猜内容',
  `option_one` varchar(100) DEFAULT NULL COMMENT '答案选项A',
  `option_two` varchar(100) DEFAULT NULL COMMENT '答案选项B',
  `option_three` varchar(100) DEFAULT NULL COMMENT '答案选项C',
  `option_four` varchar(100) DEFAULT NULL COMMENT '答案选项D',
  `one_profit` int(11) NOT NULL COMMENT '用户压A金额',
  `two_profit` int(11) NOT NULL COMMENT '用户压B金额',
  `three_profit` int(11) NOT NULL COMMENT '用户压C金额',
  `four_profit` int(11) NOT NULL COMMENT '用户压D金额',
  `final_result` smallint(6) DEFAULT '0' COMMENT '竞猜结果：0-流局；1-A；2-B；3-C；4-D',
  `rob_profit` int(11) NOT NULL COMMENT '用户最终收益',
  `is_plus` smallint(6) NOT NULL COMMENT '增加或减少',
  `create_time` varchar(32) NOT NULL COMMENT '创建时间yyyymmdd',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`guard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='竞猜守护记录表';
/**20170922 新增**/
ALTER TABLE ui_loginrecord ADD is_first smallint(6) DEFAULT '2' COMMENT '用户是否首次登入:1是2否' AFTER login_site;
update ui_loginrecord  set is_first = 1 where id in (select id from(select min(ul.id) as id
  from ui_loginrecord ul
 GROUP BY ul.`user_id`) aa )
/**20170928 **/
ALTER TABLE gift_config_info ADD bean int(11) DEFAULT '0' COMMENT '龙猫豆（礼物价值）' AFTER coin;
update gift_config_info set bean = coin * 10;