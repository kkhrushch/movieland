CREATE TABLE role_resource_permission
( id                      bigserial NOT NULL
, role                    text      NOT NULL
, url_pattern             text      NOT NULL
, http_method             text      NOT NULL
)
;