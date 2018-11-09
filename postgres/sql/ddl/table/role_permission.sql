CREATE TABLE role_permission
( id                      bigserial NOT NULL
, role                    text      NOT NULL
, allowed_url             text      NOT NULL
)
;