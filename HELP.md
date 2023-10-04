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
- [ ] Flyway
- [ ] Link Users to Courses
- [ ] Prepare each Postman Request
    * User -> Access Course
    * User -> Access Chapter
    * User -> Access Lesson
    * Admin -> Create Complete Course

- [ ] Only Admin can Modify Course aspects
- [ ] Add Spring Validation Annotations
- [ ] How to implement Cookie 


```json

{
  "course": {
    "imageURL": "course_image_url_here",
    "title": "Course Title",
    "details": "Course Details",
    "duration": 10,
    "level": "Beginner",
    "courseOwner": {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com"
    },
    "chapters": [
      {
        "title": "Chapter 1",
        "description": "Chapter 1 Description",
        "lessons": [
          {
            "title": "Lesson 1",
            "description": "Lesson 1 Description",
            "isLessonComplete": false
          },
          {
            "title": "Lesson 2",
            "description": "Lesson 2 Description",
            "isLessonComplete": false
          }
        ]
      },
      {
        "title": "Chapter 2",
        "description": "Chapter 2 Description",
        "lessons": [
          {
            "title": "Lesson 1",
            "description": "Lesson 1 Description",
            "isLessonComplete": false
          }
        ]
      }
    ]
  }
}
```


