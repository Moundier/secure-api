package com.example.demo.course;

import java.io.File;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.chapters.Chapter;
import com.example.demo.chapters.ChapterRepo;
import com.example.demo.lesson.Lesson;
import com.example.demo.lesson.LessonRepo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;
    private final ChapterRepo chapterRepo;
    private final LessonRepo lessonRepo;

    // Course save can be refactored to courseRepo.save() with no losses
    public Course save(Course course) {
        
        Course newerCourse = Course.builder()
            .imageURL(course.getImageURL())
            .title(course.getTitle())
            .details(course.getDetails())
            .duration(course.getDuration())
            .level(course.getLevel())
            .chapters(new HashSet<>())
            .build();

        course.createSlug();

        for (Chapter chapter : course.getChapters()) {
            
            Chapter newerChapter = Chapter.builder()
                .title(chapter.getTitle())
                .description(chapter.getDescription())
                .lessons(new HashSet<>())
                .build();

                for (Lesson lesson : chapter.getLessons()) {
                    Lesson newerLesson = Lesson.builder()
                    .title(lesson.getTitle())
                    .description(lesson.getDescription())
                    .isLessonComplete(lesson.getIsLessonComplete())
                    .lessonReadme(lesson.getLessonReadme())
                    .build();

                    newerChapter.getLessons().add(newerLesson);
                }

            newerCourse.getChapters().add(newerChapter);
        }
        
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

    public record OnlyCourseDto(
        String image,
        String titulo,
        String details,
        Date duration,
        String level
    ) { }

    public List<Course> findAll() {
        // TODO: Return OnlyCourseDto 
        return courseRepo.findAll();
    }

    public Course findBySlug(String slug) {
        return courseRepo.findBySlug(slug);
    }

    public ResponseEntity<?> edit(Integer courseId, Course newer) {

        if (courseId == null || newer == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid courseId or updated Course");
        }
    
        Course older = courseRepo.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    
        Set<Chapter> chaptersToRemove = new HashSet<>(older.getChapters());
        
        for (Chapter chapter : chaptersToRemove) {
            older.getChapters().remove(chapter);
            chapterRepo.deleteById(chapter.getId());

            Set<Lesson> lessonsToRemove = new HashSet<>(chapter.getLessons());   

            for (Lesson lesson : lessonsToRemove) {
                chapter.getLessons().remove(lesson);
                lessonRepo.deleteById(lesson.getId());
            }
        }

        Course course = Course.builder()
        .id(older.getId())
        .imageURL(newer.getImageURL())
        .title(newer.getTitle())
        .details(newer.getDetails())
        .duration(newer.getDuration())
        .level(newer.getLevel())
        .chapters(newer.getChapters())
        .build();

        course.createSlug();
    
        return ResponseEntity.status(HttpStatus.OK).body(courseRepo.save(course));
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