/**定义主播加分规则 */
INSERT INTO `anchor_plus_rule` VALUES ('1', '本月没有虚假竞猜', '0', '+1', '1488972528', '1488972528', '1');
INSERT INTO `anchor_plus_rule` VALUES ('2', '本月直播时长[condition]小时以上', '100', '+1', '1488972528', '1488972528', '1');
INSERT INTO `anchor_plus_rule` VALUES ('3', '本月竞猜数量在[condition]场以上', '800', '+1', '1488972528', '1488972528', '1');
INSERT INTO `anchor_plus_rule` VALUES ('4', '本月竞猜数量在[condition]场以上（仅对较差等级主播）', '1000', '+2', '1488972528', '1488972528', '1');
INSERT INTO `anchor_plus_rule` VALUES ('5', '本月竞猜数量在[condition]场以上（仅对较差等级主播）', '1500', '+5', '1488972528', '1488972528', '1');
INSERT INTO `anchor_plus_rule` VALUES ('6', '额外奖励,视情节给予适当加分', '0', '0', '1488972528', '1488972528', '1');

/**定义主播减分规则 */
INSERT INTO `anchor_deduction_rule` VALUES ('1', '竞猜流局占总局数[condition]', '5%-10%', '-2', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('2', '竞猜流局占总局数>[condition]', '10%', '-5', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('3', '广告欺骗，经核实', '1', '-10', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('4', '骚扰谩骂，经核实', '1', '-10', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('5', '虚假竞猜，经核实', '1', '降档', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('6', '淫秽色情，经核实', '1', '封号', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('7', '反动政治，经核实', '1', '封号', '1488972528', '1488972528', '1');
INSERT INTO `anchor_deduction_rule` VALUES ('8', '其他内容', '0', '视情节严重程度进行处罚', '1488972528', '1488972528', '1');

/**定义[全民主播]信誉等级提成规则 */
INSERT INTO `anchor_credit_rule` VALUES (1,'极好',95,100,70,'主播等级为[min_val]-[min_val],礼物分成为70%',1488972528,1488972528,1);
INSERT INTO `anchor_credit_rule` VALUES (2,'优秀',85,94,60,'主播等级为[min_val]-[min_val],礼物分成为60%',1488972528,1488972528,1);
INSERT INTO `anchor_credit_rule` VALUES (3,'良好（初始等级）',75,84,50,'主播等级为[min_val]-[min_val],礼物分成为50%',1488972528,1488972528,1);
INSERT INTO `anchor_credit_rule` VALUES (4,'中等',65,74,40,'主播等级为[min_val]-[min_val],礼物分成为40%',1488972528,1488972528,1);
INSERT INTO `anchor_credit_rule` VALUES (5,'较差',60,64,30,'主播等级为[min_val]-[min_val],礼物分成为30%',1488972528,1488972528,1);
INSERT INTO `anchor_credit_rule` VALUES (6,'极差',0,59,70,'主播等级为[min_val]-[min_val],冻结开播权限30天（30天后可重新申请）',1488972528,1488972528,1);

/** 定义渠道 **/
INSERT INTO `chl_info` VALUES (8,'龙猫直播卡路里',1,1488942155,1488942155,1);
INSERT INTO `chl_info` VALUES (9,'支付宝',1,1488942155,1488942155,1);

/** 定义商品类型 **/
INSERT INTO `goods_type_info` VALUES (9,'卡路里兑换RMB',1,1488942155,1488942155,1);
INSERT INTO `goods_type_info` VALUES (10,'支付宝',1,1488942155,1488942155,1);

/** 定义 商品分类 **/
INSERT INTO `goods_category_info` VALUES (31,9,'卡路里兑换',0.00,1,1488942155,1488942155,1);
INSERT INTO `goods_category_info` VALUES (32,10,'支付宝',0.00,1,1488942155,1488942155,1);

/** 定义商品信息 **/
INSERT INTO `goods_info` VALUES (10,31,'卡路里兑换RMB',0,0.00,1,100,8,3,2,2,8,'龙猫直播卡路里',9,'龙猫直播卡路里',1488972528,1488972528,1);
INSERT INTO `goods_info` VALUES (11,32,'支付宝',0,0.00,1,100,9,3,2,2,9,'支付宝',10,'支付宝',1488972528,1488972528,1);
/********************************* SNAPSHOT-1.8.2 ************************************************************/
/** 定义靓号信息 **/
INSERT INTO `good_number` VALUES (1,12345678,'珍藏号段',1),(2,23456790,'珍藏号段',1),(3,34567890,'珍藏号段',1),(4,11111111,'顶级号段',1),(5,33333333,'顶级号段',1),(6,55555555,'顶级号段',1),(7,66666666,'顶级号段',1),(8,77777777,'顶级号段',1),(9,88888888,'顶级号段',1),(10,99999999,'顶级号段',1),(11,10011001,'次级号段',1),(12,10011234,'次级号段',1),(13,10012345,'次级号段',1),(14,10013456,'次级号段',1),(15,10015678,'次级号段',1),(16,10016789,'次级号段',1),(17,10017890,'次级号段',1),(18,10101010,'次级号段',1),(19,10101234,'次级号段',1),(20,10102345,'次级号段',1),(21,10103456,'次级号段',1),(22,10105678,'次级号段',1),(23,10106789,'次级号段',1),(24,10107890,'次级号段',1),(25,11001100,'次级号段',1),(26,11001234,'次级号段',1),(27,11002345,'次级号段',1),(28,11003456,'次级号段',1),(29,11005678,'次级号段',1),(30,11006789,'次级号段',1),(31,11007890,'次级号段',1),(32,11011011,'次级号段',1),(33,11111234,'次级号段',1),(34,11112345,'次级号段',1),(35,11113456,'次级号段',1),(36,11115678,'次级号段',1),(37,11116789,'次级号段',1),(38,11117890,'次级号段',1),(39,20021234,'次级号段',1),(40,20022345,'次级号段',1),(41,20023456,'次级号段',1),(42,20025678,'次级号段',1),(43,20026789,'次级号段',1),(44,20027890,'次级号段',1),(45,20201234,'次级号段',1),(46,20202345,'次级号段',1),(47,20203456,'次级号段',1),(48,20205678,'次级号段',1),(49,20206789,'次级号段',1),(50,20207890,'次级号段',1),(51,22001234,'次级号段',1),(52,22002345,'次级号段',1),(53,22003456,'次级号段',1),(54,22005678,'次级号段',1),(55,22006789,'次级号段',1),(56,22007890,'次级号段',1),(57,22221234,'次级号段',1),(58,22222345,'次级号段',1),(59,22223456,'次级号段',1),(60,22225678,'次级号段',1),(61,22226789,'次级号段',1),(62,22227890,'次级号段',1),(63,30031234,'次级号段',1),(64,30032345,'次级号段',1),(65,30033003,'次级号段',1),(66,30033456,'次级号段',1),(67,30035678,'次级号段',1),(68,30036789,'次级号段',1),(69,30037890,'次级号段',1),(70,30301234,'次级号段',1),(71,30302345,'次级号段',1),(72,30303030,'次级号段',1),(73,30303456,'次级号段',1),(74,30305678,'次级号段',1),(75,30306789,'次级号段',1),(76,30307890,'次级号段',1),(77,33001234,'次级号段',1),(78,33002345,'次级号段',1),(79,33003300,'次级号段',1),(80,33003456,'次级号段',1),(81,33005678,'次级号段',1),(82,33006789,'次级号段',1),(83,33007890,'次级号段',1),(84,33033033,'次级号段',1),(85,33331234,'次级号段',1),(86,33332345,'次级号段',1),(87,33333456,'次级号段',1),(88,33335678,'次级号段',1),(89,33336789,'次级号段',1),(90,33337890,'次级号段',1),(91,40041234,'次级号段',1),(92,40042345,'次级号段',1),(93,40043456,'次级号段',1),(94,40045678,'次级号段',1),(95,40046789,'次级号段',1),(96,40047890,'次级号段',1),(97,40401234,'次级号段',1),(98,40402345,'次级号段',1),(99,40403456,'次级号段',1),(100,40405678,'次级号段',1),(101,40406789,'次级号段',1),(102,40407890,'次级号段',1),(103,44001234,'次级号段',1),(104,44002345,'次级号段',1),(105,44003456,'次级号段',1),(106,44005678,'次级号段',1),(107,44006789,'次级号段',1),(108,44007890,'次级号段',1),(109,44441234,'次级号段',1),(110,44442345,'次级号段',1),(111,44443456,'次级号段',1),(112,44445678,'次级号段',1),(113,44446789,'次级号段',1),(114,44447890,'次级号段',1),(115,50051234,'次级号段',1),(116,50052345,'次级号段',1),(117,50053456,'次级号段',1),(118,50055005,'次级号段',1),(119,50055678,'次级号段',1),(120,50056789,'次级号段',1),(121,50057890,'次级号段',1),(122,50501234,'次级号段',1),(123,50502345,'次级号段',1),(124,50503456,'次级号段',1),(125,50505050,'次级号段',1),(126,50505678,'次级号段',1),(127,50506789,'次级号段',1),(128,50507890,'次级号段',1),(129,55001234,'次级号段',1),(130,55002345,'次级号段',1),(131,55003456,'次级号段',1),(132,55005500,'次级号段',1),(133,55005678,'次级号段',1),(134,55006789,'次级号段',1),(135,55007890,'次级号段',1),(136,55055055,'次级号段',1),(137,55551234,'次级号段',1),(138,55552345,'次级号段',1),(139,55553456,'次级号段',1),(140,55555678,'次级号段',1),(141,55556789,'次级号段',1),(142,55557890,'次级号段',1),(143,60061234,'次级号段',1),(144,60062345,'次级号段',1),(145,60063456,'次级号段',1),(146,60065678,'次级号段',1),(147,60066006,'次级号段',1),(148,60066789,'次级号段',1),(149,60067890,'次级号段',1),(150,60601234,'次级号段',1),(151,60602345,'次级号段',1),(152,60603456,'次级号段',1),(153,60605678,'次级号段',1),(154,60606060,'次级号段',1),(155,60606789,'次级号段',1),(156,60607890,'次级号段',1),(157,66001234,'次级号段',1),(158,66002345,'次级号段',1),(159,66003456,'次级号段',1),(160,66005678,'次级号段',1),(161,66006600,'次级号段',1),(162,66006789,'次级号段',1),(163,66007890,'次级号段',1),(164,66066066,'次级号段',1),(165,66661234,'次级号段',1),(166,66662345,'次级号段',1),(167,66663456,'次级号段',1),(168,66665678,'次级号段',1),(169,66666789,'次级号段',1),(170,66667890,'次级号段',1),(171,70071234,'次级号段',1),(172,70072345,'次级号段',1),(173,70073456,'次级号段',1),(174,70075678,'次级号段',1),(175,70076789,'次级号段',1),(176,70077007,'次级号段',1),(177,70077890,'次级号段',1),(178,70701234,'次级号段',1),(179,70702345,'次级号段',1),(180,70703456,'次级号段',1),(181,70705678,'次级号段',1),(182,70706789,'次级号段',1),(183,70707070,'次级号段',1),(184,70707890,'次级号段',1),(185,77001234,'次级号段',1),(186,77002345,'次级号段',1),(187,77003456,'次级号段',1),(188,77005678,'次级号段',1),(189,77006789,'次级号段',1),(190,77007700,'次级号段',1),(191,77007890,'次级号段',1),(192,77077077,'次级号段',1),(193,77771234,'次级号段',1),(194,77772345,'次级号段',1),(195,77773456,'次级号段',1),(196,77775678,'次级号段',1),(197,77776789,'次级号段',1),(198,77777890,'次级号段',1),(199,80081234,'次级号段',1),(200,80082345,'次级号段',1),(201,80083456,'次级号段',1),(202,80085678,'次级号段',1),(203,80086789,'次级号段',1),(204,80087890,'次级号段',1),(205,80088008,'次级号段',1),(206,80801234,'次级号段',1),(207,80802345,'次级号段',1),(208,80803456,'次级号段',1),(209,80805678,'次级号段',1),(210,80806789,'次级号段',1),(211,80807890,'次级号段',1),(212,80808080,'次级号段',1),(213,88001234,'次级号段',1),(214,88002345,'次级号段',1),(215,88003456,'次级号段',1),(216,88005678,'次级号段',1),(217,88006789,'次级号段',1),(218,88007890,'次级号段',1),(219,88008800,'次级号段',1),(220,88088088,'次级号段',1),(221,88881234,'次级号段',1),(222,88882345,'次级号段',1),(223,88883456,'次级号段',1),(224,88885678,'次级号段',1),(225,88886789,'次级号段',1),(226,88887890,'次级号段',1),(227,90091234,'次级号段',1),(228,90092345,'次级号段',1),(229,90093456,'次级号段',1),(230,90095678,'次级号段',1),(231,90096789,'次级号段',1),(232,90097890,'次级号段',1),(233,90099009,'次级号段',1),(234,90901234,'次级号段',1),(235,90902345,'次级号段',1),(236,90903456,'次级号段',1),(237,90905678,'次级号段',1),(238,90906789,'次级号段',1),(239,90907890,'次级号段',1),(240,90909090,'次级号段',1),(241,99001234,'次级号段',1),(242,99002345,'次级号段',1),(243,99003456,'次级号段',1),(244,99005678,'次级号段',1),(245,99006789,'次级号段',1),(246,99007890,'次级号段',1),(247,99009900,'次级号段',1),(248,99099099,'次级号段',1),(249,99991234,'次级号段',1),(250,99992345,'次级号段',1),(251,99993456,'次级号段',1),(252,99995678,'次级号段',1),(253,99996789,'次级号段',1),(254,99997890,'次级号段',1);
/** 定义 sys_config 信息 **/
INSERT INTO `sys_config` VALUES (1,'ios_pay','2','是否打开苹果支付（1：开启；2：不开启）',1488972528,1488972528,1),(2,'live_set','1','是否开启全线直播（1：开启；2：不开启）',1488972528,1495179766,1),(3,'guess_set','1','是否开启全线竞猜（1：开启；2：不开启）',1488972528,1488972528,1),(4,'android_code','1802','安卓最新版本CODE',1488972528,1488972528,1),(5,'android_version','v.1.8.2','安卓最新版本号',1488972528,1495097207,1),(6,'android_update','2','安卓是否强制更新(1:是；2：否)',1488972528,1488972528,1),(7,'android_url','','安卓下载地址',1488972528,1488972528,1),(8,'ios_code','1802','苹果最新版本CODE',1488972528,1488972528,1),(9,'ios_version','v.1.8.2','苹果最新版本号',1488972528,1488972528,1),(10,'ios_update','2','苹果是否强制更新(1:是；2：否)',1488972528,1488972528,1),(11,'ios_url','','苹果下载地址',1488972528,1488972528,1);
/** 定义 role_menu 信息 **/
INSERT INTO `role_menu` VALUES (1,'系统管理','',NULL,0,1492511817,1494409421,1),(3,'账户管理','',NULL,0,1492511817,1494905002,1),(4,'竞猜管理','',NULL,0,1492511817,1492511817,1),(5,'经验值管理','',NULL,0,1492511817,1493363596,1),(6,'管理员列表','/adminInfos',1,0,1492511817,1492511817,1),(7,'菜单列表','/menuInfos',1,0,1492511817,1494240319,1),(8,'角色列表','/roleInfos',1,0,1492511817,1494240327,1),(9,'经验值列表','/grade.html',5,0,1492511817,1492511817,1),(11,'竞猜列表','/guessInfo',4,0,1492511817,1492511817,1),(13,'账户列表','/anchor.html',3,0,1492511817,1494905027,1),(14,'提现处理','/cash.html',3,0,1492511817,1492511817,1),(17,'系统配置','/sysconfig',1,0,1494315000,1494315189,1),(18,'广告管理','',NULL,0,1494388576,1494572053,1),(19,'banner配置','/banner',18,0,1494388643,1494388643,1),(20,'信息反馈','',NULL,0,1494409454,1494409454,1),(21,'意见反馈','/feedback',20,0,1494409487,1494409487,1),(22,'违规举报','/illegalReport',20,0,1494409507,1494409507,1),(23,'直播间H5','/liveH5',18,0,1494572072,1494572072,1),(24,'公众号管理','',NULL,0,1494905983,1494905983,1),(25,'公众号订单','/weichatInfo',24,0,1494906014,1494906014,1),(26,'分类管理','',NULL,0,1494906032,1494906032,1),(27,'类型管理','/typeInfo',26,0,1494906050,1494906050,1),(28,'类别管理','/categoryInfo',26,0,1494906183,1494906183,1),(30,'公众号商品','/wechatPro',24,0,1494906520,1494906520,1);
/** 定义 admin_info 信息 **/
INSERT INTO `admin_info` VALUES (1001,'admin','0790929388eb7b79a974e671715c62af','Hermit',1,'27','15355048838','510876005@qqcom',1494914228,'192.168.99.1',1,'1610509306',1,1494914228,1494914228,1),(1002,'sjx0220','f099454f5b4b97bc48a7ffaf11c44cbe','沉睡的懒飘',1,'28','17767078521','363618421@qq.com',1494330507,NULL,1,'26648',2,1494330507,1494330526,1),(1003,'iam007','bad82bb1b86d1a6ae1abac809d4e7a4f','一颗',2,'18','17682342553','360809885@qq.com',1494490682,'192.168.2.17',1,'2064',3,1494490682,1494490682,1);
