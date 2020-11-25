--liquibase formatted sql
--changeset Nils-Emil:20201125003024>
ALTER TABLE advertisement ALTER COLUMN image_content_type DROP NOT NULL;
ALTER TABLE prize ALTER COLUMN image_content_type DROP NOT NULL;
