<!--xwf-2017-10-13-->
CREATE TABLE `guess_adventure` (
  `adventure_id` int(11) NOT NULL COMMENT '真心话大冒险id',
  `adventure_type` smallint(4) NOT NULL COMMENT '类型(1:真心话,2:大冒险)',
  `is_enable` smallint(4) NOT NULL DEFAULT '1' COMMENT '是否启用（1启用 2不启用）',
  `adventure_content` varchar(50) NOT NULL COMMENT '真心话大冒险内容',
  `sorts` smallint(11) NOT NULL DEFAULT '1' COMMENT '排序字段',
  `ctime` int(11) NOT NULL COMMENT '创建时间',
  `utime` int(11) NOT NULL COMMENT '修改时间',
  `is_delete` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否删除(1-正常,-1-删除)',
  PRIMARY KEY (`adventure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='真心话大冒险题库表';
<!--yst-2017-10-13-->
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('1', '1', '1', '有没有强吻过别人？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('2', '1', '1', '你还会想你的前任么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('3', '1', '1', '你身上那个部位最敏感？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('4', '1', '1', '你听过或者看过最好的求婚方式是什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('5', '1', '1', '你相亲过吗？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('6', '1', '1', '你最近的节日礼物最想要什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('7', '1', '1', '你会选择婚前同居么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('8', '1', '1', '你小时候偷过别人东西么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('9', '1', '1', '你做过对不起你父母的事情么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('10', '1', '1', '你会不会在意另一半的过去？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('11', '1', '1', '你有没有什么别人不知道的才能？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('12', '1', '1', '运气真好，没有事情发生？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('13', '1', '1', '你初恋是几岁？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('14', '1', '1', '你最害怕的事情或者东西是什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('15', '1', '1', '你最欣赏自己哪个部位？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('16', '1', '1', '你会做菜么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('17', '1', '1', '你每天睡觉前都刷牙么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('18', '1', '1', '你在游泳池里尿过尿么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('19', '1', '1', '当过第三者么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('20', '1', '1', '最奢侈的一次消费是买的什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('21', '1', '1', '最长一次连续多久没睡觉？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('22', '1', '1', '你最多同时和几个人恋爱？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('23', '1', '1', '你一共收藏了多少小电影？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('24', '1', '1', '你会在婚前跟你的另一半发生关系么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('25', '1', '1', '你觉得自己长得丑吗？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('26', '1', '1', '你喜欢裸睡么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('27', '1', '1', '最想要的生日礼物是什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('28', '1', '1', '你做过最奇怪的梦是什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('29', '1', '1', '如果能回到过去，你最想做的是什么？', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('30', '1', '1', '你每天都洗澡么？', '1', '1507881587', '1507881587', '1');

INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('31', '2', '1', '表演你性感的声音，重复三次。', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('32', '2', '1', '随机挑选一个观众说你爱他/她', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('33', '2', '1', '读出这两个字：干爹', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('34', '2', '1', '做个可爱的鬼脸', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('35', '2', '1', '用笔在额头上画一个乌龟，直到游戏结束', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('36', '2', '1', '模仿一段广告', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('37', '2', '1', '对观众做亲吻（10秒）', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('38', '2', '1', '表演跪地求婚', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('39', '2', '1', '讲个鬼故事', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('40', '2', '1', '做五个俯卧撑', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('41', '2', '1', '做俯卧撑期间唱国歌，直到唱完', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('42', '2', '1', '尽可能的展现一次一字马', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('43', '2', '1', '舌头舔鼻子10秒', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('44', '2', '1', '摆出不同的3个S型', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('45', '2', '1', '展现下平时你是怎么哭的', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('46', '2', '1', '用口红在脸上画一个吻痕直到游戏结束', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('47', '2', '1', '表演肚皮舞', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('48', '2', '1', '讲一个荤段子', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('49', '2', '1', '弹自己三个脑瓜崩', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('50', '2', '1', '唱一首歌', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('51', '2', '1', '一口气喝一满杯水', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('52', '2', '1', '对着观众说三次：我是猪', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('53', '2', '1', '深情的吻墙10秒', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('54', '2', '1', '舔一圈嘴唇，然后说“我饿了', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('55', '2', '1', '唱国歌', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('56', '2', '1', '做个害羞的表情', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('57', '2', '1', '10秒内原地转5圈', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('58', '2', '1', '做一个你认为异性最性感的动作', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('59', '2', '1', '亲自己的膝盖', '1', '1507881587', '1507881587', '1');
INSERT INTO `guess_adventure` (`adventure_id`, `adventure_type`, `is_enable`, `adventure_content`, `sorts`, `ctime`, `utime`, `is_delete`) VALUES ('60', '2', '1', '说一段绕口令', '1', '1507881587', '1507881587', '1');

/** 20171031 修改**/
ALTER TABLE good_number MODIFY COLUMN is_retain smallint(6) DEFAULT '1' COMMENT '是否持有:1是,2否' AFTER remark;
/** 20171101 菜单**/
INSERT INTO `role_menu` VALUES (39,'真心话大冒险','/truthDare',4,0,1507792414,1507792414,1),(40,'库表管理','/dbInfos',1,0,1508478528,1508838423,1);