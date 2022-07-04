create table user_role (
   user_id bigint not null,
   role_id bigint not null,
   FOREIGN KEY (user_id) REFERENCES user(id),
   FOREIGN KEY (role_id) REFERENCES role(id)
);