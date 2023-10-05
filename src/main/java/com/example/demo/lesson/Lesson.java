package com.example.demo.lesson;

import java.io.File;
import java.util.Set;

import com.example.demo.chapters.Chapter;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_lesson")
public class Lesson {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    private Boolean isLessonComplete;
    private File lessonReadme;

    @ManyToMany(mappedBy = "lessons", fetch = FetchType.LAZY)
    private Set<Chapter> chapters;
}
