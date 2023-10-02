FROM maven AS build
WORKDIR /app
COPY ../../../../pom.xml .
COPY ../../.. ./src
RUN mvn clean package
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/telegram-bot-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","telegram-bot-0.0.1-SNAPSHOT.jar.jar"]