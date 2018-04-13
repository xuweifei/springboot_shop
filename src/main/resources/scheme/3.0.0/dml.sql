/* 直播版块配置 live_forum_func 2018.03.06 未更新 */
INSERT INTO `live_forum_func` VALUES (1,'竞猜',1,1520216796,1520216796,1),
(2,'购物',1,1520216796,1520216796,1);
/* 敏感词汇sensitive_word 2018.03.19 UPDATE */
UPDATE sensitive_word set is_use = 1;
UPDATE sensitive_word set ctime = 1521444784;
UPDATE sensitive_word set utime = 1521444784;
/* 敏感词汇sensitive_pause 2018.03.19 UPDATE **/
UPDATE sensitive_pause set is_use = 1;
UPDATE sensitive_pause set ctime = 1521444784;
UPDATE sensitive_pause set utime = 1521444784;

/* 主播等级规则 anchor_grade_rule 2018.03.27 INSERT */
INSERT INTO `anchor_grade_rule` VALUES 
(1492420162804556,1,'https://lmfile.tvlongmao.com/anchor_grade_icon_1.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',0,10000,'初入新星','初入新星',0,1521450533,1521698275,1),
(1492420595719309,2,'https://lmfile.tvlongmao.com/anchor_grade_icon_2.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',10000,20000,'初入新星','四角星',0,1492420677,1521684910,1),
(1492420836305422,3,'https://lmfile.tvlongmao.com/anchor_grade_icon_3.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',20000,40000,'初入新星','四角星',0,1492420878,1521695758,1),
(1492420941434163,4,'https://lmfile.tvlongmao.com/anchor_grade_icon_4.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',40000,80000,'初入新星','四角星',0,1492420953,1521686580,1),
(1492427543681267,5,'https://lmfile.tvlongmao.com/anchor_grade_icon_5.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',80000,160000,'初入新星','四角星',0,1492427923,1521684973,1),
(1492427543681268,6,'https://lmfile.tvlongmao.com/anchor_grade_icon_6.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',160000,320000,'初入新星','四角星',0,1492427957,1512974214,1),
(1492427543681269,7,'https://lmfile.tvlongmao.com/anchor_grade_icon_7.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',320000,640000,'初入新星','四角星',0,1492427982,1512974220,1),
(1492427543681270,8,'https://lmfile.tvlongmao.com/anchor_grade_icon_8.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',640000,1880000,'初入新星','四角星',0,1492427996,1512974224,1),
(1492427543681271,9,'https://lmfile.tvlongmao.com/anchor_grade_icon_9.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',1880000,2880000,'初入新星','四角星',0,1492428013,1512974229,1),
(1492427543681272,10,'https://lmfile.tvlongmao.com/anchor_grade_icon_10.png','https://lmfile.tvlongmao.com/anchor_honor_1.png',2880000,3880000,'初入新星','四角星',0,1492428030,1512974233,1),
(1492427543681273,11,'https://lmfile.tvlongmao.com/anchor_grade_icon_11.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',3880000,4880000,'人气偶像','五角星',0,1492428053,1512974248,1),
(1492427543681274,12,'https://lmfile.tvlongmao.com/anchor_grade_icon_12.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',4880000,5880000,'人气偶像','五角星',0,1492428075,1512974255,1),
(1492427543681275,13,'https://lmfile.tvlongmao.com/anchor_grade_icon_13.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',5880000,6880000,'人气偶像','五角星',0,1492428090,1512974261,1),
(1492427543681276,14,'https://lmfile.tvlongmao.com/anchor_grade_icon_14.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',6880000,7880000,'人气偶像','五角星',0,1492428105,1512974267,1),
(1492427543681277,15,'https://lmfile.tvlongmao.com/anchor_grade_icon_15.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',7880000,8880000,'人气偶像','五角星',0,1492428124,1512974273,1),
(1492427543681278,16,'https://lmfile.tvlongmao.com/anchor_grade_icon_16.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',8880000,9880000,'人气偶像','五角星',0,1492428139,1512974281,1),
(1492427543681279,17,'https://lmfile.tvlongmao.com/anchor_grade_icon_17.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',9880000,10880000,'人气偶像','五角星',0,1492428189,1512974287,1),
(1492427543681280,18,'https://lmfile.tvlongmao.com/anchor_grade_icon_18.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',10880000,11880000,'人气偶像','五角星',0,1492428213,1512974294,1),
(1492427543681281,19,'https://lmfile.tvlongmao.com/anchor_grade_icon_19.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',11880000,12880000,'人气偶像','五角星',0,1492428229,1512974301,1),
(1492427543681282,20,'https://lmfile.tvlongmao.com/anchor_grade_icon_20.png','https://lmfile.tvlongmao.com/anchor_honor_2.png',12880000,14880000,'人气偶像','五角星',0,1492428244,1512974307,1),
(1492427543681283,21,'https://lmfile.tvlongmao.com/anchor_grade_icon_21.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',14880000,17880000,'实力艺人','六角星',0,1492428261,1512974328,1),
(1492427543681284,22,'https://lmfile.tvlongmao.com/anchor_grade_icon_22.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',17880000,21880000,'实力艺人','六角星',0,1492428298,1512974333,1),
(1492427543681285,23,'https://lmfile.tvlongmao.com/anchor_grade_icon_23.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',21880000,26880000,'实力艺人','六角星',0,1492428326,1512974339,1),
(1492427543681286,24,'https://lmfile.tvlongmao.com/anchor_grade_icon_24.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',26880000,32880000,'实力艺人','六角星',0,1492428418,1512974345,1),
(1492427543681287,25,'https://lmfile.tvlongmao.com/anchor_grade_icon_25.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',32880000,39880000,'实力艺人','六角星',0,1492428434,1512974367,1),
(1512964489619469,26,'https://lmfile.tvlongmao.com/anchor_grade_icon_26.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',39880000,47880000,'实力艺人','六角星',0,1512973388,1512974359,1),
(1512964489619470,27,'https://lmfile.tvlongmao.com/anchor_grade_icon_27.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',47880000,56880000,'实力艺人','六角星',0,1512973468,1512974374,1),
(1512964489619471,28,'https://lmfile.tvlongmao.com/anchor_grade_icon_28.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',56880000,66880000,'实力艺人','六角星',0,1512973486,1512974381,1),
(1512964489619472,29,'https://lmfile.tvlongmao.com/anchor_grade_icon_29.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',66880000,77880000,'实力艺人','六角星',0,1512973504,1512974387,1),
(1512964489619473,30,'https://lmfile.tvlongmao.com/anchor_grade_icon_30.png','https://lmfile.tvlongmao.com/anchor_honor_3.png',77880000,89880000,'实力艺人','六角星',0,1512973530,1512974409,1),
(1512964489619474,31,'https://lmfile.tvlongmao.com/anchor_grade_icon_31.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',89880000,102880000,'超级巨星','钻石',0,1512973556,1512974422,1),
(1512964489619475,32,'https://lmfile.tvlongmao.com/anchor_grade_icon_32.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',102880000,116880000,'超级巨星','钻石',0,1512973579,1512974444,1),
(1512964489619476,33,'https://lmfile.tvlongmao.com/anchor_grade_icon_33.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',116880000,131880000,'超级巨星','钻石',0,1512973592,1512974449,1),
(1512964489619477,34,'https://lmfile.tvlongmao.com/anchor_grade_icon_34.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',131880000,147880000,'超级巨星','钻石',0,1512973648,1512974454,1),
(1512964489619478,35,'https://lmfile.tvlongmao.com/anchor_grade_icon_35.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',147880000,164880000,'超级巨星','钻石',0,1512973663,1512974459,1),
(1512964489619479,36,'https://lmfile.tvlongmao.com/anchor_grade_icon_36.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',164880000,182880000,'超级巨星','钻石',0,1512973676,1512974465,1),
(1512964489619480,37,'https://lmfile.tvlongmao.com/anchor_grade_icon_37.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',182880000,201880000,'超级巨星','钻石',0,1512973692,1521687429,1),
(1512964489619481,38,'https://lmfile.tvlongmao.com/anchor_grade_icon_38.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',201880000,221880000,'超级巨星','钻石',0,1512973719,1522056263,1),
(1512964489619482,39,'https://lmfile.tvlongmao.com/anchor_grade_icon_39.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',221880000,251880000,'超级巨星','钻石',0,1512973731,1512974480,1),
(1512964489619483,40,'https://lmfile.tvlongmao.com/anchor_grade_icon_40.png','https://lmfile.tvlongmao.com/anchor_honor_4.png',251880000,291880000,'超级巨星','钻石',0,1512973771,1512974485,1),
(1512964489619484,41,'https://lmfile.tvlongmao.com/anchor_grade_icon_41.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',291880000,341880000,'传奇巨星','皇冠',0,1512973786,1521687394,1),
(1512964489619485,42,'https://lmfile.tvlongmao.com/anchor_grade_icon_42.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',341880000,401880000,'传奇巨星','皇冠',0,1512973803,1521695734,1),
(1512964489619486,43,'https://lmfile.tvlongmao.com/anchor_grade_icon_43.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',401880000,471880000,'传奇巨星','皇冠',0,1512973828,1521687190,1),
(1512964489619487,44,'https://lmfile.tvlongmao.com/anchor_grade_icon_44.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',471880000,551880000,'传奇巨星','皇冠',0,1521536290,1521686562,1),
(1512964489619488,45,'https://lmfile.tvlongmao.com/anchor_grade_icon_45.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',551880000,641880000,'传奇巨星','皇冠',0,1521536288,1521686553,1),
(1512964489619489,46,'https://lmfile.tvlongmao.com/anchor_grade_icon_46.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',641880000,741880000,'传奇巨星','皇冠',0,1521536285,1521686535,1),
(1512964489619490,47,'https://lmfile.tvlongmao.com/anchor_grade_icon_47.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',741880000,941880000,'传奇巨星','皇冠',0,1521536283,1522049568,1),
(1512964489619491,48,'https://lmfile.tvlongmao.com/anchor_grade_icon_48.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',941880000,1241880000,'传奇巨星','皇冠',0,1521536281,1522638782,1),
(1512964489619492,49,'https://lmfile.tvlongmao.com/anchor_grade_icon_49.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',1241880000,1641880000,'传奇巨星','皇冠',0,1522638764,1522638723,1),
(1512964489619493,50,'https://lmfile.tvlongmao.com/anchor_grade_icon_50.png','https://lmfile.tvlongmao.com/anchor_honor_5.png',1641880000,2141880000,'传奇巨星','皇冠',1,1522638675,1522049596,1);
/*增加短信模版配置*/
INSERT INTO `totorosports`.`lm_sms_config` (`sms_id`, `sms_mark`, `service_name`, `sms_name`, `sms_template`, `sms_addressee`, `sms_receiver`, `sms_status`, `remark`, `ctime`, `utime`, `is_delete`) VALUES ('14', 'register_totoro', 'tototo_user', '用户注册', '提醒您，您正在注册新用户，验证码：[messagecode]如非本人操作，请忽略本消息。', '操作人手机号', '操作人', '1', '验证码：[messagecode]', '1515549218', '1515549218', '1');

