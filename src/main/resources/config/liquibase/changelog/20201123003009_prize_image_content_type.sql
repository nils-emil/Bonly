--liquibase formatted sql
--changeset Nils-Emil:20201123003009>
ALTER TABLE prize ADD COLUMN image_content_type varchar(255);

