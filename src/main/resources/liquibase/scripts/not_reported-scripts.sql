-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE not_reported
(
    id bigserial primary key,
    user_id bigint not null,
    days_lost int not null,
    FOREIGN KEY (user_id) REFERENCES user(id)
);