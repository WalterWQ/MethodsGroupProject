FROM openjdk:latest
WORKDIR /tmp
COPY ./target/classes/group8 /app/group8
COPY ./target/GroupProject-Development-shaded.jar /app/GroupProject-Development-shaded.jar
ENTRYPOINT ["java", "-jar", "/app/GroupProject-Development-shaded.jar"]