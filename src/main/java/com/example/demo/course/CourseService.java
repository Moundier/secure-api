package com.example.demo.course;

import java.io.File;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.chapters.Chapter;
import com.example.demo.lesson.Lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;

    public Course save(Course course) {
        return courseRepo.save(course);
    }

    public Course find(Integer id) {
        return courseRepo.findById(id).orElseThrow(() -> notFound404("Single Course"));
    }

    public CourseDTO findCompleteCourse(Integer id) {
        // Retrieve the Course entity from the repository
        Course course = courseRepo.findById(id).orElseThrow(() -> notFound404("Course"));

        // Create a CourseDTO and set its properties
        CourseDTO finalCourse = CourseDTO.builder()
                .imageURL(course.getImageURL())
                .title(course.getTitle())
                .details(course.getDetails())
                .duration(course.getDuration())
                .level(course.getLevel())
                .chapters(new HashSet<>()) // Initialize the set of ChapterDTOs
                .build();

        Set<Chapter> chapters = course.getChapters();
       
        // No Chapter null
        for (Chapter chapter : chapters) {
            if (chapter == null) {
                throw chapterNotFound404();
            }
        }

        for (Chapter chapter : chapters) {
            // Create a ChapterDTO and set its properties
            ChapterDTO finalChapter = ChapterDTO.builder()
                    .title(chapter.getTitle())
                    .description(chapter.getDescription())
                    .lessons(new HashSet<>()) // Initialize the set of LessonDTOs
                    .build();

            // Retrieve the lessons associated with the chapter
            Set<Lesson> lessons = chapter.getLessons();
            
            // Iterate to verify if any is null
            for (Lesson lesson : lessons) {
                if (lesson == null) {
                    throw lessonNotFound404();
                }
            }

            for (Lesson lesson : lessons) {
                // Create a LessonDTO and set its properties
                LessonDTO finalLesson = LessonDTO.builder()
                        .title(lesson.getTitle())
                        .description(lesson.getDescription())
                        .isLessonComplete(lesson.getIsLessonComplete())
                        .lessonReadme(lesson.getLessonReadme())
                        .build();
                // Add the LessonDTO to the ChapterDTO's set of lessons
                finalChapter.getLessons().add(finalLesson);
            }

            // Add the ChapterDTO to the CourseDTO's set of chapters
            finalCourse.getChapters().add(finalChapter);
        }

        return finalCourse;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseDTO {
        private String imageURL;
        private String title;
        private String details;
        private Date duration;
        private String level;
        private Set<ChapterDTO> chapters;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChapterDTO {
        private String title;
        private String description;
        private Set<LessonDTO> lessons;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LessonDTO {
        private String title;
        private String description;
        private Boolean isLessonComplete;
        private File lessonReadme;
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public Course edit(Integer id, Course newer) {
        
        // We dont pass the id
        var older = courseRepo.findById(id).orElseThrow(() -> notFound404("Course"));

        if (older != null) {
            older = Course.builder()
                .id(older.getId())
                .imageURL(newer.getImageURL())
                .title(newer.getTitle())
                .details(newer.getDetails())
                .duration(newer.getDuration())
                .level(newer.getLevel())
                .build();
        
            return courseRepo.save(older);
        }

        return null;
    }

    public void wipe(Integer id) {
        courseRepo.deleteById(id);
    }

    public RuntimeException notFound404(String message) {
        return new RuntimeException(message + " was not found in database!");
    }

    public RuntimeException lessonNotFound404() {
        return new RuntimeException("Lesson was not found in database!");
    }

    public RuntimeException chapterNotFound404() {
        return new RuntimeException("Lesson was not found in database!");
    }
}