--liquibase formatted sql
--changeset Nils-Emil:20201121143008>
TRUNCATE TABLE prize_registration CASCADE;
TRUNCATE TABLE prize CASCADE;
TRUNCATE TABLE advertisement_answers CASCADE;
TRUNCATE TABLE advertisement CASCADE;

ALTER TABLE prize_registration
    DROP CONSTRAINT fk_prize_registration_user_id;
ALTER TABLE prize_registration
    DROP CONSTRAINT ux_prize_registration_prize_id;
ALTER TABLE prize_registration
    DROP CONSTRAINT ux_prize_registration_user_id;
ALTER TABLE prize_registration
    ADD CONSTRAINT fk_prize_registration_user_id
        FOREIGN KEY (user_id)
            REFERENCES jhi_user(id);

ALTER TABLE prize
    DROP CONSTRAINT fk_prize_winner_id;
ALTER TABLE prize
    DROP CONSTRAINT ux_prize_winner_id;
ALTER TABLE prize
    ADD CONSTRAINT fk_prize_winner_id
        FOREIGN KEY (winner_id)
            REFERENCES jhi_user(id);

ALTER TABLE user_advertisement_answers
    DROP CONSTRAINT fk_user_advertisement_answers_user_id;
ALTER TABLE user_advertisement_answers
    DROP CONSTRAINT ux_user_advertisement_answers_user_id;
ALTER TABLE user_advertisement_answers
    ADD CONSTRAINT fk_user_advertisement_answers_user_id
        FOREIGN KEY (user_id)
            REFERENCES jhi_user(id);

ALTER TABLE advertisement
    DROP CONSTRAINT ux_advertisement_correct_answer_id;

ALTER TABLE advertisement_answers
    DROP CONSTRAINT ux_advertisement_answers_advertisement_id;


DROP TABLE person;
