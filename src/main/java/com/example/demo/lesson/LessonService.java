package com.example.demo.lesson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.chapters.Chapter;
import com.example.demo.chapters.ChapterRepo;
import com.example.demo.course.Course;
import com.example.demo.course.CourseRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final CourseRepo courseRepo;
    private final ChapterRepo chapterRepo;
    private final LessonRepo lessonRepo;

    public ResponseEntity<?> save(Integer courseId, Integer chapterId, Lesson newer) {
        Course course = courseRepo.findById(courseId).orElse(null);

        if (newer == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Chapter " + chapterId + " not found in course " + courseId);

        for (Chapter chapter : course.getChapters()) {
            if (chapter.getId().equals(chapterId)) {

                chapter.getLessons().add(newer);
                chapterRepo.save(chapter); // Ensure Lesson Chapter Association

                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Lesson added to chapter " + chapterId + " in course " + courseId);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not found or new lesson is null");
    }

    public ResponseEntity<?> find(Integer lessson) {
        return ResponseEntity.status(HttpStatus.FOUND).body(lessonRepo.findById(lessson));
    }

    public ResponseEntity<?> edit(Integer lessonId, Lesson lesson) {
        
        if (lessonId == null || lesson == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid lessonId or updatedLesson");
        }
    
        Lesson existingLesson = lessonRepo.findById(lessonId)
            .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
    
        existingLesson = Lesson.builder()
            .id(existingLesson.getId())
            .title(lesson.getTitle())
            .description(lesson.getDescription())
            .lessonReadme(lesson.getLessonReadme())
            .isLessonComplete(lesson.getIsLessonComplete())
            // Add more properties to update as needed
            .build();
    
        lessonRepo.save(existingLesson);
    
        return ResponseEntity.ok(existingLesson);
    }

    public ResponseEntity<?> wipe(Integer chapterId, Integer lessonId) {

        if (chapterId == null && lessonId == null)
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid chapterId or lessonId");

        Chapter chapter = chapterRepo.findById(chapterId).orElseThrow(() -> new EntityNotFoundException("Chapter not found with ID: " + chapterId));
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));

        if (chapter != null && lesson != null) {
            // It removes from the mapping table
            chapter.getLessons().remove(lesson);
            chapterRepo.save(chapter);
        }

        lessonRepo.deleteById(lessonId);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

    }
}
