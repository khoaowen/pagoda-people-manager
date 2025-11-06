# Stage 1: Build frontend
FROM node:20-alpine AS frontend-build
WORKDIR /app/web-app
COPY web-app/package*.json ./
RUN npm ci
COPY web-app/ ./
RUN npm run build

# Stage 2: Build backend
FROM maven:3.9-eclipse-temurin-21 AS backend-build
WORKDIR /app
COPY pom.xml ./
COPY core/ ./core/
COPY application/ ./application/
COPY infrastructure/ ./infrastructure/
COPY shared/ ./shared/
COPY api/ ./api/
COPY architecture-tests/ ./architecture-tests/
COPY report-aggregate/ ./report-aggregate/

# Copy frontend build to backend static resources
COPY --from=frontend-build /app/web-app/dist/ ./api/src/main/resources/static/

# Build backend with embedded frontend
RUN mvn clean package -Pexecutable-jar,skip-all-tests

# Stage 3: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/api/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
