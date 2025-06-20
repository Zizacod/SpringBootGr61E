CREATE TABLE users
(
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN      NOT NULL
);

CREATE TABLE authorities
(
    username  VARCHAR(255) NOT NULL,
    authority VARCHAR(255) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

insert into users (username, password, enabled)
values ('admin', '$2a$10$WMCTUc/4EwHJsYQvLEGmiOTQrbSb22teChUYrc9kncqvrXqxVLi02', true);
insert into users (username, password, enabled)
values ('user', '$2a$10$x3uB6R5Dslaj4TFYh9MeYOdHHKQLzgzGbWwW1SAoggjNW1iijjJlO', true);
insert into authorities (username, authority)
values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority)
values ('user', 'ROLE_USER');