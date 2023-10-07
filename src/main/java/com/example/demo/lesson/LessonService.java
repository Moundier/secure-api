package com.example.demo.lesson;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.chapters.Chapter;
import com.example.demo.chapters.ChapterRepo;
import com.example.demo.course.Course;
import com.example.demo.course.CourseRepo;

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
                    
                return ResponseEntity.status(HttpStatus.CREATED).body("Lesson added to chapter " + chapterId + " in course " + courseId);
            }
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not found or new lesson is null");
    }
    

    public ResponseEntity<?> edit(Integer id, Lesson provided) {
        Course course = courseRepo.findById(id).orElseThrow(null);

        if (course == null || provided == null) 
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Course not found or lesson is null");

        for (Chapter chapter : course.getChapters()) {
            for (Lesson lesson: chapter.getLessons()) {       
                if (lesson.getId().equals(provided.getId())) {
                    var edited = Lesson.builder()
                        .id(provided.getId())
                        .title(provided.getTitle())
                        .description(provided.getDescription())
                        .lessonReadme(provided.getLessonReadme())
                        .isLessonComplete(provided.getIsLessonComplete())
                        .chapter(chapter) // Ensure Association chapter lesson 
                        .build();

                    return ResponseEntity.status(HttpStatus.CREATED).body(lessonRepo.save(edited));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course " + id + " not found!");
    }

    
    public ResponseEntity<?> wipe(@PathVariable Integer lessonId) {
        Optional<Lesson> lessonOptional = lessonRepo.findById(lessonId);
        
        if (lessonOptional.isPresent()) {
            lessonRepo.deleteById(lessonId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson " + lessonId + " not found!");
    }
}
