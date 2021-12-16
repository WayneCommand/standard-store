FROM openjdk:17-jdk-bullseye

COPY target/stock-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/stock.service.jar

EXPOSE 8010
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/stock.service.jar"]