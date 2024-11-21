FROM openjdk:latest
COPY ./target/classes/org /tmp/org
WORKDIR /tmp
ENTRYPOINT ["java", "org.group8.projectfiles.Main"]