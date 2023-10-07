-- V4__create-tbl-course-chapter.sql
CREATE TABLE IF NOT EXISTS tbl_course_chapter (
    course_id INTEGER,
    chapter_id INTEGER,
    PRIMARY KEY (course_id, chapter_id),
    FOREIGN KEY (course_id) REFERENCES tbl_course (id),
    FOREIGN KEY (chapter_id) REFERENCES tbl_chapter (id)
);
