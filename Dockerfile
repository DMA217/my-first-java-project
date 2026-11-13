FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

RUN ls -la build/libs/

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]