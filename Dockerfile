# === Stage 1: Build and compile the application ===
FROM maven:3.9-eclipse-temurin-26 AS build
WORKDIR /app
# Copy the pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .
# Download dependencies (this layer won't rebuild unless pom.xml changes)
RUN mvn dependency:go-offline -B
# Copy the source code
COPY src ./src
# Compile and package the application into a JAR file, skipping unit tests
RUN mvn clean package -DskipTests

# # === Stage 2: Minimal runtime environment ===
FROM eclipse-temurin:26-jre-alpine
WORKDIR /app
# Create a non-root system user for security purposes
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# Copy the compiled executable JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
# Expose the standard Spring Boot embedded server port
EXPOSE 8080
# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]
