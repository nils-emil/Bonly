--liquibase formatted sql
--changeset Nils-Emil:20201122203008>
ALTER TABLE user_advertisement_answers DROP CONSTRAINT ux_user_advertisement_answers_advertisement_id;
ALTER TABLE user_advertisement_answers DROP COLUMN state_province;
ALTER TABLE user_advertisement_answers ADD COLUMN advertisement_answer_id bigint;

ALTER TABLE user_advertisement_answers
    ADD CONSTRAINT fk_user_advertisement_answers_advertisement_answer_id
        FOREIGN KEY (advertisement_answer_id)
            REFERENCES advertisement_answers(id);

ALTER TABLE user_advertisement_answers ADD UNIQUE (user_id, advertisement_id)

