# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#web.security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#using.devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## Project Achievements
- [x] Follow Git best practices (branching, commit messages).
- [x] Implement user authentication and authorization.
- [x] Design and implement RESTful API endpoints.
- [x] Implement Security authentication and authorization.
- [x] Implement Jwt Bearer.

Future
- [ ] Implement Jwt Refresh Token.
- [ ] Implement Logout.

## Todo
- [ ] Edit Course Passing Chapters and Sections
- [ ] Dockerfile Dockercompose
- [ ] Add Spring Validation Annotations
- [/] (Low Priority) Edit User by roles get null

- [ ] Link Users to Courses
* Link users to courses
* users that are admins can Sew
* normal users can only see

- [] if Access course as admin, course reference to user is equal to course id, then can Sew
- [] if Access course as admin, but reference differente, cannot Sew

- Not so okay
Edit Course
Find only course aspects and not its children 
at saving, we can add another column to the 

## Hyperlink

https://app.gleek.io

```json
// Connect shapes together using different connection types
User {1}--{0..n} Course
Course {1}--{1..n} Chapter
Chapter {1}--{1..n} Lesson


Course
	int id
	string imageURL
	string title
	string details
	Date duration
	String level

Chapter
	int id
	string title
	string description

Lesson
	int id
	string title
	string description
	boolean isLessonComplete
	file lessonReadme

User
	int id
	string firstname
	string lastname
	string email
	string password
	Role role


>>>>>> SQL

SELECT tbl_course.title, tbl_chapter.title, tbl_lesson.title
FROM tbl_course
LEFT JOIN tbl_course_chapter ON tbl_course.id = tbl_course_chapter.course_id
LEFT JOIN tbl_chapter ON tbl_course_chapter.chapter_id = tbl_chapter.id
LEFT JOIN tbl_chapter_lesson ON tbl_chapter.id = tbl_chapter_lesson.chapter_id
LEFT JOIN tbl_lesson ON tbl_chapter_lesson.lesson_id = tbl_lesson.id
WHERE tbl_course.id = 1;

select * from tbl_course;
```