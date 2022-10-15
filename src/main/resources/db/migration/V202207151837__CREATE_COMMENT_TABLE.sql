create table comment (
   id bigint primary key not null auto_increment,
   title varchar(100),
   content varchar(5000),
   author_id bigint not null,
   project_id bigint not null,
   creation_date date not null,
   FOREIGN KEY (author_id) REFERENCES user(id),
   FOREIGN KEY (project_id) REFERENCES project(id)
);