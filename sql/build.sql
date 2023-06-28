create function create_name() returns varchar(3)
begin
    declare xin varchar(3) default '梅杨阳';
    set xin = '梅杨阳';
    return xin;
end;

set global log_bin_trust_function_creators = TRUE;
show global variables like '%function%';

select create_name();