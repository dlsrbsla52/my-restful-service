-- insert into users(id, join_date, name, password, ssn) values (2001, now(), 'User1', 'test1', '980609-1111111');
-- insert into users(id, join_date, name, password, ssn) values (2002, now(), 'User2', 'test2', '980609-2222222');
-- insert into users(id, join_date, name, password, ssn) values (2003, now(), 'User3', 'test3', '980609-3333333');


insert into users(join_date, name, password, ssn) values (now(), 'User1', 'test1', '980609-1111111');
insert into users(join_date, name, password, ssn) values (now(), 'User2', 'test2', '980609-2222222');
insert into users(join_date, name, password, ssn) values (now(), 'User3', 'test3', '980609-3333333');


insert into post(description, user_id) values ('My first post', (select id from users where name = 'User1'));
insert into post(description, user_id) values ('My second post', (select id from users where name = 'User2'));
insert into post(description, user_id) values ('My third post', (select id from users where name = 'User3'));