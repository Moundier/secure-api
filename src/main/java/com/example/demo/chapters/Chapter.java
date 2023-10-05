package com.example.demo.chapters;

import java.util.Set;

import com.example.demo.course.Course;
import com.example.demo.lesson.Lesson;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "tbl_chapter")
public class Chapter {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;

    
    @ManyToMany(mappedBy = "chapters", fetch = FetchType.LAZY)
    private Set<Course> courses;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lesson> lessons;
}
