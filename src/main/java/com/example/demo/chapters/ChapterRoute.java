package com.example.demo.chapters;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chapter")
@RequiredArgsConstructor
public class ChapterRoute {
    
    private final ChapterService chapterService;

    @PostMapping("/{id}")
    public ResponseEntity<?> save(@PathVariable Integer id, @RequestBody Chapter chapter) {
        return chapterService.save(id, chapter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody Chapter lesson) {
        return chapterService.edit(id, lesson);
    }

    @DeleteMapping("/{courseId}/{chapterId}")
    public ResponseEntity<?> wipe(@PathVariable Integer courseId, @PathVariable Integer chapterId) {
        return chapterService.wipe(courseId, chapterId);
    }
}
