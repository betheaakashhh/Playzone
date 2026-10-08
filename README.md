# PLAYZONE

<!-- readme-sync-bot:toc:start -->
## Table of Contents

- [Stack](#stack)
- [Features](#features)
- [Run locally](#run-locally)
- [Docker](#docker)
- [First admin](#first-admin)
- [Important product/compliance note](#important-productcompliance-note)
- [Configuration](#configuration)
- [📋 Recommended Sections Checklist](#-recommended-sections-checklist)
<!-- readme-sync-bot:toc:end -->

Daily competitive social gaming platform MVP.

## Stack
- Frontend: React + Vite + pure CSS
- Backend: Spring Boot 3.5 + Spring Security + JWT
- Database: MongoDB
- Deployment-ready Docker setup

## Features
- Player registration/login
- Tournament discovery and registration
- Admin tournament creation
- Tournament status management API
- Player XP and leaderboard
- Match result reporting + evidence URL
- Admin result verification API
- Responsive mobile-first UI
- Paid-entry/prize features intentionally compliance-gated

## Run locally

### Backend
```bash
cd backend
cp .env.example .env
mvn spring-boot:run
```
MongoDB must be available at the configured `MONGODB_URI`.

### Frontend
```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

## Docker
```bash
cd backend
docker compose up --build
```

## First admin
For development, create a normal user and promote its `role` field to `ADMIN` in MongoDB. A production release should use a controlled admin provisioning flow rather than exposing role changes.

## Important product/compliance note
The MVP does not implement collection of player entry fees or redistribution of a pooled cash pot. Before enabling any paid-entry/prize mechanics, obtain India-specific legal/compliance review for the exact game, tournament rules, funding source, payment flow, age/KYC requirements, and applicable online-gaming/payment rules.

## Configuration

| Variable | Description | Required |
|----------|-------------|----------|
| `VITE_API_BASE_URL` | Vite Api Base Url | Optional |

<!-- readme-sync-bot:checklist:start -->
## 📋 Recommended Sections Checklist

_The bot can't write these automatically — they need your judgment, not a diff. This list updates itself as you add them:_

- [ ] License
- [ ] Author / Contact
- [ ] Contributing Guidelines
- [ ] Acknowledgements
- [ ] Testing
- [ ] Deployment
<!-- readme-sync-bot:checklist:end -->
