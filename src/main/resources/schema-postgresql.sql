DROP TABLE IF EXISTS users cascade;
CREATE TABLE IF NOT EXISTS users (
                  id serial PRIMARY KEY,
                  username VARCHAR ( 50 ) UNIQUE NOT NULL,
                  firstname VARCHAR ( 50 ) NOT NULL,
                  lastname VARCHAR ( 50 ) NOT NULL,
                  age int,
                  email VARCHAR ( 255 ) UNIQUE NOT NULL,
                  phone VARCHAR ( 255 ) UNIQUE,
                  password VARCHAR ( 50 ) NOT NULL,
                  created_at timestamp NOT NULL DEFAULT NOW(),
                  enabled bool NOT NULL,
                  deactivated_at timestamp NULL
);

DROP TABLE IF EXISTS connections;
CREATE TABLE IF NOT EXISTS connections (
                   id serial PRIMARY KEY,
                   user_id int8 NOT NULL,
                   connection_id int8 NOT NULL,
                   created_at timestamp NOT NULL DEFAULT NOW(),
                   FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                   FOREIGN KEY (connection_id) REFERENCES Users (id) on delete cascade
);

DROP TABLE IF EXISTS posts cascade;
CREATE TABLE IF NOT EXISTS posts (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  title VARCHAR ( 50 ) NOT NULL,
                  body VARCHAR ( 255 ) NOT NULL,
                  created_at timestamp NOT NULL DEFAULT NOW(),
                  FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade
);

DROP TABLE IF EXISTS postLikes;
CREATE TABLE IF NOT EXISTS postLikes (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  post_id int8 NOT NULL,
                  FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                  FOREIGN KEY (post_id) REFERENCES Posts (id) on delete cascade
);

DROP TABLE IF EXISTS favouritePosts;
CREATE TABLE IF NOT EXISTS favouritePosts (
                  id serial PRIMARY KEY,
                  user_id int8 NOT NULL,
                  post_id int8 NOT NULL,
                  FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                  FOREIGN KEY (post_id) REFERENCES Posts (id)
);

DROP TABLE IF EXISTS comments cascade;
CREATE TABLE IF NOT EXISTS comments (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 post_id int8 NOT NULL,
                 body VARCHAR ( 50 ) NOT NULL,
                 created_at timestamp NOT NULL DEFAULT NOW(),
                 FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                 FOREIGN KEY (post_id) REFERENCES Posts (id) on delete cascade
);

DROP TABLE IF EXISTS commentLikes;
CREATE TABLE IF NOT EXISTS commentLikes (
                 id serial PRIMARY KEY,
                 user_id int8 NOT NULL,
                 comment_id int8 NOT NULL,
                 FOREIGN KEY (user_id) REFERENCES Users (id) on delete cascade,
                 FOREIGN KEY (comment_id) REFERENCES Comments (id) on delete cascade
);

DROP TABLE IF EXISTS authorities;
CREATE TABLE IF NOT EXISTS authorities (
                   id serial PRIMARY KEY,
                   username VARCHAR ( 50 ) NOT NULL,
                   authority VARCHAR ( 50 ) NOT NULL,
                   CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES Users (username)
);
create unique index ix_auth_username on authorities (username, authority) ;