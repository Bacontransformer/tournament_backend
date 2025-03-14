create table tournament.admin
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

create table tournament.event
(
    event_id              int auto_increment comment '主键'
        primary key,
    name                  varchar(255) not null comment '活动名称',
    introduction          text         null comment '活动介绍',
    stadium               varchar(255) not null comment '体育馆',
    required_area_count   int          not null comment '需要的最少场地数',
    required_referee_count int         not null comment '需要的最少裁判数',
    begin_time            date         not null comment '活动开始日期',
    end_time              date         not null comment '活动结束日期',
    match_type            varchar(50)  not null comment '赛事类型',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '赛事表';

create table tournament.leader
(
    leader_id   int auto_increment comment '主键'
        primary key,
    team_id     int                             null,
    username    varchar(100)                       not null comment '用户名',
    phone       varchar(15)                        not null comment '手机号',
    password    varchar(255)                       not null comment '密码',
    name        varchar(100)                       not null comment '姓名',
    team_name   varchar(255)                       not null comment '队伍名',
    gender      varchar(10)                        not null comment '性别',
    age         int                                not null comment '年龄',
    department  varchar(255)                       not null comment '部门',
    is_passed   tinyint  default 0                 not null comment '是否被审核通过',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint phone
        unique (phone),
    constraint username
        unique (username)
)
    comment '领队表';

create table tournament.match_a
(
    match_a_id             int auto_increment comment '主键'
        primary key,
    event_id               int          not null comment '逻辑关联活动id',
    team_a_id              int          not null comment '队伍a的id',
    team_b_id              int          not null comment '队伍b的id',
    winner_team_id         int          null comment '最终获胜队伍id',
    team_a_department      varchar(255)    null comment '队伍a的部门',
    team_b_department      varchar(255)    null comment '队伍b的部门',
    winner_team_department varchar(255)    null comment '最终获胜队伍部门',
    game_count             int             not null comment '赛制局数目（1或者3）',
    win_score              int             not null comment '获胜基础比分',
    max_participation_times int default 10 not null comment '同一个人最多参加次数',
    min_team_age_sum       int default 0   not null comment '最少团队年龄',
    max_team_age_sum       int default 1000 not null comment '最大团队年龄',
    max_substitute_player  int default 10  not null comment '最大替补人数',
    team_a_mode_score      int default 0   not null comment 'A队模式大比分',
    team_b_mode_score      int default 0   not null comment 'B队模式大比分',
    create_time            datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time            datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '传统比赛表';

create table tournament.match_b
(
    match_b_id                  int auto_increment comment '主键'
        primary key,
    event_id                    int          not null comment '逻辑关联活动id',
    status                      int default 0   not null comment '比赛状态',
    section_score               int             not null comment '每小节的获胜分数',
    current_section             int default 1   not null comment '当前小节',
    team_a_id                   int          not null comment 'teamA的编号',
    team_b_id                   int          not null comment 'teamB的编号',
    winner_team_id              int          null comment '获胜队伍编号',
    referee_id                  int          not null comment '裁判的id',
    substitute_referee_id       int          null comment '替补裁判的id',
    team_a_department           varchar(255)    null comment 'teamA的部门',
    team_b_department           varchar(255)    null comment 'teamB的部门',
    winner_team_department      varchar(255)    null comment '胜者队伍的部门',
    venue_number                int             not null comment '场地编号',
    team_a_player_id1           int          null comment 'teamA的1号选手',
    team_a_player_id2           int          null comment 'teamA的2号选手',
    team_a_player_id3           int          null comment 'teamA的3号选手',
    team_a_player_id4           int          null comment 'teamA的4号选手',
    team_b_player_id1           int          null comment 'teamB的1号选手',
    team_b_player_id2           int          null comment 'teamB的2号选手',
    team_b_player_id3           int          null comment 'teamB的3号选手',
    team_b_player_id4           int          null comment 'teamB的4号选手',
    team_a_substitute_player_id1 int         null comment 'teamA替补选手1',
    team_a_substitute_player_id2 int         null comment 'teamA替补选手2',
    team_a_substitute_player_id3 int         null comment 'teamA替补选手3',
    team_a_substitute_player_id4 int         null comment 'teamA替补选手4',
    team_b_substitute_player_id1 int         null comment 'teamB替补选手1',
    team_b_substitute_player_id2 int         null comment 'teamB替补选手2',
    team_b_substitute_player_id3 int         null comment 'teamB替补选手3',
    team_b_substitute_player_id4 int         null comment 'teamB替补选手4',
    current_team_a_player_id1   int          null comment '当前teamA的1号选手',
    current_team_a_player_id2   int          null comment '当前teamA的2号选手',
    current_team_b_player_id1   int          null comment '当前teamB的1号选手',
    current_team_b_player_id2   int          null comment '当前teamB的2号选手',
    team_a_score                int default 0   not null comment 'teamA的总分数',
    team_b_score                int default 0   not null comment 'teamB的总分数',
    max_substitute_player       int default 10  not null comment '最大替补人数',
    min_team_age_sum            int default 0   not null comment '最小队伍年龄和',
    max_team_age_sum            int default 1000 not null comment '最大队伍年龄和',
    begin_time                  datetime        not null comment '比赛开始时间',
    create_time                 datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time                 datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '趣味比赛表';

create table tournament.match_mode
(
    match_mode_id             int auto_increment comment '主键'
        primary key,
    match_a_id                int          not null comment '逻辑关联match-a的id',
    mode                      varchar(50)     not null comment '模式',
    venue_number              int             not null comment '场地编号',
    referee_id                int          not null comment '裁判id',
    substitute_referee_id     int          null comment '替补裁判id',
    team_a_id                 int          not null comment 'a队id',
    team_b_id                 int          not null comment 'b队id',
    mode_winner_team_id       int          null comment '该模式获胜的队伍id',
    begin_time                datetime        not null comment '比赛开始时间',
    status                    int default 0   not null comment '0:未开始，1:进行中，2:已结束',
    team_a_player1            int          null comment 'a队参赛队员1号',
    team_a_player2            int          null comment 'a队参赛队员2号',
    team_b_player1            int          null comment 'b队参赛队员1号',
    team_b_player2            int          null comment 'b队参赛队员2号',
    team_a_substitute_player1 int          null comment 'a队替补1号',
    team_a_substitute_player2 int          null comment 'a队替补2号',
    team_b_substitute_player1 int          null comment 'b队替补1号',
    team_b_substitute_player2 int          null comment 'b队替补2号',
    team_a_round_score1       int default 0   not null comment 'teamA第1局的得分',
    team_a_round_score2       int             null comment 'teamA第2局的得分',
    team_a_round_score3       int             null comment 'teamA第3局的得分',
    team_b_round_score1       int default 0   not null comment 'teamB第1局的得分',
    team_b_round_score2       int             null comment 'teamB第2局的得分',
    team_b_round_score3       int             null comment 'teamB第3局的得分',
    team_a_game_score         int default 0   not null comment 'A队某个模式的获胜局数',
    team_b_game_score         int default 0   not null comment 'B队某个模式的获胜局数',
    current_game              int default 1   not null comment '当前第几局',
    current_round             int default 1   not null comment '当前第几轮',
    create_time               datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time               datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '传统比赛某一模式的信息表';

create table tournament.player
(
    player_id    int auto_increment comment '主键'
        primary key,
    team_id      int          not null comment '逻辑关联队伍id',
    leader_id    int          not null comment '逻辑关联领队id',
    gender       varchar(10)     not null comment '性别',
    name         varchar(100)    not null comment '姓名',
    age          int             not null comment '年龄',
    department   varchar(255)    null comment '部门',
    phone        varchar(15)     null comment '电话',
    role         varchar(50)     null comment '队员角色（如：队长、副队长、队员）',
    is_active    tinyint default 1 not null comment '是否活跃',
    join_time    datetime        null comment '加入队伍时间',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '队员表';

create table tournament.referee
(
    referee_id     int auto_increment comment '主键'
        primary key,
    password       varchar(255)    not null comment '密码',
    username       varchar(100)    not null comment '用户名',
    name           varchar(100)    not null comment '姓名',
    department     varchar(255)    null comment '部门',
    phone          varchar(15)     null comment '电话号码',
    is_passed      tinyint default 0 not null comment '是否通过审核',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint username
        unique (username),
    constraint phone
        unique (phone)
)
    comment '裁判表';

create table tournament.team
(
    team_id        int auto_increment comment '主键'
        primary key,
    leader_id      int          not null comment '逻辑管理领队id',
    name           varchar(255)    not null comment '队伍名',
    leader_name    varchar(100)    null comment '领队名',
    introduction   text            null comment '简介',
    department     varchar(255)    not null comment '部门',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '队伍表';
