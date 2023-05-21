# Use an OpenJDK 17 base image
FROM openjdk:17-oracle
# Set the working directory inside the container
WORKDIR /Collab_Backend

# Copy the JAR file from the target directory on the host to the container
COPY target/Collab_Backend-0.0.1-SNAPSHOT.jar .

# Expose the port your Spring Boot app is running on
EXPOSE 8081

# Define the command to run your Spring Boot app
CMD ["java", "-jar", "Collab_Backend-0.0.1-SNAPSHOT.jar"]
