# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Install Python
RUN apt-get update && apt-get install -y python3 python3-pip
# Copy Python scripts
COPY src/main/java/com/tracker/quantumscripts/project/Scripts /app/Scripts
COPY --from=build /app/target/tracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8072
ENTRYPOINT ["java", "-jar", "app.jar"]