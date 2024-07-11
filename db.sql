create table log(
    log varchar(150) not null,
    time_log timestamp default now()
);

create table position(
    position_time timestamp not null default now(),
    lat numeric(9, 7) not null,
    long numeric(10, 7) not null)
;
