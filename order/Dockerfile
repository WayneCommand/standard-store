FROM openjdk:17-jdk-bullseye

COPY target/order-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/order.service.jar

EXPOSE 8030
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/order.service.jar"]