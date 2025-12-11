# Scripts Guide

## Available Scripts

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

Start the backend first (`cd api && mvn spring-boot:run`) before executing the E2E commands. If you need the backend to serve the built SPA, run `npm run build` in `web-app` and copy `web-app/dist/*` into `api/src/main/resources/static/` before starting Spring Boot.

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
