create table if not exists post (
    id serial primary key,
	name varchar(255) not null,
	description varchar(255) not null,
	created timestamp not null,
	visible boolean not null,
	city_id int not null
);

create table if not exists candidate (
    id serial primary key,
	name varchar(255) not null,
	description varchar(255) not null,
	created timestamp not null,
	photo bytea
);

create table if not exists users (
  id serial primary key,
  email varchar(50) unique,
  password text
);




