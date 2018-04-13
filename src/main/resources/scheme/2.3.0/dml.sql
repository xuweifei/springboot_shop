/* prod账户sig */
INSERT INTO `app_admin_sig` VALUES (1,'prod',1400011093,'adminlm','eJxlj9FKwzAUhu-7FKG3ikvSRTbBC5ljZp2CrhXmTYhNOqNLGtq0XSe*u103MOC5-b7z-*d8BwCAMFmtr3iWFbVxzHVWhuAGhDC8-IPWKsG4Y1Ep-kG5t6qUjOdOlgNEhBAMoe8oIY1TuTobXGhldtoTKvHFhpZTwrhfRwhOI19R2wE*ztMZfb5Hto9MRq80n23SHTmIxd0SF*3qodFxhzCBusb43XCyaek2fUI0mUK*QBcCqfozdjTfowxfw-lo8haLNsEvy7r5wF1161U6peX5peNJYzIhHm1kWanCDAKGvYIjeJww*Al*AbSWXQk_','2017-12-23 12:49:45');
/* 游戏奖金池账户 */
INSERT INTO `ui_user` VALUES 
('LM666666','666666',3,0,'66666666666',NULL,NULL,NULL,NULL,NULL,NULL,'66666666666','游戏储备池',NULL,0,1,NULL,0,NULL,NULL,NULL,'2017-01-01 01:01:01','666666','2017-01-01 01:01:01','666666','0','eJxlzlFPgzAQwPF66','2017-01-01 01:01:01','保密',NULL,'用于小游戏奖金池储备奖金',1);
INSERT INTO `ui_coin_account` VALUES ('99998','666666',0,'2017-01-01 01:01:01','666666','2017-01-01 01:01:01','666666');
INSERT INTO `ui_bean_account` VALUES ('99998','666666', 100000000,'2017-01-01 01:01:01','666666','2017-01-01 01:01:01', '666666');



/* 公会新添加启用停用状态,创建字段并更新之前的字段值为1 scj */
ALTER TABLE `broker_confraternity` add COLUMN `is_enable`  smallint(6) NOT NULL DEFAULT 1 COMMENT '是否启用:1,是;2:否';
update broker_confraternity SET is_enable=1;
/* user表新添加备注字段,运营有时候需要写备注  scj */
ALTER TABLE ui_user ADD column remark VARCHAR(66) default '' COMMENT '备注' AFTER push_id;
INSERT INTO `ui_bean_change_record` VALUES ('99998','666666',0,0,100000000,0,'99998','2017-01-01 01:01:01','','2017-01-01 01:01:01','');

INSERT INTO `ui_user` VALUES 
('LM999999','999999',3,0,'99999999999',NULL,NULL,NULL,NULL,NULL,NULL,'99999999999','游戏回收池',NULL,0,1,NULL,0,NULL,NULL,NULL,'2017-01-01 01:01:01','999999','2017-01-01 01:01:01','999999','0','eJxlzlFPgzAQwPF99','2017-01-01 01:01:01','保密',NULL,'用于回收小游戏奖金池溢出奖金',1);
INSERT INTO `ui_coin_account` VALUES ('99997','999999',0,'2017-01-01 01:01:01','999999','2017-01-01 01:01:01','999999');
INSERT INTO `ui_bean_account` VALUES ('99997','999999',0,'2017-01-01 01:01:01','999999','2017-01-01 01:01:01', '999999');
update `ui_user` set nick_name = '总出账户', remark = '竞猜,游戏等出豆账户' where user_id = '777777';
update `ui_user` set nick_name = '总进账户', remark = '竞猜,游戏,提现等进豆账户' where user_id = '888888';
update live_play_info set start_time = ctime, end_time = utime;


delete from live_config;
INSERT INTO `live_config` VALUES (1,'1','anchor_share_title','嘿！你的好友【Anchor】正在直播~','',1505450770,1505450770,1);
INSERT INTO `live_config` VALUES (2,'1','anchor_share_content','看直播，玩竞猜，豆子还能换话费，你还不快来!','',1505450772,1505450772,1);
INSERT INTO `live_config` VALUES (3,'1','user_share_title','我在【Anchor】的直播间玩竞猜,快来围观~','',1505450774,1505450774,1);
INSERT INTO `live_config` VALUES (4,'1','user_share_content','看直播，玩竞猜，豆子还能换话费，你还不快来!','',1505450776,1505450776,1);




-- 插入迎新春砸金蛋活动配置数据 hjy 2018年1月11日15:09:46
INSERT INTO `totorosports`.`act_config` (`config_id`, `activity_type`, `name`, `content`, `pic_url`, `reward_type`, `start_time`, `end_time`, `status`, `ctime`, `utime`, `is_delete`) 
VALUES ('1513048763854988', '6', '迎新春砸金蛋', '活动期间，用户每天累计押注的龙猫豆可兑换不同的蛋', 'https://qnimg.tvlongmao.com/3cbb46dc9a6c4abebd560cf04cb6153d.JPG', '3', '2018-01-16 00:00:00', '2018-03-03 00:00:00', '1', '1515634287', '1515634287', '1');

/* 增加商品种类，商品信息 2018.01.13 */
INSERT INTO `goods_category_info` VALUES (52,12,'1260豆',18.00,1,1515811496,1515811496,1),
(53,12,'4200豆',60.00,1,1515811498,1515811498,1);

INSERT INTO `goods_info` VALUES (43,52,'1260豆',0,18.00,1,100,100,3,1,2,11,'苹果内支付',12,'苹果内支付18元1260豆',1515811780,1515811780,1),
(44,53,'4200豆',0,60.00,1,100,100,3,1,2,11,'苹果内支付',12,'苹果内支付60元4200豆',1515811782,1515811782,1);



-- 插入迎新春砸金蛋活动的商品数据 hjy 2018年1月15日10:09:46
	DELETE FROM goods_info WHERE goods_id in (31,32,33,34,35,36,37,38,39,40,41,42);
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('31', '49', 'Macbook pro', '0', '6988', '0', '0', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('32', '49', 'oppo A37m 16G', '0', '1199', '0', '0', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('33', '49', '50000龙猫豆', '0', '500', '50000', '10', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('34', '49', '30000龙猫豆', '0', '300', '30000', '90', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');

	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('35', '50', '150元中石化油卡', '0', '150', '0', '0', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('36', '50', '10000龙猫豆', '0', '100', '10000', '5', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('37', '50', '5000龙猫豆', '0', '50', '5000', '30', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('38', '50', '2000龙猫豆', '0', '20', '2000', '65', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');

	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('39', '51', '龙猫公仔', '0', '20', '0', '0', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('40', '51', '1500龙猫豆', '0', '15', '1500', '5', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('41', '51', '1000龙猫豆', '0', '10', '1000', '40', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('42', '51', '500龙猫豆', '0', '5', '500', '55', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1515982503', '1515982503');

