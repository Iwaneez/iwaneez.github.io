CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(256) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id SERIAL PRIMARY KEY,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
  
INSERT INTO users(username,password,enabled)
VALUES ('iwaneez','9b6173e5991a7db6aa91fb2b40e5785c36079987f5eebb68946eb0ea04f3ee7b882cd760366e4262', true);
INSERT INTO users(username,password,enabled)
VALUES ('alex','3ef30b19ce1ddf363ec760ea55bdffb1b943ebf56d9f16034f5bab78ecfc1496456ad6361d017a49', true);

INSERT INTO user_roles (username, role)
VALUES ('iwaneez', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('iwaneez', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('alex', 'ROLE_USER');