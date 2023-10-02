#FROM maven:3-eclipse-temurin-20 AS MAVEN
#WORKDIR /build/
#COPY pom.xml /build
#COPY src /build/src
#RUN mvn clean package -DskipTests=true
#
#FROM eclipse-temurin:20-jdk
#COPY --from=MAVEN /build/target/stm-labs.jar /opt/PawfectMatch.jar
#ENTRYPOINT java -Xms512m -Xmx1024m -Duser.timezone=UTC -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9999 -jar -Djava.security.egd=file:/dev/./urandom /opt/PawfectMatch.jar
#
FROM maven AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/pawfectmatch.jar .
CMD ["java","-jar","pawfectmatch.jar"]