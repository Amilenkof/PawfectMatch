FROM maven:3-openjdk-20-slim AS MAVEN
WORKDIR /build/
COPY pom.xml /build
COPY src /build/src
RUN mvn clean package -DskipTests=true

FROM openjdk-20-slim
COPY --from=MAVEN /build/target/PawfectMatch.jar /opt/PawfectMatch.jar
ENTRYPOINT java -Xms512m -Xmx1024m -Duser.timezone=UTC -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9999 -jar -Djava.security.egd=file:/dev/./urandom /opt/PawfectMatch.jar