# PLAYZONE

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
