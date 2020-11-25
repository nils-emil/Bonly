--liquibase formatted sql
--changeset Nils-Emil:20201121193008>
ALTER TABLE jhi_user ADD COLUMN gender varchar(255) null;
