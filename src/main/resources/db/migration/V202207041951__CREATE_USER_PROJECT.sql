create table user_project (
   user_id bigint not null,
   project_id bigint not null,
   FOREIGN KEY (user_id) REFERENCES user(id),
   FOREIGN KEY (project_id) REFERENCES project(id)
);