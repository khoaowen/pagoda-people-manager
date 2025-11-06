# Deployment Guide

## 🚀 CI/CD Pipeline

### Continuous Integration (CI)
Runs on every push/PR to `main`:
1. **Unit Tests** - Java unit tests
2. **Integration Tests** - Java integration tests
3. **Frontend Tests** - React lint + build
4. **SonarQube Scan** - Code quality analysis
5. **Build** - Creates deployable JAR with embedded frontend
6. **E2E Tests** - Playwright suite against Docker Compose stack

### Continuous Deployment (CD)
Runs on version tags (`v*`):
1. **Release** - Creates GitHub release with JAR
2. **Docker** - Builds and pushes Docker image

---

## 📦 Deployment Options

### Option 1: Railway.app (Recommended - Free) ✅

**Setup:**
1. Sign up at https://railway.app
2. New Project → Deploy from GitHub
3. Select your repository
4. Add PostgreSQL service
5. Set environment variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATASOURCE_URL=jdbc:postgresql://${{Postgres.HOST}}:${{Postgres.PORT}}/${{Postgres.DATABASE}}
   DATASOURCE_USERNAME=${{Postgres.PGUSER}}
   DATASOURCE_PASSWORD=${{Postgres.PGPASSWORD}}
   DATASOURCE_DRIVER=org.postgresql.Driver
   DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
   HIBERNATE_DDL_AUTO=update
   ```
   _Railway's `Postgres.DATABASE_URL` uses the `postgres://` prefix. Either assemble the JDBC URL as above or create your own variable that rewrites the prefix._
6. Deploy!

**Auto-deploy:** Push to GitHub → Railway auto-deploys

**Free tier:** $5/month credit (enough for small apps)

---

### Option 2: Render.com (Alternative - Free)

**Setup:**
1. Sign up at https://render.com
2. New Web Service → Connect GitHub
3. Docker environment detected automatically
4. Add PostgreSQL database
5. Set environment variables (same as Railway)
6. Deploy!

**Limitation:** App sleeps after 15 min inactivity

---

### Option 3: JAR File (Self-hosted)

```bash
# Download from GitHub releases
java -jar api-1.0.0.jar

# With external PostgreSQL
java -jar api-1.0.0.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/pagoda \
  --spring.datasource.username=pagoda \
  --spring.datasource.password=yourpassword
```

---

### Option 4: Docker (Self-hosted)

```bash
# Pull from GitHub Container Registry
docker pull ghcr.io/khoaowen/pagoda-people-manager:latest

# Run with environment variables
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATASOURCE_URL=jdbc:postgresql://host:5432/pagoda \
  -e DATASOURCE_USERNAME=pagoda \
  -e DATASOURCE_PASSWORD=yourpassword \
  -e DATASOURCE_DRIVER=org.postgresql.Driver \
  -e DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect \
  -e HIBERNATE_DDL_AUTO=update \
  ghcr.io/khoaowen/pagoda-people-manager:latest
```

---

## 🔧 Environment Variables

### Production
```bash
export SPRING_PROFILES_ACTIVE=prod
export DATASOURCE_URL=jdbc:postgresql://localhost:5432/pagoda
export DATASOURCE_USERNAME=pagoda
export DATASOURCE_PASSWORD=your-password
export DATASOURCE_DRIVER=org.postgresql.Driver
export DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
export HIBERNATE_DDL_AUTO=update
```

---

## 📋 Release Process

### Create a new release:
```bash
# Tag version
git tag v1.0.0
git push origin v1.0.0

# CI/CD automatically:
# 1. Builds frontend + backend
# 2. Creates GitHub release
# 3. Builds Docker image
# 4. Pushes to registry
```

---

## 🔍 Monitoring

Access management endpoints:
- Health: `http://localhost:8080/actuator/health`
- Info: `http://localhost:8080/actuator/info`
