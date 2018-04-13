/** sys_config 数据库已更新**/
INSERT INTO `sys_config` VALUES (15,'rob_set','1','是否开启全线抢庒（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (16,'h5_enter','2','是否开启H5入口（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (17,'wx_pub_pay','1','是否打开微信公众号支付（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (18,'wx_android_pay','1','是否打开Android微信支付（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (19,'wx_ios_pay','1','是否打开IOS微信支付（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (20,'alipay_android_pay','1','是否打开Android支付宝支付（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (21,'alipay_ios_pay','1','是否打开IOS支付宝支付（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (22,'inc_set','1','是否主播提现（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (23,'bean_0_set','1','是否开启用户豆换币（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (24,'coin_0_set','1','是否开启用户币换豆（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (25,'topic_set','1','是否开启自定义话题（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (26,'app_pay','1','是否开启全线APP支付（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (27,'bean_1_set','1','是否开启主播豆换币（1：开启；2：不开启）',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (28,'coin_1_set','1','是否开启主播币换豆（1：开启；2：不开启）',1488972528,1495186131,1);

delete form goods_category_info where goods_category_id = 32;
delete form goods_info where goods_id = 11;

/**定义商品类型 */
INSERT INTO `goods_type_info` VALUES (10,'支付宝娱乐直播充值',1,1488942155,1488942155,1);

/**定义商品类别 */
INSERT INTO `goods_category_info` VALUES ('32', '10', '60币', '6.00', '1', '1488942204', '1488942204', '1');
INSERT INTO `goods_category_info` VALUES ('33', '10', '300币', '30.00', '1', '1488942204', '1488942204', '1');
INSERT INTO `goods_category_info` VALUES ('34', '10', '1029币', '98.00', '1', '1488942204', '1488942204', '1');
INSERT INTO `goods_category_info` VALUES ('35', '10', '3129币', '298.00', '1', '1488942204', '1488942204', '1');
INSERT INTO `goods_category_info` VALUES ('36', '10', '6174币', '588.00', '1', '1488942204', '1488942204', '1');
INSERT INTO `goods_category_info` VALUES ('37', '10', '20979币', '1998.00', '1', '1488942204', '1488942204', '1');

/**定义商品信息*/
INSERT INTO `goods_info` VALUES ('11', '32', '60币', '0', '6.00', '1', '96', '102', '3', '1', '2', '9', '支付宝娱乐直播', '10', '支付宝娱乐直播龙猫币充值专供商品', '1488972528', '1488972528', '1');
INSERT INTO `goods_info` VALUES ('12', '33', '300币', '0', '30.00', '1', '96', '102', '3', '1', '2', '9', '支付宝娱乐直播', '10', '支付宝娱乐直播龙猫币充值专供商品', '1488972528', '1488972528', '1');
INSERT INTO `goods_info` VALUES ('13', '34', '1029币', '0', '98.00', '1', '96', '102', '3', '1', '2', '9', '支付宝娱乐直播', '10', '支付宝娱乐直播龙猫币充值专供商品', '1488972528', '1488972528', '1');
INSERT INTO `goods_info` VALUES ('14', '35', '3129币', '0', '298.00', '1', '96', '102', '3', '1', '2', '9', '支付宝娱乐直播', '10', '支付宝娱乐直播龙猫币充值专供商品', '1488972528', '1488972528', '1');
INSERT INTO `goods_info` VALUES ('15', '36', '6174币', '0', '588.00', '1', '96', '102', '3', '1', '2', '9', '支付宝娱乐直播', '10', '支付宝娱乐直播龙猫币充值专供商品', '1488972528', '1488972528', '1');
INSERT INTO `goods_info` VALUES ('16', '37', '20979币', '0', '1998.00', '1', '96', '102', '3', '1', '2', '9', '支付宝娱乐直播', '10', '支付宝娱乐直播龙猫币充值专供商品', '1488972528', '1488972528', '1');
