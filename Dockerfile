
# Use an official OpenJDK 17 image as the base image
FROM openjdk:17-slim

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy your project files into the container (e.g., pom.xml and source code)
COPY . /app

# Run Maven to install dependencies and build the project (produces the .jar)
RUN mvn clean test package

# Expose the port your application will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run the generated .jar file
CMD ["java", "-jar", "target/GroupProject-Development.jar"]
