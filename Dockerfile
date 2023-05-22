# Use an OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8081
VOLUME /tmp

# Copy the JAR file from the target directory on the host to the container
COPY target/*.jar Collab_Backend-0.0.1-SNAPSHOT.jar

# Define the command to run your Spring Boot app
CMD ["java", "-jar", "/Collab_Backend-0.0.1-SNAPSHOT.jar"]