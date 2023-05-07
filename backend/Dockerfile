FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY RELEASES/version_1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]