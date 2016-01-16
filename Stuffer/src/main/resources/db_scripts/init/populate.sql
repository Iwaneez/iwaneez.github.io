-- dropping tables
drop table if exists user_roles;
drop table if exists users;

-- creating tables
create table users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(256) NOT NULL ,
  enabled BOOLEAN NOT NULL DEFAULT TRUE ,
  PRIMARY KEY (username));

create table user_roles (
  user_role_id SERIAL PRIMARY KEY,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
  
-- inserting data
insert into users(username, password, enabled)
values ('iwaneez','9b6173e5991a7db6aa91fb2b40e5785c36079987f5eebb68946eb0ea04f3ee7b882cd760366e4262', true);
insert into users(username,password,enabled)
values ('alex','3ef30b19ce1ddf363ec760ea55bdffb1b943ebf56d9f16034f5bab78ecfc1496456ad6361d017a49', true);

insert into user_roles (username, role)
values ('iwaneez', 'ROLE_USER');
insert into user_roles (username, role)
values ('iwaneez', 'ROLE_ADMIN');
insert into user_roles (username, role)
values ('alex', 'ROLE_USER');