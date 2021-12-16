FROM openjdk:17-jdk-bullseye

COPY target/bag-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/bag.service.jar

EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/bag.service.jar"]