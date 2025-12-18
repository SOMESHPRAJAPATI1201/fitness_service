# ---------- STAGE 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# copy pom + download dependencies first (build caching)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# copy source code and build
COPY src ./src

RUN mvn -q clean package -DskipTests

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy built jar from builder stage
COPY --from=builder /app/target/*.jar eureka-server.jar

# expose your microservice port
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]