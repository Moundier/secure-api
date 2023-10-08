CREATE TABLE tbl_chapter_lesson (
    chapter_id INTEGER,
    lesson_id INTEGER,
    PRIMARY KEY (chapter_id, lesson_id),
    FOREIGN KEY (chapter_id) REFERENCES tbl_chapter (id),
    FOREIGN KEY (lesson_id) REFERENCES tbl_lesson (id)
);