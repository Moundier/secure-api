# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file (Spring Boot application) into the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/ 

# Expose the port your Spring Boot application is running on (default is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
