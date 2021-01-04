--liquibase formatted sql
--changeset Nils-Emil:20201300003025>

DROP TABLE IF EXISTS allowed_email;
DROP SEQUENCE IF EXISTS allowed_email_id_seq;
CREATE SEQUENCE allowed_email_id_seq;
CREATE TABLE IF NOT EXISTS allowed_email
(
    id smallint NOT NULL DEFAULT nextval('allowed_email_id_seq'),
    email varchar(255) NOT NULL
);
ALTER SEQUENCE allowed_email_id_seq OWNED BY allowed_email.id;

ALTER table advertisement ADD COLUMN IF NOT EXISTS priority integer;
