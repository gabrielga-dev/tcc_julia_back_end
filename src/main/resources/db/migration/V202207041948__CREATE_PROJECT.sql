create table project (
   id bigint primary key not null auto_increment,
   name varchar(100) not null,
   description varchar(1000),
   max_participants int not null,
   situation varchar(30) not null,
   start_date date not null,
   end_date date,
   owner_id bigint not null,
   FOREIGN KEY (owner_id) REFERENCES user(id)
);