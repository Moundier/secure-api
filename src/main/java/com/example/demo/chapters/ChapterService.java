package com.example.demo.chapters;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepo;
import com.example.demo.lesson.Lesson;
import com.example.demo.lesson.LessonRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {
    
    private final CourseRepo courseRepo;
    private final ChapterRepo chapterRepo;
    private final LessonRepo lessonRepo;

    public ResponseEntity<?> save(Integer id, Chapter newer) {
        
        Course course = courseRepo.findById(id).orElse(null);
        if (course == null || newer == null) 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chapter or course not found!");

        Chapter chapter = Chapter.builder()
            .title(newer.getTitle())
            .description(newer.getDescription())
            .lessons(new HashSet<>())
            .build();

        for (Lesson lesson : newer.getLessons()) {
              chapter.getLessons().add(lesson);  
        }
        course.getChapters().add(chapter);        

        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepo.save(course));
    }

    public ResponseEntity<?> edit(Integer chapterId, Chapter newer) {

        if (chapterId == null || newer == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid chapterId or updated Chapter");
        }
    
        Chapter older = chapterRepo.findById(chapterId)
            .orElseThrow(() -> new RuntimeException("Chapter not found with ID: " + chapterId));
    
        Set<Lesson> lessonsToDelete = new HashSet<>(older.getLessons());
    
        for (Lesson lesson : lessonsToDelete) {
            older.getLessons().remove(lesson);
            lessonRepo.deleteById(lesson.getId());
        }
    
        // Use Lombok builder to construct an updated Chapter object
        Chapter updatedChapter = Chapter.builder()
            .id(older.getId()) // Set the ID if needed
            .title(newer.getTitle())
            .description(newer.getDescription())
            .lessons(newer.getLessons())
            .build();
    
        Chapter savedChapter = chapterRepo.save(updatedChapter);
    
        return ResponseEntity.status(HttpStatus.OK).body(savedChapter);
    }

    public ResponseEntity<?> wipe(Integer courseId, Integer chapterId) {

        if (courseId == null && chapterId == null)
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid courseId or chapterId");

        // Its Needed beacuse of the mapping table, so we remove 
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Lesson not found with ID: " + courseId));
        Chapter chapter = chapterRepo.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found with ID: " + chapterId));
        
        if (course != null && chapter != null) {
            course.getChapters().remove(chapter);
            courseRepo.save(course);
        }

        chapterRepo.deleteById(chapterId);

        return ResponseEntity.status(HttpStatus.OK).body("Chapter " + chapterId + " got deleted!");
    }
}
