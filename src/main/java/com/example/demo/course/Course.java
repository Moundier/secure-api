package com.example.demo.course;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.chapters.Chapter;
import com.example.demo.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "tbl_course")
public class Course {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String imageURL;
    private String title;
    private String details;
    private Integer duration;
    private String level;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "tbl_course_user",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToOne
    private User courseOwner;

    @Builder.Default
    @OneToMany(mappedBy = "course")
    private Set<Chapter> chapters = new HashSet<>();

}