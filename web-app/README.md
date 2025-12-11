# Pagoda People Manager - Web App

React + TypeScript + PWA frontend for managing people in a Vietnamese pagoda.

## 🚀 Quick Start

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Lint project
npm run lint

# Preview production build
npm run preview
```

## 📋 Features

- ✅ Add new people
- ✅ Search by name
- ✅ View people list
- ✅ Download individual PDF reports
- ✅ Download full list PDF
- ✅ PWA - installable as desktop/mobile app
- ✅ Offline shell via PWA caching (API data still requires connectivity)

## 🔧 Configuration

### Environment Variables

- Default fallback in code: `VITE_API_URL` → `http://localhost:8080/api/persons`
- Override for dev: set `VITE_API_URL=http://localhost:8080/api/persons` before `npm run dev`
- Override for prod build outside Docker: set `VITE_API_URL=/api/persons` before `npm run build` (Dockerfile already
  injects this by default)

### Deployment Options

**Option 1: Serve from Spring Boot (Recommended)**
```bash
npm run build
cp -r dist/* ../api/src/main/resources/static/
```
Access at: `http://localhost:8080`

**Option 2: Separate Deployment**
Deploy `dist/` folder to any static host (Netlify, Vercel, S3)
Set `VITE_API_URL` to your backend URL

## 📱 PWA Installation

Once deployed, users can install the app:
- **Desktop**: Click install icon in browser address bar
- **Mobile**: "Add to Home Screen" option

## 🏗️ Project Structure

```
src/
├── components/     # React components
│   ├── PersonForm.tsx
│   └── PeopleList.tsx
├── assets/         # Static assets (icons, images)
├── services/       # API calls
│   └── api.ts
├── pages/          # Future routed pages
├── App.css         # Component-level styles
├── types/          # TypeScript types
│   └── person.ts
├── App.tsx         # Main app
├── main.tsx        # Entry point
└── index.css       # Global styles
```
