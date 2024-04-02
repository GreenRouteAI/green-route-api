create table if not exists "user" (
    id varchar primary key not null,
    first_name varchar,
    last_name varchar,
    email varchar,
    birthdate timestamp with time zone,
    authentication_id varchar,
    photo_id varchar,
    username varchar
);