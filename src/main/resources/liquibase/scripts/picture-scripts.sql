-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE picture
(
    id         bigserial primary key,
    data       bytea        not null,
    file_path  varchar(255) not null,
    file_size  bigint       not null,
    media_type varchar(255) not null,
    shelter_id bigint       not null,
    FOREIGN KEY (shelter_id) REFERENCES shelter (id)
);
