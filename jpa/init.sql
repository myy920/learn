create database test_jpa;

use test_jpa;

drop table if exists t_customer;
create table t_customer
(
    id          bigint       not null auto_increment primary key,
    name        varchar(128) not null comment '客户名称',
    age         int          not null comment '客户年龄',
    creator     varchar(128) not null comment '创建人',
    create_time timestamp    not null comment '创建时间',
    updater     varchar(128) not null comment '更新人',
    update_time timestamp    not null comment '更新时间'
) comment '客户表';

drop table if exists t_document;
create table t_document
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
