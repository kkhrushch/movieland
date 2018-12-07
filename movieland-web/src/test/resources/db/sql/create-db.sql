CREATE TABLE role_resource_permission
( id                      int                NOT NULL
, role                    varchar(1000)      NOT NULL
, url_pattern             varchar(1000)      NOT NULL
, http_method             varchar(1000)      NOT NULL
)
;

CREATE TABLE movie_review
( id                      int
, movie_id                int    NOT NULL
, app_user_id             int    NOT NULL
, text                    varchar(1000)      NOT NULL
)
;

CREATE TABLE movie_rate
( id                      int NOT NULL
, movie_id                int    NOT NULL
, app_user_id             int    NOT NULL
, rate                    double   NOT NULL
)
;

CREATE TABLE movie_genre
( id                      int NOT NULL
, movie_id                int    NOT NULL
, genre_id                int    NOT NULL
)
;

CREATE TABLE movie
( id                      int NOT NULL
, name                    varchar(2000)      NOT NULL
, original_name           varchar(2000)      NOT NULL
, year                    int   NOT NULL
, description             varchar(2000)
, rating                  double
, price                   double   NOT NULL
, poster_url              varchar(2000)
)
;

CREATE TABLE genre
( id                      int NOT NULL
, name                    varchar(2000)      NOT NULL
)
;

CREATE TABLE app_user
( id                      int NOT NULL
, email                   varchar(2000)      NOT NULL
, password                varchar(2000)      NOT NULL
, role                    varchar(2000)      NOT NULL
, first_name              varchar(2000)      NOT NULL
, last_name               varchar(2000)      NOT NULL
, nickname                varchar(2000)
)
;

CREATE TABLE country
( id                      int                NOT NULL
, name                    varchar(2000)      NOT NULL
)
;

CREATE TABLE movie_country
( id                      int       NOT NULL
, movie_id                int       NOT NULL
, country_id              int       NOT NULL
)
;