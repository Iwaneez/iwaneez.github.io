-- delete all data
delete from user_role;
delete from users;
delete from role;

-- inserting data
insert into users(id, username, password)
values (1, 'iwaneez','$2a$10$g0a.9vabcUS4U7ONyWSHrOD9rcdQ5noxqbUyOIz3F1EX40MIa5BQW');

insert into role(id, name)
values (1, 'ADMIN');
insert into role(id, name)
values (2, 'USER');

insert into user_role(user_id, role_id)
values (1, 1);
insert into user_role(user_id, role_id)
values (1, 2);