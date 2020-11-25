--liquibase formatted sql
--changeset Nils-Emil:20201124003020>
ALTER TABLE jhi_user ADD COLUMN age varchar(255);


