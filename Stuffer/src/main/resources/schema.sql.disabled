-- creating tables
create table if not exists users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(256) NOT NULL ,
  enabled BOOLEAN NOT NULL DEFAULT TRUE ,
  PRIMARY KEY (username));

create table if not exists user_roles (
  user_role_id SERIAL PRIMARY KEY,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));