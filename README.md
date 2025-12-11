# 🙏 Pagoda People Manager

**Multi-module Spring Boot backend with a React PWA** to manage people in a Vietnamese pagoda — including Buddhists, lay brothers, and the master.

![Java](https://img.shields.io/badge/Core-Java-blue?logo=java)
![SpringBoot](https://img.shields.io/badge/Backend-SpringBoot-brightgreen?logo=springboot)
![React](https://img.shields.io/badge/Web-React-61DAFB?logo=react&logoColor=white)
![CI](https://github.com/khoaowen/pagoda-people-manager/actions/workflows/ci.yml/badge.svg)

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=khoaowen_pagoda-people-manager)

---

## ✨ Features

- Create and search pagoda members with Spring Boot + PostgreSQL/H2
- Export PDF profile (`Sơ yếu lý lịch`) and complete roster (`Danh sách`)
- React PWA frontend served directly from the backend
- Multi-module hexagonal architecture (core, application, infrastructure, shared)
- Automated CI covering unit, integration, frontend, and E2E flows

---

## 🧱 Project Structure

```bash
pagoda-people-manager/
├── architecture-tests/   # ArchUnit rules guarding module boundaries
├── core/                 # Domain model (Person aggregate, value objects)
├── shared/               # DTOs, mappers, and cross-module contracts
├── application/          # Use cases and ports (CQRS style)
├── infrastructure/       # JPA adapters and PDF generation
├── api/                  # Spring Boot REST API (serves SPA + PDFs)
├── report-aggregate/     # Jacoco aggregate report module
├── web-app/              # React + Vite frontend (PWA)
├── docker-compose.test.yml  # Compose stack for Playwright E2E
└── test-e2e-docker.sh    # Docker-backed Playwright runner
```
