-- V2__create-tbl-chapter.sql
CREATE TABLE IF NOT EXISTS tbl_chapter (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255)
);
