show databases;
use dev;


select count(*)
from dev.evaluate;

with temp_sum as
             (select a0, count(*) as qty from dev.evaluate group by a0)
select *
from temp_sum;

create table employee_code_lines
(
    number     varchar(16) primary key,
    code_lines json

);

insert into employee_code_lines(number)
select distinct a0
from evaluate
limit 100000;

select *
from employee_code_lines;

select round(rand() * 10000, 0);

update employee_code_lines
set code_lines =
        (select json_arrayagg(line)
         from (
                  select round(rand() * 10000) as line
                  from incr
                  where id + 'a' != number
                  limit 1000) as t)
where;
update employee_code_lines A inner join (
    select t.number,

           (select round(rand() * 10000) as line
            from incr
            where t.number is not null
            limit 1) as code_lines
    from employee_code_lines as t) B
    on A.number = B.number
set A.code_line = B.code_lines;

select number, json_extract(code_lines, '$[1]')
from employee_code_lines;

alter table employee_code_lines
    add code_lines_1000 json;

alter table employee_code_lines
    add code_line bigint;



select number, json_extract(code_lines, '$[50]')
from employee_code_lines
group by number;


update employee_code_lines set code_lines_1000 = null;


select * from employee_code_lines;