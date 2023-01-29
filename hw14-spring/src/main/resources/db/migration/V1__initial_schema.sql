create table client
(
    id          bigserial not null primary key,
    name        varchar(50)
);

create table client_addresses
(
    id          bigserial not null primary key,
    street      varchar (255) not null,
    client_id   bigint not null REFERENCES client(id)
);

create table phones
(
    id          bigserial not null primary key,
    number      varchar (255) not null,
    client_id   bigint REFERENCES client(id)
);
