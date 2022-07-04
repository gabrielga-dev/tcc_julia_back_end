create table role (
   id bigint primary key not null auto_increment,
   name varchar(100),
   description varchar(500)
);
INSERT INTO role(id, name, description)VALUES(1, 'INTERNO', 'INTERNO');
INSERT INTO role(id, name, description)VALUES(2, 'EXTERNO', 'EXTERNO');