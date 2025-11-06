#!/bin/bash

set -e

echo "🐳 Building Docker image..."
docker-compose -f docker-compose.test.yml build

echo "🚀 Starting application in Docker..."
docker-compose -f docker-compose.test.yml up -d

echo "⏳ Waiting for application to be healthy..."
for i in {1..60}; do
  if docker-compose -f docker-compose.test.yml ps | grep -q "healthy"; then
    echo "✅ Application is healthy!"
    break
  fi
  echo "Waiting... ($i/60)"
  sleep 2
done

if ! docker-compose -f docker-compose.test.yml ps | grep -q "healthy"; then
  echo "❌ Application failed to start"
  docker-compose -f docker-compose.test.yml logs
  docker-compose -f docker-compose.test.yml down
  exit 1
fi

echo "✅ Application is ready!"

echo "🧪 Running E2E tests..."
cd web-app
npm run test:e2e

TEST_EXIT_CODE=$?

echo "🛑 Stopping Docker containers..."
cd ..
docker-compose -f docker-compose.test.yml down

if [ $TEST_EXIT_CODE -eq 0 ]; then
  echo "✅ All tests passed!"
else
  echo "❌ Tests failed!"
  exit $TEST_EXIT_CODE
fi
