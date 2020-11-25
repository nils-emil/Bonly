--liquibase formatted sql
--changeset Nils-Emil:20201121203008>
ALTER TABLE advertisement DROP CONSTRAINT fk_advertisement_correct_answer_id;
ALTER TABLE advertisement DROP COLUMN correct_answer_id;
ALTER TABLE advertisement_answers ADD COLUMN correct BOOLEAN NOT NULL DEFAULT false;
