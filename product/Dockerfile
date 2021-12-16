FROM openjdk:17-jdk-bullseye

COPY target/product-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/product.service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/product.service.jar"]