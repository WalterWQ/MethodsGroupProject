FROM openjdk:latest
WORKDIR /tmp
COPY ./target/classes/group8 /app/group8
COPY ./target/GroupProject-Development.jar /app/GroupProject-Development.jar
ENTRYPOINT ["java", "-jar", "/app/GroupProject-Development.jar"]