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

