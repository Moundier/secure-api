package com.example.demo.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CourseRepo extends JpaRepository<Course, Integer> {
    
    Course findByTitle(String title);

    final String query = "" + 
    "SELECT tbl_course.id AS course_id, tbl_course.title AS course_title, tbl_chapter.title AS chapter_title, tbl_lesson.title AS lesson_title " +
    "FROM tbl_course " +
    "LEFT JOIN tbl_course_chapter ON tbl_course.id = tbl_course_chapter.course_id " +
    "LEFT JOIN tbl_chapter ON tbl_course_chapter.chapter_id = tbl_chapter.id " +
    "LEFT JOIN tbl_chapter_lesson ON tbl_chapter.id = tbl_chapter_lesson.chapter_id " +
    "LEFT JOIN tbl_lesson ON tbl_chapter_lesson.lesson_id = tbl_lesson.id " +
    "WHERE tbl_course.id = :courseId";

    @Query(value = query, nativeQuery = true)
    Course findCompleteCourse(@Param("courseId") Integer courseId);

    Course findBySlug(String slug);
}
