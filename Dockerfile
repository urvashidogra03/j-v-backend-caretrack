FROM openjdk:17-jdk-slim
LABEL authors="HP"
COPY target/myapp.jar CareTrackApplication.jar
ENTRYPOINT ["java", "jar","CareTrackApplication.jar"]