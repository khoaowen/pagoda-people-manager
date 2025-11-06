# Railway Deployment Guide

## 🚀 Quick Deploy (5 minutes)

### Step 1: Sign Up
1. Go to https://railway.app
2. Sign up with GitHub

### Step 2: Create Project
1. Click "New Project"
2. Select "Deploy from GitHub repo"
3. Choose `pagoda-people-manager`
4. Railway detects `Dockerfile` automatically ✅

### Step 3: Add Database
1. Click "New" → "Database" → "Add PostgreSQL"
2. Railway creates database automatically

### Step 4: Configure Environment Variables
Click on your app service → Variables → Add:

```
SPRING_PROFILES_ACTIVE=prod
DATASOURCE_URL=jdbc:postgresql://${{Postgres.HOST}}:${{Postgres.PORT}}/${{Postgres.DATABASE}}
DATASOURCE_USERNAME=${{Postgres.PGUSER}}
DATASOURCE_PASSWORD=${{Postgres.PGPASSWORD}}
DATASOURCE_DRIVER=org.postgresql.Driver
DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
HIBERNATE_DDL_AUTO=update
```

> Note: Railway exposes `Postgres.DATABASE_URL` in `postgres://` format. The application expects the `jdbc:postgresql://` form, so either use the host/port/database variables as above or create a custom variable that rewrites the prefix.

### Step 5: Deploy
1. Railway builds and deploys automatically
2. Wait 5-10 minutes for first build
3. Click "Generate Domain" to get public URL

### Step 6: Access Your App
```
https://your-app.up.railway.app
```

---

## 🔄 Auto-Deploy

Every push to `main` branch triggers automatic deployment!

```bash
git push origin main
# Railway automatically rebuilds and deploys
```

---

## 📊 Monitor

**View logs:**
- Railway Dashboard → Your Service → Logs

**Check health:**
```
https://your-app.up.railway.app/actuator/health
```

---

## 💰 Free Tier

- $5/month credit
- Enough for:
  - Small app (always running)
  - PostgreSQL database
  - ~500 hours/month runtime

---

## 🐛 Troubleshooting

**Build fails:**
- Check Dockerfile is correct
- Verify all dependencies in pom.xml

**App won't start:**
- Check environment variables
- View logs in Railway dashboard

**Database connection fails:**
- Verify Postgres service is running
- Check DATABASE_URL variable is set

---

## 🎉 Done!

Your app is now live and auto-deploys on every push!
