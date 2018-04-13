-- 自动竞猜需要的字段 hjy 2018-04-10 11:34:00 未测试
ALTER TABLE guess_info ADD COLUMN begin_time datetime DEFAULT NULL COMMENT '本盘开始时间' AFTER stop_bet_time;
ALTER TABLE guess_info ADD COLUMN begin_time_cron varchar(30) DEFAULT NULL COMMENT '本盘开始时间的cron表达式' AFTER begin_time;
ALTER TABLE guess_info ADD COLUMN next_sub_id bigint(20) DEFAULT NULL COMMENT '下一盘的竞猜主题id' AFTER begin_time_cron;
ALTER TABLE guess_info ADD COLUMN next_guess_title varchar(150) DEFAULT NULL COMMENT '下一盘的竞猜标题' AFTER next_sub_id;
ALTER TABLE guess_info ADD COLUMN is_next smallint(6) DEFAULT NULL COMMENT '是否自动下一盘(1-是 2-否)' AFTER next_guess_title;
ALTER TABLE guess_info ADD COLUMN automatic_type smallint(6) DEFAULT NULL COMMENT '自动开盘状态(0 将要挂起，1 挂起中，2 结束，3异常)' AFTER is_next;
-- 自动竞猜的容错时间  hjy 2018-04-12 11:34:00 未测试
ALTER TABLE guess_subject ADD COLUMN fault_tolerant_time int(11) DEFAULT '10' COMMENT '容错时间（封盘到下一盘开始的时间间隔（秒））' AFTER pool_bean;
ALTER TABLE guess_subject ADD COLUMN stake_time int(11) DEFAULT '10' COMMENT '投注时间（秒）' AFTER fault_tolerant_time;
