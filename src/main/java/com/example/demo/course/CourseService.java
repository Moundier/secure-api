package com.example.demo.course;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepo courseRepo;

    public Course save(Course course) {
        return courseRepo.save(course);
    }

    public Course find(Integer id) {
        return courseRepo.findById(id).orElseThrow(() -> notFound404());
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