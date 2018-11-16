CREATE TABLE movie_rate
( id                      bigserial NOT NULL
, movie_id                bigint    NOT NULL
, app_user_id             bigint    NOT NULL
, rate                    numeric   NOT NULL
)
;
