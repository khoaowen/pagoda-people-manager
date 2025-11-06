# E2E Testing Guide

## 🎯 Setup

- Install dependencies once: `npm install`
- Start the Spring Boot backend in another terminal before running tests:
  ```bash
  # From repo root
  ./run-local.sh
  ```
  or manually:
  ```bash
  npm run build          # ensure fresh frontend assets
  cp -r dist/* ../api/src/main/resources/static/
  (cd ../api && mvn spring-boot:run)
  ```
  The backend must stay running and serving `http://localhost:8080` while tests execute.

---

## 🚀 Running Tests Locally

### Option 1: Headless (Fast)
```bash
npm run test:e2e
```
- Runs all tests in all browsers (Chrome, Firefox, Safari)
- Requires backend running on port 8080
- Results in terminal

### Option 2: UI Mode (Best for Development) ✨
```bash
npm run test:e2e:ui
```
- Beautiful visual interface
- Watch tests run in real-time
- Time-travel debugging
- Pick which tests/browsers to run

### Option 3: Debug Mode
```bash
npm run test:e2e:debug
```
- Step through tests line by line
- Inspect elements
- Pause execution

---

## 📝 Writing New Tests

Create files in `web-app/e2e/`:

```typescript
import { test, expect } from '@playwright/test'

test('my new test', async ({ page }) => {
  await page.goto('/')
  await page.click('button')
  await expect(page.locator('h1')).toContainText('Success')
})
```

---

## 🔍 Test Reports

After running tests:
```bash
npx playwright show-report
```

Opens HTML report with:
- Screenshots
- Videos (on failure)
- Traces
- Logs

---

## 🌐 Testing Specific Browser

```bash
npx playwright test --project=chromium
npx playwright test --project=firefox  
npx playwright test --project=webkit
```

---

## ⚙️ How It Works

1. You build the frontend and start the Spring Boot backend yourself (`./run-local.sh` is the easiest path).
2. Playwright hits the live app at `http://localhost:8080` and exercises the full stack.
3. When you're done, stop the backend with `Ctrl+C` in the terminal where it is running.

---

## 🐛 Troubleshooting

**Backend won't start:**
```bash
# Check if port 8080 is busy
lsof -i :8080
kill -9 <PID>
```
- Make sure the `dist/` folder was copied to `api/src/main/resources/static/` (use `./run-local.sh` to automate).

**Frontend assets look stale:**
- Rebuild: `npm run build`
- Re-run `./run-local.sh` to copy fresh assets before retrying tests.

**Need to see what's happening:**
```bash
npm run test:e2e:ui
```
