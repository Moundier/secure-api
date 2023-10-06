CREATE TABLE tbl_lesson (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    is_lesson_complete BOOLEAN,
    lesson_readme BYTEA,
    chapter_id INTEGER,
    FOREIGN KEY (chapter_id) REFERENCES tbl_chapter (id)
);
