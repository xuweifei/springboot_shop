-- 插入充值送活动配置数据 yst 2017年12月12日15:11:45
INSERT INTO `totorosports`.`act_config` (`config_id`, `activity_type`, `name`, `content`, `pic_url`, `reward_type`, `start_time`, `end_time`, `status`, `ctime`, `utime`, `is_delete`) 
VALUES ('1513048763854986', '5', '充值送活动', '用户在活动期间内充值龙猫币大于98元，将赠送5%的龙猫币（自定义时四舍五入）', 'test.jpg', '2', '2016-11-27 21:56:28', '2017-12-23 00:00:00', '1', '1513062269', '1513062269', '1');

-- 插入双蛋活动配置数据 hjy 2017年12月12日15:09:46
INSERT INTO `totorosports`.`act_config` (`config_id`, `activity_type`, `name`, `content`, `pic_url`, `reward_type`, `start_time`, `end_time`, `status`, `ctime`, `utime`, `is_delete`) 
VALUES ('1513048763854987', '4', '双蛋活动', '活动期间，用户累计押注的龙猫豆可兑换不同的蛋', 'test.jpg', '3', '2017-12-24 00:00:00', '2018-1-1 23:59:59', '1', '1513062269', '1513062269', '1');


-- 插入商品类别 goods_category_info hjy 2017年12月12日15:09:46
	INSERT INTO `goods_category_info` (`goods_category_id`, `goods_type_id`, `name`, `price`, `is_enable`, `ctime`, `utime`) VALUES ('49', '11', '双蛋活动-金蛋礼物', '0', '1', '1488942204', '1488942204');
	INSERT INTO `goods_category_info` (`goods_category_id`, `goods_type_id`, `name`, `price`, `is_enable`, `ctime`, `utime`) VALUES ('50', '11', '双蛋活动-银蛋礼物', '0', '1', '1488942204', '1488942204');
	INSERT INTO `goods_category_info` (`goods_category_id`, `goods_type_id`, `name`, `price`, `is_enable`, `ctime`, `utime`) VALUES ('51', '11', '双蛋活动-铜蛋礼物', '0', '1', '1488942204', '1488942204');

-- 插入商品 goods_info hjy 2017年12月12日15:09:46
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('31', '49', 'Macbook pro', '0', '6988', '0', '0', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('32', '49', 'oppo A37m 16G', '0', '1199', '0', '1', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('33', '49', '50000龙猫豆', '0', '500', '50000', '49', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('34', '49', '30000龙猫豆', '0', '300', '30000', '50', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');

	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('35', '50', '300元中石化油卡', '0', '300', '0', '1', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('36', '50', '20000龙猫豆', '0', '200', '20000', '9', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('37', '50', '10000龙猫豆', '0', '100', '10000', '40', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('38', '50', '5000龙猫豆', '0', '50', '5000', '50', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');

	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('39', '51', '龙猫公仔', '0', '30', '0', '5', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('40', '51', '2000龙猫豆', '0', '20', '2000', '15', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('41', '51', '1500龙猫豆', '0', '15', '1500', '40', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');
	INSERT INTO `goods_info` (`goods_id`, `goods_category_id`, `name`, `current_storage`, `price`, `price_num`, `discount`, `seller_uid`, `audit_status`, `is_enable`, `chl_id`, `chl_name`, `goods_type_id`, `remark`, `ctime`, `utime`) VALUES ('42', '51', '1000龙猫豆', '0', '10', '1000', '40', '0', '3', '1', '10', '活动服务', '11', '活动抽奖专供商品', '1488972528', '1488972528');



	





