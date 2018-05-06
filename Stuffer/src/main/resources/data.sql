-- delete all data
delete from user_role;
delete from users;
delete from role;

-- inserting data
insert into users(id, username, password)
values (1, 'admin','$2a$10$Yd.FY0Oz6WOlXP.j2ha7nOEK1PBYMcBw676JcK1U7hdAc4M5TH9LG');

insert into role(id, name)
values (1, 'ADMIN');
insert into role(id, name)
values (2, 'USER');

insert into user_role(user_id, role_id)
values (1, 1);
insert into user_role(user_id, role_id)
values (1, 2);