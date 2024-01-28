drop table if exists $table;

create table $table
(
    id   bigint auto_increment primary key,
    name varchar(32)
);