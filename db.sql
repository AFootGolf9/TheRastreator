create table log(
    log varchar(150) not null,
    time_log timestamp default now()
);

create table client(
id int primary key,
username varchar(25) unique,
email varchar(255) unique,
password varchar(100)
);

create sequence client_id_seq;
alter table client alter column id set default nextval('client_id_seq');

create table token(
    token varchar(255) not null unique,
    client_id int not null,
    time timestamp default now(),
    foreign key (client_id) references client(id)
);

create table position(
    position_time timestamp not null default now(),
    client_id int not null,
    lat numeric(9, 7) not null,
    long numeric(10, 7) not null,
    foreign key (client_id) references client(id)
);