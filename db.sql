create table admin
(
    admin_id    bigint auto_increment comment '管理员ID'
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

create table assignment
(
    assignment_id bigint auto_increment comment '球员上场记录ID'
        primary key,
    match_id      bigint                               not null comment '比赛ID',
    match_type    tinyint                              not null comment '比赛类型（0=MatchA, 1=MatchB）',
    type_order    int                                  not null comment '比赛模式序号',
    team_id       bigint                               not null comment '队伍ID',
    player_id     bigint                               not null comment '球员ID',
    is_substitute tinyint(1) default 0                 null comment '是否为替补',
    create_time   datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '球员上场情况记录表';

create table event
(
    event_id               bigint                             not null comment '活动ID'
        primary key,
    name                   varchar(255)                       not null comment '活动名称',
    introduction           text                               null comment '活动描述',
    stadium                varchar(255)                       null comment '活动体育馆',
    required_area_count    int                                null comment '需要的最大场地数',
    required_referee_count int                                null comment '需要的最大裁判数',
    begin_time             date                               not null comment '活动开始日期',
    end_time               date                               not null comment '活动结束日期',
    match_type             varchar(10)                        null comment '赛事类型 混团 1234',
    create_time            datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time            datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '赛事表';

create table leader
(
    leader_id   bigint auto_increment comment '领队ID'
        primary key,
    team_id     bigint                               null comment '队伍ID',
    password    varchar(255)                         not null comment '密码',
    name        varchar(255)                         not null comment '姓名',
    gender      varchar(10)                          null comment '性别',
    age         int                                  null comment '年龄',
    username    varchar(255)                         not null comment '用户名',
    department  varchar(255)                         null comment '部门',
    phone       varchar(20)                          null comment '手机号',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_passed   tinyint(1) default 0                 null comment '是否通过审核',
    constraint phone
        unique (phone),
    constraint username
        unique (username)
)
    comment '领队表';

create table match_a
(
    match_a_id              bigint auto_increment comment '比赛的ID'
        primary key,
    event_id                bigint                             not null comment '活动ID',
    team_a_id               bigint                             not null comment '队伍A的ID',
    team_b_id               bigint                             not null comment '队伍B的ID',
    venue_number            int                                not null comment '场地编号',
    modes                   varchar(255)                       not null comment '比赛模式（以逗号分隔存储）',
    round_count             int                                not null comment '每个比赛模式的局数',
    win_score               int                                not null comment '单局获胜分数',
    max_participation_times int                                null comment '单个选手最大可参加比赛模式数量',
    min_team_age_sum        int                                null comment '最小队伍年龄和',
    max_team_age_sum        int                                null comment '最大队伍年龄和',
    max_substitute_player   int                                null comment '最大替补人数',
    begin_time              datetime                           not null comment '比赛开始时间',
    create_time             datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time             datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'MatchA 比赛记录表';

create table match_b
(
    match_b_id            bigint auto_increment comment '比赛的ID'
        primary key,
    event_id              bigint                             not null comment '活动ID',
    team_a_id             bigint                             not null comment '队伍A的ID',
    team_b_id             bigint                             not null comment '队伍B的ID',
    venue_number          int                                not null comment '场地编号',
    section_score         int                                not null comment '每小节的获胜分数',
    max_substitute_player int                                null comment '最大替补人数',
    min_team_age_sum      int                                null comment '最小队伍年龄和',
    max_team_age_sum      int                                null comment '最大队伍年龄和',
    begin_time            datetime                           not null comment '比赛开始时间',
    create_time           datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time           datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'MatchB 比赛记录表';

create table match_set
(
    match_set_id   bigint auto_increment comment '每局得分记录ID'
        primary key,
    match_id       bigint                             not null comment '比赛ID',
    match_type     tinyint                            not null comment '比赛类型（0=MatchA, 1=MatchB）',
    type_order     int                                not null comment '比赛模式序号',
    match_round    int                                not null comment '当前局数',
    team_a_score   int                                not null comment '队伍A得分',
    team_b_score   int                                not null comment '队伍B得分',
    score_list     text                               null comment '得分情况（以逗号分隔，0代表TeamA得分，1代表TeamB得分）',
    winner_team_id bigint                             null comment '当前局的获胜队伍ID',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '比赛局得分记录表';

create table player
(
    player_id  bigint auto_increment comment '队员ID'
        primary key,
    team_id    bigint                               not null comment '队伍ID',
    leader_id  bigint                               null comment '领队ID',
    gender     varchar(10)                          null comment '性别',
    name       varchar(255)                         not null comment '姓名',
    age        int                                  null comment '年龄',
    department varchar(255)                         null comment '部门',
    phone      varchar(20)                          null comment '手机号',
    role       varchar(50)                          null comment '队员角色',
    is_active  tinyint(1) default 1                 null comment '是否活跃',
    join_time  datetime   default CURRENT_TIMESTAMP null comment '加入队伍时间',
    constraint phone
        unique (phone)
)
    comment '队员表';

create table registration
(
    registration_id   bigint auto_increment
        primary key,
    team_id           bigint       not null,
    event_id          bigint       not null,
    team_name         varchar(255) not null,
    event_name        varchar(255) not null,
    registration_time datetime     not null,
    remark            varchar(500) null
);

create table team
(
    team_id      bigint auto_increment comment '队伍ID'
        primary key,
    leader_id    bigint                             not null comment '领队ID',
    name         varchar(255)                       not null comment '队伍名称',
    leader_name  varchar(255)                       null comment '领队姓名',
    introduction text                               null comment '队伍简介',
    department   varchar(255)                       null comment '部门',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '队伍表';

