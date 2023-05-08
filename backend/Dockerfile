ARG SERVER_PORT=${SERVER_PORT}

FROM eclipse-temurin:17-jdk-alpine

ARG SERVER_PORT
ENV SERVER_PORT=${SERVER_PORT}

VOLUME /tmp
COPY RELEASES/version_1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]