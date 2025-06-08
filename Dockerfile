FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/tourism-service-0.0.1-SNAPSHOT.jar"] 