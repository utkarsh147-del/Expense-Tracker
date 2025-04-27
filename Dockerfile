# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .  # Copy everything
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Install Python and pip
RUN apt-get update && apt-get install -y python3 python3-pip

# Copy JAR from the build stage
COPY --from=build /app/target/tracker-0.0.1-SNAPSHOT.jar app.jar

# Copy the Python scripts
COPY src/main/java/com/tracker/quantumscripts/project/Scripts /app/scripts

EXPOSE 8072
ENTRYPOINT ["java", "-jar", "app.jar"]
