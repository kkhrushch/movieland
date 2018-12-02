INSERT INTO movie (id, name, original_name, year, rating, price, poster_url)
VALUES
(
  (1, 'Список Шиндлера', 'Schindler''s List', 1993, 8.7, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg')
, (2, 'Унесённые призраками', 'Sen to Chihiro no kamikakushi', 2001, 8.6, 145.9, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOGJjNzZmMmUtMjljNC00ZjU5LWJiODQtZmEzZTU0MjBlNzgxL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1._SY209_CR0,0,140,209_.jpg')
, (3, 'Побег из Шоушенка', 'The Shawshank Redemption', 1994, 8.9, 123.45, 'https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg')
, (4, 'Зеленая миля', 'The Green Mile', 1999, 8.6, 134.67, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg')
);

INSERT INTO genre (id, name)
VALUES
(
  (1, 'вестерн')
, (2, 'ужасы')
);

INSERT INTO movie_genre (id, movie_id, genre_id)
VALUES
(
  (1, 1, 1)
, (2, 1, 2)
, (3, 2, 1)
, (4, 3, 2)
, (5, 4, 1)
, (6, 4, 2)
);

INSERT INTO country(id, name)
VALUES
(
  (1, 'США')
, (2, 'Франция')
);

INSERT INTO movie_country(id, movie_id, country_id)
VALUES
(
  (1, 1, 1)
, (1, 1, 2)
, (1, 2, 1)
, (1, 3, 2)
, (1, 4, 1)
);

INSERT INTO app_user(id, email, password, role, first_name, last_name, nickname)
VALUES
(
  (1, 'a@gmail.com', 'passA', 'USER', 'FirstNameA', 'LastNameA', 'a nickname')
, (2, 'b@gmail.com', 'passB', 'USER', 'FirstNameB', 'LastNameB', 'another nickname')
, (3, 'ronald.reynolds66@example.com', '$2a$10$03jNpS5HrO4So/Bm/yNF/OuJb/HON7Qn4/1CoaUwly4Aaiw2Qk/iO', 'USER', 'Рональд', 'Рейнольдс', 'Рональд Рейнольдс')
)

INSERT INTO movie_review(id, movie_id, app_user_id, text)
VALUES
(
  (1, 1, 1, 'a review')
, (2, 1, 2, 'another review')
);

insert into role_resource_permission (id, role, url_pattern, http_method)
values
(
  (1, 'GUEST', '/v1/movie', 'GET')
, (2, 'GUEST', '/v1/movie/\d+', 'GET')
, (3, 'GUEST', '/v1/movie/random', 'GET')
, (4, 'GUEST', '/v1/movie/genre/\d+', 'GET')
, (5, 'GUEST', '/v1/genre', 'GET')
, (6, 'GUEST', '/v1/login', 'POST')

, (7, 'USER', '/v1/movie', 'GET')
, (8, 'USER', '/v1/movie/\d+', 'GET')
, (9, 'USER', '/v1/movie/random', 'GET')
, (10, 'USER', '/v1/movie/genre/\d+', 'GET')
, (11, 'USER', '/v1/genre', 'GET')
, (12, 'USER', '/v1/login', 'POST')
, (13, 'USER', '/v1/logout', 'DELETE')
)
;

