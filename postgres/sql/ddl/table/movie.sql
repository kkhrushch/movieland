CREATE TABLE movie
( id                      bigserial NOT NULL
, name                    text      NOT NULL
, original_name           text      NOT NULL
, year                    integer   NOT NULL
, country                 text      NOT NULL
, movie_genre_id          bigint    NOT NULL
, description             text      NOT NULL
, price                   numeric   NOT NULL
, poster_url              text      NOT NULL
)
;