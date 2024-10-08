FROM openjdk:latest
COPY ./target/classes/group8 /tmp/group8
WORKDIR /tmp
ENTRYPOINT ["java", "group8.project_files.Main"]