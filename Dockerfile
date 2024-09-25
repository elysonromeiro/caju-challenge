FROM gradle:7.6-jdk17 AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/
COPY src /app/src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

ARG JWT_SECRET
ENV JWT_SECRET=$JWT_SECRET

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]