create table if not exists grabber
(
    id      serial primary key,
    name    varchar(255),
    text    text,
    link    text UNIQUE,
    created timestamp
);