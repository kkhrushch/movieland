CREATE TABLE movie_review
( id                      bigserial NOT NULL
, movie_id                bigint    NOT NULL
, app_user_id             bigint    NOT NULL
, text                    text      NOT NULL
)
;