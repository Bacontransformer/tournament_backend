create table admin
(
    admin_id    int auto_increment comment '管理员ID'
        primary key,
    password    varchar(255)                       not null comment '密码',
    username    varchar(100)                       not null comment '用户名',
    name        varchar(100)                       not null comment '姓名',
    phone       varchar(15)                        null comment '电话号码',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint username
        unique (username)
)
    comment '管理员表';

create table leader
(
    leader_id   int auto_increment comment '领导ID'
        primary key,
    password    varchar(255)                         not null comment '密码',
    name        varchar(100)                         not null comment '姓名',
    username    varchar(100)                         not null comment '用户名',
    department  varchar(100)                         null comment '部门',
    phone       varchar(15)                          null comment '电话号码',
    create_time datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_passed   tinyint(1) default 0                 null,
    constraint username
        unique (username)
)
    comment '领队表';

create table player
(
    player_id   int                                not null comment '选手ID'
        primary key,
    team_id     int                                not null comment '队伍ID',
    gender      varchar(10)                        not null comment '性别',
    name        varchar(50)                        not null comment '姓名',
    department  varchar(100)                       not null comment '院系',
    phone       varchar(15)                        not null comment '手机号',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '选手表';

create table referee
(
    referee_id  int auto_increment comment '裁判ID'
        primary key,
    password    varchar(255)                         not null comment '密码',
    username    varchar(100)                         not null comment '用户名',
    name        varchar(100)                         not null comment '姓名',
    department  varchar(100)                         null comment '部门',
    phone       varchar(15)                          null comment '电话号码',
    is_passed   tinyint(1) default 0                 null comment '是否通过',
    create_time datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint username
        unique (username)
)
    comment '裁判表';

create table team
(
    team_id      int auto_increment comment '队伍ID'
        primary key,
    leader_id    int                                not null comment '队伍领导ID',
    name         varchar(255)                       not null comment '队伍名称',
    leader_name  varchar(255)                       not null comment '队伍领导名称',
    introduction text                               null comment '队伍简介，可以为空',
    department   varchar(255)                       not null comment '队伍所属部门',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '队伍表';

ALTER TABLE admin MODIFY COLUMN admin_id BIGINT AUTO_INCREMENT COMMENT '管理员ID';
ALTER TABLE leader MODIFY COLUMN leader_id BIGINT AUTO_INCREMENT COMMENT '领导ID';
ALTER TABLE player MODIFY COLUMN player_id BIGINT NOT NULL COMMENT '选手ID';
ALTER TABLE referee MODIFY COLUMN referee_id BIGINT AUTO_INCREMENT COMMENT '裁判ID';
ALTER TABLE team MODIFY COLUMN team_id BIGINT AUTO_INCREMENT COMMENT '队伍ID';

CREATE TABLE `match` (
                         `match_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `event_id` BIGINT NOT NULL,
                         `match_type` VARCHAR(50) NOT NULL COMMENT '比赛类型，MatchA 或 MatchB',
                         `begin_time` DATETIME NOT NULL COMMENT '比赛开始时间',
                         `total_rounds` INT DEFAULT 0 COMMENT '总局数',
                         `team_a_id` BIGINT NOT NULL COMMENT '队伍A的ID',
                         `team_b_id` BIGINT NOT NULL COMMENT '队伍B的ID',
                         `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE TABLE `match_a` (
                           `match_id` BIGINT PRIMARY KEY COMMENT '逻辑关联 match 表的 ID',
                           `modes` VARCHAR(255) COMMENT '比赛模式，例如：男单、女双等',
                           `max_player_participation` INT DEFAULT 0 COMMENT '单个选手的最大参赛次数',
                           `round_count` INT DEFAULT 1 COMMENT '每场比赛的局数',
                           `win_score` INT DEFAULT 21 COMMENT '单局获胜分数'
);

CREATE TABLE `match_set` (
                             `set_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                             `match_id` BIGINT NOT NULL COMMENT '逻辑关联 match 表的 ID',
                             `set_number` INT NOT NULL COMMENT '当前局数',
                             `team_a_score` INT DEFAULT 0 COMMENT '队伍A在当前局的得分',
                             `team_b_score` INT DEFAULT 0 COMMENT '队伍B在当前局的得分',
                             `winner_team_id` BIGINT COMMENT '当前局的获胜队伍ID'
);

CREATE TABLE `match_b` (
                           `match_id` BIGINT PRIMARY KEY COMMENT '逻辑关联 match 表的 ID',
                           `section_score` INT NOT NULL COMMENT '每小节获胜所需分数',
                           `total_sections` INT DEFAULT 4 COMMENT '总小节数',
                           `min_team_age_sum` INT COMMENT '队伍年龄和最小值（可选）',
                           `max_team_age_sum` INT COMMENT '队伍年龄和最大值（可选）'
);

CREATE TABLE `match_b_section` (
                                   `section_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   `match_id` BIGINT NOT NULL COMMENT '逻辑关联 match 表的 ID',
                                   `section_number` INT NOT NULL COMMENT '小节编号',
                                   `team_a_score` INT DEFAULT 0 COMMENT '队伍A的得分',
                                   `team_b_score` INT DEFAULT 0 COMMENT '队伍B的得分',
                                   `next_team_a_score_to_win` INT DEFAULT 0 COMMENT '队伍A的下一目标获胜分数'
);

CREATE TABLE `match_player` (
                                `assignment_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                `match_id` BIGINT NOT NULL COMMENT '逻辑关联 match 表的 ID',
                                `team_id` BIGINT NOT NULL COMMENT '逻辑关联队伍的 ID',
                                `player_id` BIGINT NOT NULL COMMENT '逻辑关联选手的 ID',
                                `role` VARCHAR(50) COMMENT '选手角色，例如：1号、2号、队长',
                                `is_captain` BOOLEAN DEFAULT FALSE COMMENT '是否为队长',
                                `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE TABLE team_registration (
                                   registration_id BIGINT NOT NULL AUTO_INCREMENT, -- 主键ID
                                   team_id BIGINT NOT NULL, -- 队伍ID
                                   event_id BIGINT NOT NULL, -- 活动ID
                                   team_name VARCHAR(255) NOT NULL, -- 队伍名称
                                   event_name VARCHAR(255) NOT NULL, -- 活动名称
                                   registration_time DATETIME NOT NULL, -- 报名时间
                                   remark VARCHAR(500), -- 备注
                                   PRIMARY KEY (registration_id) -- 主键
);