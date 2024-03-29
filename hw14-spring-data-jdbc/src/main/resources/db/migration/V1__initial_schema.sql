
drop table if exists client;
create table client
(
    id bigserial not null primary key,
    name varchar(50)
);

drop table if exists address;
create table address
(
    id bigserial not null primary key,
    street varchar(50),
    client_id bigint references client(id)
);

drop table if exists phone;
create table phone
(
    id bigserial not null primary key,
    client_id bigint references client(id) on delete cascade,
    number varchar(50)
);

