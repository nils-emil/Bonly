--liquibase formatted sql
--changeset Nils-Emil:20201220003025>
ALTER TABLE prize
    ADD COLUMN title varchar(255);
