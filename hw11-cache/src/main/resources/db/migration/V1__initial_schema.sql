-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);
 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 1 increment by 1;

create table addresses
(
    id bigserial not null primary key,
    street varchar (255) not null
);

create table client
(
    id   bigint not null primary key,
    address_id bigint REFERENCES addresses(id),
    name varchar(50)
);

create table phones
(

    id bigserial not null primary key,
    number varchar (255) not null,
    client_id bigint REFERENCES client(id)
);
