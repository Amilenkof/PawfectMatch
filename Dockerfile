FROM maven AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/pawfectmatch.jar .
CMD ["java","-jar","pawfectmatch.jar"]