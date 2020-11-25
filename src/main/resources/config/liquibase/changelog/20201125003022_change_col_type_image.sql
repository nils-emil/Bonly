--liquibase formatted sql
--changeset Nils-Emil:20201125003022>
ALTER TABLE prize ALTER COLUMN image TYPE TEXT;
ALTER TABLE advertisement ALTER COLUMN image TYPE TEXT;
