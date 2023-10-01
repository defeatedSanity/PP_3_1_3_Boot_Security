create table if not exists user
(
    id bigint auto_increment primary key,
    birthdate datetime     not null,
    name      varchar(20)  null,
    password  varchar(255) null,
    surname   varchar(20)  null,
    username  varchar(20)  null unique
);

create table if not exists role
(
    id   bigint auto_increment primary key,
    name varchar(20) null,
    constraint UK_8sewwnpamngi6b1dwaa88askk
    unique (name)
);

create table if not exists user_role
(
    user_id bigint not null references user(id),
    role_id bigint not null references role(id),
    primary key (user_id, role_id)
);

insert into user
    (birthdate,
     name,
     password,
     surname,
     username) VALUES (
                       '1900-01-01',
                       'Ivan',
                       '$2a$12$/e4N1gUewR0dJ5Isq89QCODNbveFGA.SiKZNAV8WuN/7v59RweLkW',
                        'Ivanov',
                       'admin'
                      ), (
                          '1950-01-01',
                          'Vasily',
                          '$2a$12$r3B/LmJN.F9VgDkCv6lWL.lP1NJmwmD/4FEbcggXzwCNlhP1qrYyq',
                          'Pupkin',
                          'user'
                        );

INSERT into role (name) values ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1), (2, 2)





