# Scripts Guide

## Available Scripts

### `./run-local.sh`
**Purpose:** Run full-stack app locally (production-like)

**What it does:**
1. Builds frontend
2. Copies to backend static resources
3. Starts Spring Boot (frontend embedded)

**Usage:**
```bash
./run-local.sh
```

Access at: `http://localhost:8080`

---

### `./test-e2e-docker.sh`
**Purpose:** Run E2E tests with Docker

**What it does:**
1. Builds Docker image (frontend + backend)
2. Starts container
3. Runs Playwright E2E tests
4. Stops container

**Usage:**
```bash
./test-e2e-docker.sh
```

---

## Development Workflows

### Backend Development
```bash
# Run tests
mvn clean install

# Run app
cd api && mvn spring-boot:run
```

### Frontend Development
```bash
cd web-app

# Dev mode (hot reload)
npm run dev

# Lint
npm run lint

# Build
npm run build

# E2E tests (requires backend running on :8080)
npm run test:e2e
npm run test:e2e:ui
```

Start the backend first (`./run-local.sh` or `cd api && mvn spring-boot:run`) before executing the E2E commands.

### Full Stack Testing
```bash
# Complete integration test
./test-e2e-docker.sh
```

---

## Docker Files

### `Dockerfile`
**Purpose:** Build Docker image for deployment

**Usage:**
```bash
# Build image
docker build -t pagoda-app .

# Run locally
docker run -p 8080:8080 pagoda-app
```

---

### `docker-compose.test.yml`
**Purpose:** E2E testing (used by `test-e2e-docker.sh`)

**Usage:**
```bash
# Used automatically by test-e2e-docker.sh
# Or manually:
docker compose -f docker-compose.test.yml up
```

**Includes:**
- App container with H2 in-memory database
- Health check for test readiness
