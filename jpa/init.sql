create database test_jpa;

use test_jpa;

drop table if exists test_document;
create table test_document
(
    id              bigint       not null auto_increment primary key,
    title           varchar(128) not null comment '标题',
    content         text         null comment '内容',
    version         varchar(64)  not null comment '版本',
    is_last_version tinyint(1)   not null comment '是否最新版本: 0=否, 1=是',
    creator         varchar(128) not null comment '创建人',
    create_time     timestamp    not null comment '创建时间',
    updater         varchar(128) not null comment '更新人',
    update_time     timestamp    not null comment '更新时间'
) comment 'word表';

select *
from test_document;

show full processlist;

insert into test_document (id, title, content, version, is_last_version, creator, create_time, updater, update_time)
values (1, '标题AAA', '123', '1.0.0', 0, 'system', '2024-04-22 20:34:34', 'system', '2024-04-22 20:37:23'),
       (2, '标题AAA', '123', '1.0.1', 0, 'system', '2024-04-22 20:37:23', 'system', '2024-04-22 20:37:41'),
       (3, '标题AAA', '123', '1.0.2', 0, 'system', '2024-04-22 20:37:41', 'system', '2024-04-22 20:37:44'),
       (4, '标题AAA', '123', '1.0.3', 0, 'system', '2024-04-22 20:37:44', 'system', '2024-04-22 20:37:51'),
       (5, '标题AAA', '123', '1.0.4', 0, 'system', '2024-04-22 20:37:51', 'system', '2024-04-22 20:37:54'),
       (6, '标题AAA', '123', '1.0.5', 0, 'system', '2024-04-22 20:37:54', 'system', '2024-04-22 20:37:58'),
       (7, '标题AAA', '123', '1.0.6', 0, 'system', '2024-04-22 20:37:58', 'system', '2024-04-22 20:38:02'),
       (8, '标题AAA', '123', '1.0.7', 1, 'system', '2024-04-22 20:38:01', 'system', '2024-04-22 20:38:02'),
       (9, '标题BBB', '123DSFSA', '1.0.0', 0, 'system', '2024-04-22 20:38:10', 'system', '2024-04-22 20:38:14'),
       (10, '标题BBB', '123DSFSA', '1.0.1', 0, 'system', '2024-04-22 20:38:14', 'system', '2024-04-22 20:38:17');


drop table if exists test_department;
create table test_department
(
    id          bigint       not null auto_increment primary key,
    l1_dept     varchar(64)  not null comment '一级部门',
    l2_dept     varchar(64)  null comment '二级部门',
    l3_dept     varchar(64)  null comment '三级部门',
    l4_dept     varchar(64)  null comment '四级部门',
    description varchar(256) null comment '描述',
    sort        int          not null comment '排序',
    creator     varchar(128) not null comment '创建人',
    create_time timestamp    not null comment '创建时间',
    updater     varchar(128) not null comment '更新人',
    update_time timestamp    not null comment '更新时间'
) comment '部门表';