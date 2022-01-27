insert into users (username, firstname, lastname, email, password, enabled)
    values ('amy', 'Amara', 'Ojiaku', 'amy@oji.com', 'amypassword', true),
           ('salt', 'Flora', 'Arong', 'salt@arong.com', 'florapassword', true),
           ('ken', 'Kehnide', 'Oje', 'ken@oje.com', 'kenpassword', true);

insert into connections (user_id, connection_id) values (3, 2), (3, 1), (1, 2), (2, 3);

insert into posts (user_id, title, body) values
    (2,'Flora 1st Post','Lets discuss the negative effect of social media on teens'),
    (3,'Kenn 1st Post','Blockchain programming tools'),
    (3,'Kenn 2nd Post','Guide to ace that technical interviews'),
    (1,'Amy 2nd Post','Java for ABSOLUTE beginners'),
    (1,'Amy 1st Post','Introduction to react');

insert into postlikes (user_id, post_id) VALUES (3,2), (1,2), (1,5), (3,4), (3,5), (2,3), (1,3);

insert into comments (user_id, post_id, body) values
    (1,3,'I am interested in this topic'), (1,3,'me too'), (3,5,'nice one, keep it up'),
    (1,5,'thanks'), (3,4,'100% *fire*'), (1,2,'I want to be a part of the conversation'),
    (3,5,'keep it up');

insert into commentlikes (user_id, comment_id) VALUES (1,3), (3,2), (3,5), (1,2);

insert into authorities (username, authority) values
    ('amy','ROLE_USER'), ('ken','USER'), ('salt','USER');