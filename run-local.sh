#!/bin/bash

echo "🔨 Building frontend..."
cd web-app
npm run build

echo "📦 Copying to backend..."
cd ..
mkdir -p api/src/main/resources/static
cp -r web-app/dist/* api/src/main/resources/static/

echo "🚀 Starting Spring Boot..."
cd api
mvn spring-boot:run
