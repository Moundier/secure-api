package com.example.demo.course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.chapters.Chapter;
import com.example.demo.chapters.ChapterRepo;
import static com.example.demo.course.CourseRoute.*;
import com.example.demo.course.CourseRoute.CourseDTO;
import com.example.demo.lesson.Lesson;
import com.example.demo.lesson.LessonRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepo courseRepo;
    private final ChapterRepo chapterRepo;
    private final LessonRepo lessonRepo;

    public CourseDTO save(CourseDTO courseDTO) {
        
        Course course = Course.builder()
            .imageURL(courseDTO.course().getImageURL())
            .title(courseDTO.course().getTitle())
            .details(courseDTO.course().getDetails())
            .duration(courseDTO.course().getDuration())
            .level(courseDTO.course().getLevel())
            .build();

        Set<ChapterDTO> CHAPTERS = courseDTO.chapters();

        courseRepo.save(course);

        for (ChapterDTO chapterDTO : CHAPTERS) {
            
            Chapter chapter = Chapter.builder()
                .title(chapterDTO.chapter().getTitle()) 
                .description(chapterDTO.chapter().getTitle())
                .course(course)
                .build();

            chapterRepo.save(chapter);

            Set<LessonDTO> lessons = chapterDTO.lessons();

            for (LessonDTO lessonDTO : lessons) {
                
                Lesson lesson = Lesson.builder()
                    .title(lessonDTO.lesson().getTitle())
                    .description(lessonDTO.lesson().getDescription())
                    .isLessonComplete(lessonDTO.lesson().getIsLessonComplete())
                    .lessonReadme(lessonDTO.lesson().getLessonReadme())
                    .chapter(chapter)
                    .build();

                lessonRepo.save(lesson);
            }
        }
        
        return courseDTO;
    }

    public Course find(Integer id) {
        return courseRepo.findById(id).orElseThrow(() -> notFound404());
    }

    public List<Course> findAll() {
        
    
        return courseRepo.findAll();
    }
    

    public Course edit(Integer id, Course course) {
        var older = courseRepo.findById(id).orElseThrow(() -> notFound404());
        Course newer = null;

        if (older != null) {
            newer = Course.builder()
              .imageURL(course.getImageURL())
              .title(course.getTitle())
              .details(course.getDetails())
              .duration(course.getDuration())
              .level(course.getLevel())
              .build();
        }
        
        return courseRepo.save(newer);
    }

    public void wipe(Integer id) {
        courseRepo.deleteById(id);
    }

    public RuntimeException notFound404() {
        return new RuntimeException("Course was not found in database!");
    }

}