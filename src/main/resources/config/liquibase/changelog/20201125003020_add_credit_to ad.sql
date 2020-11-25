--liquibase formatted sql
--changeset Nils-Emil:20201125003020>
ALTER TABLE advertisement ADD COLUMN credit_count bigint NOT NULL DEFAULT 0;


