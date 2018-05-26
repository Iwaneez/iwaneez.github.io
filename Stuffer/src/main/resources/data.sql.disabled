-- delete all data
delete from user_role;
delete from users;
delete from role;

-- inserting data
insert into users (id, username, password, language, active_profile)
values (9999, 'admin', '$2a$10$Yd.FY0Oz6WOlXP.j2ha7nOEK1PBYMcBw676JcK1U7hdAc4M5TH9LG', null, null);

insert into role (id, type)
values (1, 'ADMIN');
insert into role (id, type)
values (2, 'USER');

insert into user_role (user_id, role_id)
values (9999, 1);
insert into user_role (user_id, role_id)
values (9999, 2);