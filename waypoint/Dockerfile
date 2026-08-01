FROM eclipse-temurin:26-jdk AS build

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:26-jdk

WORKDIR /app

COPY --from=build /workspace/target/waypoint-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
