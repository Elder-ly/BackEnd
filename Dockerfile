# Build
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run
FROM amazoncorretto:17.0.12-al2023-headless

VOLUME /tmp

EXPOSE 8080

COPY --from=build /app/target/elder-ly-1.0.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]