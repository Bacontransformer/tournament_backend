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

create table team_member
(
    id        int auto_increment comment '主键'
        primary key,
    team_id   int                                   not null comment '队伍ID',
    player_id int                                   not null comment '队员ID',
    role      varchar(20) default '队员'            not null comment '角色（队长、副队长、队员等）',
    is_active tinyint(1)  default 1                 not null comment '是否活跃（1：是，0：否）',
    join_time datetime    default CURRENT_TIMESTAMP null comment '加入时间',
    constraint team_member_ibfk_1
        foreign key (team_id) references team (team_id)
            on delete cascade,
    constraint team_member_ibfk_2
        foreign key (player_id) references player (player_id)
            on delete cascade
)
    comment '队伍队员明细表';

create index player_id
    on team_member (player_id);

create index team_id
    on team_member (team_id);

