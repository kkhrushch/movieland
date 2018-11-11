CREATE TABLE app_user
( id                      bigserial NOT NULL
, email                   text      NOT NULL
, password                text      NOT NULL
, role                    text      NOT NULL
, first_name              text      NOT NULL
, last_name               text      NOT NULL
, display_name            text      NOT NULL
)
;