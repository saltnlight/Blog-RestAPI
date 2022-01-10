-- DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
                  id serial PRIMARY KEY,
                  firstname VARCHAR ( 50 ) UNIQUE NOT NULL,
                  lastname VARCHAR ( 50 ) UNIQUE NOT NULL,
                  age int NOT NULL,
                  email VARCHAR ( 255 ) UNIQUE NOT NULL,
                  phone VARCHAR ( 255 ) UNIQUE NOT NULL,
                  password VARCHAR ( 50 ) NOT NULL,
                  created_at timestamp NOT NULL,
                  deactivated bool NOT NULL,
                  deactivated_at timestamp NOT NULL
);

-- VARCHAR LENGTH
CREATE TABLE IF NOT EXISTS Posts (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  title VARCHAR ( 50 ) NOT NULL,
                  body VARCHAR ( 255 ) NOT NULL,
                  favorite bool NOT NULL,
                  created_at timestamp NOT NULL,
                  FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS PostLikes (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  post_id int8 NOT NULL,
                  FOREIGN KEY (post_id) REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS Comments (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 post_id int8 NOT NULL,
                 body VARCHAR ( 50 ) NOT NULL,
                 created_at timestamp NOT NULL,
                 FOREIGN KEY (user_id) REFERENCES Users (id),
                 FOREIGN KEY (post_id) REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS CommentLikes (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 comment_id int8 NOT NULL,
                 FOREIGN KEY (user_id) REFERENCES Users (id),
                 FOREIGN KEY (comment_id) REFERENCES Comments (id)
);

CREATE TABLE IF NOT EXISTS Connections (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 connection_id int8 NOT NULL,
                 FOREIGN KEY (user_id) REFERENCES Users (id),
                 FOREIGN KEY (connection_id) REFERENCES Users (id)
);