# Stage 1: Build the project
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: # Stage 2: Copy the project, create group and user and use it in order to run the application
# Docs: https://spring.io/guides/gs/spring-boot-docker
FROM eclipse-temurin:21-jre
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "app.jar"]

# Base setup
#FROM eclipse-temurin:21-jdk
#WORKDIR /app
#COPY --from=builder /app/target/*.jar app.jar
#EXPOSE 8181
#ENTRYPOINT ["java", "-jar", "app.jar"]
