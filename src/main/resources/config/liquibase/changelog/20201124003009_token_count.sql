--liquibase formatted sql
--changeset Nils-Emil:20201124003009>
ALTER TABLE jhi_user ADD COLUMN credit_count bigint NOT NULL DEFAULT 0;


