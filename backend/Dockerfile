# Vaihe 1: Rakenna JAR Mavenilla JDK 21:llä
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Kopioidaan pom.xml ja ladataan riippuvuudet (nopeuttaa välimuistin ansiosta)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Kopioidaan lähdekoodi ja rakennetaan
COPY src ./src
RUN mvn clean package -DskipTests

# Vaihe 2: Runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Kopioidaan rakennettu JAR
COPY --from=build /app/target/*.jar app.jar

# Käynnistetään Spring Boot
ENTRYPOINT ["java","-jar","app.jar"]
