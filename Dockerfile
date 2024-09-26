FROM gradle:7.6.0-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle build -x test

FROM amazoncorretto:17 AS runner

WORKDIR /app

ARG JWT_SECRET
ENV JWT_SECRET=$JWT_SECRET

ARG DB_URL
ENV DB_URL=$DB_URL

ARG DB_USER
ENV DB_USER=$DB_USER

ARG DB_PASSWORD
ENV DB_PASSWORD=$DB_PASSWORD

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]