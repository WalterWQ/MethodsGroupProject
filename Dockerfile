# Use a specific Java 17 JDK image to ensure it runs the dependincies with no issues with diffrent java 17 builds
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the self-contained JAR into the container
COPY GroupProject-1.0-SNAPSHOT.jar /app/GroupProject.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "GroupProject.jar"]
