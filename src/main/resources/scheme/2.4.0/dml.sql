/** 系统配置表 2018.01.30 未更新**/
INSERT INTO `sys_config` VALUES (41,0,'android_shop','1','安卓是否打开商城页面(1:开启;2:不开启)',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (42,2,'pc_pay_alipay_pc_direct','1','支付宝PC网页支付(1:开启;2:不开启)',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (43,2,'pc_pay_wx_pub_qr','1','微信PC网页支付(1:开启;2:不开启)',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (44,2,'wap_pay_alipay_wap','1','支付宝WAP网页支付(1:开启;2:不开启)',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (45,2,'wap_pay_wx_wap','1','微信WAP网页支付(1:开启;2:不开启)',1488972528,1495186131,1);
INSERT INTO `sys_config` VALUES (46,3,'app_barrage','1','是否开启直播弹幕(1:开启;2:不开启)',1488972528,1495186131,1);


-- 写入 迎新春砸金蛋 的砸蛋次数   hjy 2018年3月2日11:09:46
update act_config SET other_parameter = '5',other_parameter_explain='每天每人砸蛋次数' WHERE activity_type = 6;
-- 写入 砸蛋活动 蛋的金额   hjy 2018年3月2日11:09:46
update goods_category_info SET price = '5000000' WHERE goods_category_id = 49;
update goods_category_info SET price = '1000000' WHERE goods_category_id = 50;
update goods_category_info SET price = '100000' WHERE goods_category_id = 51;