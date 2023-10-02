-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE question
(
    id bigserial primary key,
    shelter_id bigint not null,
    volunteer_id bigint not null,
    FOREIGN KEY (shelter_id) REFERENCES shelter (id),
    FOREIGN KEY (volunteer_id) REFERENCES volunteer (id)
 );
--changeset aMilenkov:2
DROP TABLE question;
--changeset aMilenkov:3
CREATE TABLE question
(
    id bigserial primary key,
    shelter_id bigint not null,
    volunteer_id bigint not null,
    question_text text,
    FOREIGN KEY (shelter_id) REFERENCES shelter (id),
    FOREIGN KEY (volunteer_id) REFERENCES volunteer (id)
);