package com.example.demo;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.auth.AuthService;
import com.example.demo.auth.AuthRoute.RegisterDTO;
import com.example.demo.chapters.Chapter;
import com.example.demo.course.Course;
import com.example.demo.lesson.Lesson;
import com.example.demo.user.User;

@SpringBootApplication
public class Main {

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner runner() {

		return args -> {

			List<User> USERS = List.of(
					new User(null, "nami", "namizo", "nami@gmail.com", "1234", null),
					new User(null, "usop", "sogeking", "usop@gmail.com", "1234", null));

			List<User> ADMINS = List.of(
					new User(null, "zoro", "roronoa", "zoro@gmail.com", "1234", null),
					new User(null, "luff", "monkey", "luff@gmail.com", "1234", null));

			// Lesson lesson = Lesson.builder()
			// .id(null)
			// .title("Lesson Title")
			// .description("Lesson Descritpion")
			// .isLessonComplete(false)
			// .lessonReadme(null)
			// .chapter(null)
			// .build();

			// Chapter chapter = Chapter.builder()
			// .id(null)
			// .title("Chapter Title")
			// .description("Chapter Description")
			// .lessons(null)
			// .build();

			// Course course = Course.builder()
			// .id(null)
			// .imageURL("path/to/image.png")
			// .title("Course Title 1")
			// .slug("course-title")
			// .details("Course Details")
			// .duration(new Date(0))
			// .level("Easy")
			// .chapters(null)
			// .build();

			// // TODO: Mapping Relations and autowire later
			// Set<Chapter> chapters = new HashSet<>();
			// chapters.add(chapter);

			// Set<Lesson> lessons = new HashSet<>();
			// lessons.add(lesson);

			// course.createSlug();
			// course.setChapters(chapters);

			// chapter.setLessons(lessons);
			// lesson.setChapter(chapter);

			// TODO: Saving to Tables
			for (User user : USERS) {

				RegisterDTO response = new RegisterDTO(
						user.getFirstname(),
						user.getLastname(),
						user.getEmail(),
						user.getPassword());

				authService.signup(response);
			}

			for (User admin : ADMINS) {

				RegisterDTO response = new RegisterDTO(
						admin.getFirstname(),
						admin.getLastname(),
						admin.getEmail(),
						admin.getPassword());

				authService.authorize(response);
			}
		};
	}
}
