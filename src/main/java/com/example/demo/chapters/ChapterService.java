package com.example.demo.chapters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {
    
    private final CourseRepo courseRepo;
    private final ChapterRepo chapterRepo;

    public ResponseEntity<?> save(Integer id, Chapter new_chapter) {
        
        Course course = courseRepo.findById(id).orElse(null);
        
        if (course != null) {

            Chapter chapter = Chapter.builder()
              .title(new_chapter.getTitle())
              .description(new_chapter.getDescription())
              .course(course)
              .build();

            course.getChapters().add(chapter);
            courseRepo.save(course);
        }

        return null;
    }

    public ResponseEntity<?> edit(Integer courseId, Chapter newer) {
        
        Course course = courseRepo.findById(courseId).orElse(null);

        for (Chapter older : course.getChapters()) {
            if (newer.getId() == older.getId()) {
                return ResponseEntity.status(HttpStatus.OK).body(chapterRepo.save(newer));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course " + courseId + " not found!");
    }

    public ResponseEntity<?> wipe(Integer courseId, Integer chapterId) {
        Course course = courseRepo.findById(courseId).orElse(null);

        for (Chapter chapter : course.getChapters()) {
            if (chapter.getId() == chapterId) {
                chapterRepo.deleteById(chapterId);
                return ResponseEntity.status(HttpStatus.OK).body("Wipe Chapter " + chapterId + " from Course " + courseId);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chapter " + chapterId + " not found!");
    }
}
