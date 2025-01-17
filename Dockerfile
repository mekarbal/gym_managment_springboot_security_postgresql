FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gym_management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4001

ENTRYPOINT ["java", "-jar", "app.jar"]
