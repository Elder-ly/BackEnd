# Build
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run
FROM amazoncorretto:17.0.12-al2023-headless

WORKDIR /app

COPY --from=build /build/target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]