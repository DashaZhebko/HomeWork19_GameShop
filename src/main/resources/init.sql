CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255),
    nickname VARCHAR(255),
    birthday DATE,
    password VARCHAR(255)
);
CREATE TABLE Games
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255),
    release_date DATE,
    rating       DECIMAL(3, 1),
    cost         DECIMAL(10, 2),
    description  TEXT
);


CREATE TABLE Users_Games
(
    user_id INTEGER REFERENCES Users (id),
    game_id INTEGER REFERENCES Games (id)
);