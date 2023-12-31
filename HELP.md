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

## Future
- [ ] Implement Refresh Token.
- [ ] Implement RBAC 
- [ ] Implement Logout.
- [ ] Implement Validation Annotations

## Todo
- add single column slugHash
- [ ] Dockerfile & docker-compose
- [ ] Dardo-san idea of slug
- [ ] I missed creating get for chapter
- [ ] GET data Course and all its Chapters plus first Lesson
- [ ] Persist some data placeholder
- [ ] Change API Controllers paths
- [ ] Consume in frontend (via-visual)

// When Delete Lesson, pass only Lesson id, not Chapter and Lesson id, just lesson id
// Create Crow Foot Entity Model 
// Make Docker Work
// Users subscribe to course or to other users like youtube?

```sql
select * from tbl_course;
select * from tbl_chapter;
select * from tbl_lesson;
select * from tbl_course_chapter;
select * from tbl_chapter_lesson;
```

# Mvn and Docker
- `mvn clean package where the .pom is contained`
- `sudo docker build -t secure-api: latest .`
- `sudo docker compose up -d --build`


### Important
- In Login, we dont need to pass token. Login generates a token from valid credentials. 
- 

```sql
SELECT tbl_course.title, tbl_chapter.title, tbl_lesson.title
FROM tbl_course
LEFT JOIN tbl_course_chapter ON tbl_course.id = tbl_course_chapter.course_id
LEFT JOIN tbl_chapter ON tbl_course_chapter.chapter_id = tbl_chapter.id
LEFT JOIN tbl_chapter_lesson ON tbl_chapter.id = tbl_chapter_lesson.chapter_id
LEFT JOIN tbl_lesson ON tbl_chapter_lesson.lesson_id = tbl_lesson.id
WHERE tbl_course.id = 1;

select * from tbl_course;
```
