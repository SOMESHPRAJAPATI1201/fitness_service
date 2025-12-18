# ---------- STAGE 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# copy pom + download dependencies first (build caching)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# copy source code and build
COPY src ./src
RUN mvn -q -DskipTests clean package

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# expose your microservice port
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]