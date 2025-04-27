# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 1. Install Python and pip
RUN apt-get update && apt-get install -y python3 python3-pip

# 2. Copy the built jar
COPY --from=build /app/target/tracker-0.0.1-SNAPSHOT.jar app.jar

# 3. Copy your Python scripts (optional)
COPY src/main/java/com/tracker/quantumscripts/project/Scripts /app/scripts

# 4. Copy and install Python requirements (optional)
COPY requirements.txt .
RUN if [ -f "requirements.txt" ]; then pip3 install --no-cache-dir -r requirements.txt; fi

EXPOSE 8072
ENTRYPOINT ["java", "-jar", "app.jar"]
