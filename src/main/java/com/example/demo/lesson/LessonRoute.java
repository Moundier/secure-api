package com.example.demo.lesson;

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
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonRoute {

    private final LessonService lessonService;

    @PostMapping("/{courseId}/{chapterId}")
    public ResponseEntity<?> save(@PathVariable Integer courseId, @PathVariable Integer chapterId, @RequestBody Lesson newer) {
        return lessonService.save(courseId, chapterId, newer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, Lesson lesson) {
        return lessonService.edit(id, lesson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        return lessonService.find(id);
    }

    // @GetMapping("/{title}")
    // public ResponseEntity<?> findLessonBySmug(@PathVariable String title, Lesson lesson) {
    //     return null;
    // }

    @DeleteMapping("/{chapterId}/{lessonId}")
    public ResponseEntity<?> wipe(@PathVariable Integer chapterId, @PathVariable Integer lessonId) {
        return lessonService.wipe(chapterId, lessonId);
    }
}
