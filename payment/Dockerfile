FROM openjdk:17-jdk-bullseye

COPY target/payment-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/payment.service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/payment.service.jar"]