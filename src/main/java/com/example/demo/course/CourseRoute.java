package com.example.demo.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseRoute {

    private final CourseService courseService;

    @PostMapping("/save")
    public ResponseEntity<Course> save(@RequestBody Course course) {
        return new ResponseEntity<Course>(courseService.save(course), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {

        Course course = courseService.find(id);

        return (course != null) ? // Ternary Response
            new ResponseEntity<>(courseService.find(id), HttpStatus.OK) :
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody Course course) {
        return new ResponseEntity<>(courseService.edit(id, course), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> wipe(@PathVariable Integer id) {
        courseService.wipe(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
