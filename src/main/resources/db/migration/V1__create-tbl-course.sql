CREATE TABLE IF NOT EXISTS tbl_course (
    id SERIAL PRIMARY KEY,
    imageURL VARCHAR(255),
    title VARCHAR(255),
    details VARCHAR(255),
    duration DATE,
    level VARCHAR(255)
);