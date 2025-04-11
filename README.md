# 🙏 Pagoda People Manager

**Cross-platform application to manage people in a Vietnamese pagoda** — including Buddhists, lay brothers, and the master — with synchronized data across desktop and web.

![Java](https://img.shields.io/badge/Core-Java-blue?logo=java)
![JavaFX](https://img.shields.io/badge/Desktop-JavaFX-green?logo=openjfx)
![SpringBoot](https://img.shields.io/badge/Backend-SpringBoot-brightgreen?logo=springboot)
![Angular](https://img.shields.io/badge/Web-Angular-red?logo=angular)
![CI](https://github.com/khoaowen/pagoda-people-manager/actions/workflows/ci.yml/badge.svg)


---

## ✨ Features

- Add, edit, and delete people records
- Export:
  - 📄 Individual profile to PDF (`Sơ yếu lý lịch`)
  - 📋 Full list to PDF (`Danh sách`)
- Sync data across platforms via REST API
- Cross-platform desktop apps (Windows, macOS)
- Responsive web app (Angular)
- Modular architecture with shared core logic

---

## 🧱 Project Structure

```bash
pagoda-people-manager/
│
├── core/             # Shared Java business logic
├── shared-models/    # DTOs shared between backend and frontend
├── backend-api/      # Spring Boot REST API
├── desktop-app/      # JavaFX UI
└── web-app/          # Angular frontend
