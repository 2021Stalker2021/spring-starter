--liquibase formatted sql

--changeset edik:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset edik:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);