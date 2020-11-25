--liquibase formatted sql
--changeset Nils-Emil:20201123003008>
ALTER TABLE prize ADD COLUMN image bytea;
ALTER TABLE prize ADD COLUMN type varchar(255);
