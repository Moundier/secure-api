package com.example.demo.chapters;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.course.Course;
import com.example.demo.lesson.Lesson;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne 
    private Course course;

    @Builder.Default
    @OneToMany(mappedBy = "chapter")
    private Set<Lesson> lessons = new HashSet<>();
}
