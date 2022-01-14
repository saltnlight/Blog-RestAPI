DROP TABLE IF EXISTS Users cascade;
CREATE TABLE IF NOT EXISTS Users (
                  id serial PRIMARY KEY,
                  username VARCHAR ( 50 ) UNIQUE NOT NULL,
                  firstname VARCHAR ( 50 ) NOT NULL,
                  lastname VARCHAR ( 50 ) NOT NULL,
                  age int,
                  email VARCHAR ( 255 ) UNIQUE NOT NULL,
                  phone VARCHAR ( 255 ) UNIQUE,
                  password VARCHAR ( 50 ) NOT NULL,
                  created_at timestamp NOT NULL DEFAULT NOW(),
                  deactivated bool NOT NULL,
                  deactivated_at timestamp NULL
);

DROP TABLE IF EXISTS Connections;
CREATE TABLE IF NOT EXISTS Connections (
                   id serial PRIMARY KEY,
                   user_id int8 NOT NULL,
                   connection_id int8 NOT NULL,
                   created_at timestamp NOT NULL DEFAULT NOW(),
                   FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                   FOREIGN KEY (connection_id) REFERENCES Users (id) on delete cascade
);

DROP TABLE IF EXISTS Posts cascade;
CREATE TABLE IF NOT EXISTS Posts (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  title VARCHAR ( 50 ) NOT NULL,
                  body VARCHAR ( 255 ) NOT NULL,
                  created_at timestamp NOT NULL DEFAULT NOW(),
                  FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade
);

DROP TABLE IF EXISTS PostLikes;
CREATE TABLE IF NOT EXISTS PostLikes (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  post_id int8 NOT NULL,
                  FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                  FOREIGN KEY (post_id) REFERENCES Posts (id) on delete cascade
);

DROP TABLE IF EXISTS FavouritePosts;
CREATE TABLE IF NOT EXISTS FavouritePosts (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  post_id int8 NOT NULL,
                  FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                  FOREIGN KEY (post_id) REFERENCES Posts (id)
);

DROP TABLE IF EXISTS Comments cascade;
CREATE TABLE IF NOT EXISTS Comments (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 post_id int8 NOT NULL,
                 body VARCHAR ( 50 ) NOT NULL,
                 created_at timestamp NOT NULL DEFAULT NOW(),
                 FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                 FOREIGN KEY (post_id) REFERENCES Posts (id) on delete cascade
);

DROP TABLE IF EXISTS CommentLikes;
CREATE TABLE IF NOT EXISTS CommentLikes (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 comment_id int8 NOT NULL,
                 FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                 FOREIGN KEY (comment_id) REFERENCES Comments (id) on delete cascade
);
