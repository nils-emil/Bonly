--liquibase formatted sql
--changeset Nils-Emil:20201220003024>
ALTER TABLE prize
    DROP COLUMN winner_id;
ALTER TABLE prize
    ADD COLUMN winner varchar(255);
