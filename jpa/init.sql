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