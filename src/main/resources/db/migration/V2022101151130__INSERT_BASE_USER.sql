INSERT INTO  user(first_name, last_name, email, password, creation_date, active)
    VALUE (
       'João', 'Gomes', 'joao.gomes@gmail.com', '$2a$10$GL.4.W5c9BB7Wuwvb40a4eH.6uvfPP.K./jmNvloD3jKLmJBjCtxy',
       '2022-10-15', true
      );

INSERT INTO user_role(user_id, role_id) VALUE ((SELECT id FROM user WHERE email = 'joao.gomes@gmail.com'), 1);