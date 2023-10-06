package com.example.demo.course;

import java.sql.Date;

import java.util.Set;

import com.example.demo.chapters.Chapter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "tbl_course")
public class Course {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private String imageURL;
    private String title;
    private String details;
    private Date duration;
    private String level;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_course_chapter",
        joinColumns = {
            @JoinColumn(name = "course_id", referencedColumnName = "id")
        }, 
        inverseJoinColumns = {
            @JoinColumn(name = "chapter_id", referencedColumnName = "id")
        }
    )
    private Set<Chapter> chapters;
}