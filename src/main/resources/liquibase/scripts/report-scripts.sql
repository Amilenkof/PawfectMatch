-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE report
(
    id        bigserial primary key,
    photo     bytea        not null,
    food      varchar(255) not null,
    health    varchar(255) not null,
    behaviour varchar(255) not null,
    user_id   bigint       not null,
    FOREIGN KEY (user_id) REFERENCES users (id)

);
--changeset aMilenkov:2
INSERT INTO report (photo, food, health, behaviour, user_id)
VALUES ('377x90 PNG image 3,58 kB',
        'Мясо, корм для собак VidalSosun,овощи',
        'Животное чувствует себя хорошо',
        'Животное полно сил и энергии',
        1);
