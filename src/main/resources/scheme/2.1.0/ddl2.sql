/**订单表增加支付平台字段**/
ALTER TABLE order_info  ADD COLUMN pay_platform smallint(6) DEFAULT NULL  COMMENT '支付平台' AFTER integral;

/**将微信公众号扫码支付订单的支付平台设为2**/
update order_info set pay_platform = 2 where pay_way = 13 and remark like '%微信公众号扫码支付%' and chl_id = 2 and pay_way is not null;

/**将支付宝娱乐直播订单的支付平台设为9，支付方式设为103**/
update order_info set pay_platform = 9,pay_way = 103 where remark like '%支付宝娱乐直播%' and chl_id = 9 and pay_way is not null;

/**将微信WAP支付（此渠道仅针对特定客户开放）订单的支付平台设为2**/
update order_info set pay_platform = 2 where pay_way = 14 and remark like '%微信WAP支付（此渠道仅针对特定客户开放）%' and chl_id = 2 and pay_way is not null;

/**将微信APP支付订单的支付平台设为2**/
update order_info set pay_platform = 2 where pay_way = 11 and remark like '%微信APP支付%' and chl_id = 2 and pay_way is not null;

/**将龙猫直播卡路里订单的支付平台设为8,支付方式设为102**/
update order_info set pay_platform = 8,pay_way =102 where chl_id = 8 and pay_way is not null;

/**将互亿无线订单的支付平台设为7,支付方式设为101**/
update order_info set pay_platform = 7,pay_way =101 where chl_id = 7 and pay_way is not null;

/**将兑吧商城订单的支付平台设为1,支付方式设为100**/
update order_info set pay_platform = 1,pay_way =100 where chl_id = 1 and pay_way is not null;

/**将苹果内支付订单的支付平台设为11,支付方式设为104**/
update order_info set pay_platform = 11,pay_way =104 where pay_way =4 and chl_id = 11 and remark like '%苹果内支付%' and pay_way is not null; 

/**将支付宝网站支付订单的支付平台设为2，支付方式为3**/
update order_info set pay_platform = 2 where pay_way =3 and remark like '%支付宝电脑网站支付%' and chl_id = 2 and pay_way is not null; 

/**将微信公众号支付订单的支付平台设为2，支付方式为12**/
update order_info set pay_platform = 2,pay_way =12 where pay_way =2 and remark like '%微信公众号龙猫币充值%' and chl_id = 2 and pay_way is not null; 
update order_info set pay_platform = 2 where pay_way =12 and remark like '%微信公众号支付%' and chl_id = 2 and pay_way is not null; 

/**将将微信APP支付订单的支付平台设为2，支付方式为11**/
update order_info set pay_platform = 2,pay_way =11 where pay_way =2 and remark like '%微信 APP龙猫币充值%' and chl_id = 2 and pay_way is not null; 

/**将支付宝App支付订单的支付平台设为2，备注修改成‘支付宝APP龙猫币充值’**/
update order_info set pay_platform = 2,remark = replace(remark, '微信公众号龙猫币充值', '支付宝APP龙猫币充值') where pay_way =1 and remark like '%微信公众号龙猫币充值%' and chl_id = 2 and pay_way is not null; 

/**将商品卖家ID修改为100**/
update goods_info set seller_uid = 100;
/**将订单卖家ID修改为100**/
update order_info set seller_uid = 100;

/**将商品类型微信公众号充值改为龙猫豆充值**/
update goods_type_info set name='龙猫豆充值' where goods_type_id =4;

/**替换字符串**/
update order_info set remark = replace(remark, '微信 APP龙猫币充值', '微信APP龙猫币充值') where pay_way is not null; 
update order_info set remark = replace(remark, '龙猫币充值', '支付') where pay_way is not null; 

/**将支付方式为空的订单(未支付的订单)中的支付平台设为空**/
update order_info set pay_platform = null where pay_way is null;

/**修改充值送活动结束时间**/
update co_activity set end_time ='2017-12-23 00:00:00' where id = '999999';
/* order_info 支付平台 已更新 */
update order_info set pay_platform = 2 where `pay_way` in (1,2);
update order_info set pay_platform = 2, pay_way = 12 where remark like '%微信公众号支付%' and chl_id = 2 and pay_way is not null;
update order_info set pay_platform = 2, pay_way = 11 where remark like '%微信APP支付%' and chl_id = 2 and pay_way is not null;
update order_info set pay_platform = 2, pay_way = 2 where remark like '%支付宝手机网页支付%' and chl_id = 2 and `order_status` = '4' AND `delivery_status` = '3';
update order_info set pay_platform = 2, pay_way = 14 where remark like '%微信WAP支付%' and chl_id = 2 and `order_status` = '4' AND `delivery_status` = '3';
update order_info set pay_platform = 2, pay_way = 2 where remark like '%支付宝手机网页龙猫币充值%' and chl_id = 2 and `order_status` = '4' AND `delivery_status` = '3';
update order_info set pay_platform = 2, pay_way = 3 where remark like '%支付宝电脑网站支付%' and chl_id = 2 and `order_status` = '4' AND `delivery_status` = '3';
update order_info set pay_platform = 2, pay_way = 13 where remark like '%微信公众号扫码支付%' and chl_id = 2 and `order_status` = '4' AND `delivery_status` = '3';