CREATE TABLE movie
( id                      bigserial NOT NULL
, name                    text      NOT NULL
, original_name           text      NOT NULL
, year                    integer   NOT NULL
, description             text
, rating                  numeric
, price                   numeric   NOT NULL
, poster_url              text
)
;