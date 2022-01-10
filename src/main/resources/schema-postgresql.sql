DROP TABLE IF EXISTS Users;

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
-- FORIEGN ISS, VARCHAR LENGTH
CREATE TABLE IF NOT EXISTS Posts (
                  id serial PRIMARY KEY,
                  title VARCHAR ( 50 ) UNIQUE NOT NULL,
                  body VARCHAR ( 100000 ) NOT NULL,
                  favourite bool NOT NULL,
                  user_id pg_foreign_table REFERENCES Users NOT NULL
);

CREATE TABLE IF NOT EXISTS Comments (
                id serial PRIMARY KEY,
                body VARCHAR ( 50 ) NOT NULL,
                user_id pg_foreign_table REFERENCES Users NOT NULL,
                post_id pg_foreign_table REFERENCES Posts NOT NULL
);