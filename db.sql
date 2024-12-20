CREATE TABLE match_a (
                         match_a_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '比赛的ID',
                         event_id BIGINT NOT NULL COMMENT '活动ID',
                         team_a_id BIGINT NOT NULL COMMENT '队伍A的ID',
                         team_b_id BIGINT NOT NULL COMMENT '队伍B的ID',
                         venue_number INT NOT NULL COMMENT '场地编号',
                         modes VARCHAR(255) NOT NULL COMMENT '比赛模式（以逗号分隔存储）',
                         round_count INT NOT NULL COMMENT '每个比赛模式的局数',
                         win_score INT NOT NULL COMMENT '单局获胜分数',
                         max_participation_times INT COMMENT '单个选手最大可参加比赛模式数量',
                         min_team_age_sum INT COMMENT '最小队伍年龄和',
                         max_team_age_sum INT COMMENT '最大队伍年龄和',
                         max_substitute_player INT COMMENT '最大替补人数',
                         begin_time DATETIME NOT NULL COMMENT '比赛开始时间',
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT 'MatchA 比赛记录表';
