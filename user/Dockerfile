FROM openjdk:17-jdk-bullseye

COPY target/user-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/user.service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/user.service.jar"]