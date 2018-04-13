-- 自动竞猜需要的配置（自动竞猜的时间间隔（秒）封盘到下一盘的时间） hjy 2018-04-10 11:34:00 未测试
INSERT INTO `guess_config` (`config_id`, `parent_name`, `child_name`, `cname`, `default_val`, `remark`, `self_val`, `ctime`, `utime`) 
VALUES 
('13', 'GUESS_CONFIG:', 'count_guessinfo_win', '3.0.1上版后总的竞猜输赢累加', '0', '3.0.1上版后总的竞猜输赢累加', '0', '1523519862', '1523519862');
INSERT INTO `guess_config` (`config_id`, `parent_name`, `child_name`, `cname`, `default_val`, `remark`, `self_val`, `ctime`, `utime`) 
VALUES 
('14', 'GUESS_CONFIG:', 'is_automatic', '是否开始自动竞猜（1是 2否）', '1', '是否开始自动竞猜（1是 2否）', '1', '1523435014', '1523435014');