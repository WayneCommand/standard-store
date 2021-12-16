FROM maven:3-openjdk-17

RUN mvn clean package -Dmaven.javadoc.skip=true -B -V

COPY target/user-0.0.1-SNAPSHOT.jar /usr/local/share/standard-store/user.service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/share/standard-store/user.service.jar"]