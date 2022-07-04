create table user (
   id bigint primary key not null auto_increment,
   first_name varchar(50) not null,
   last_name varchar(150) not null,
   email varchar(100) not null,
   password varchar(255) not null,
   creation_date date
);