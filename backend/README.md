# PLAYZONE API

Spring Boot REST API for PLAYZONE.

## API groups
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/tournaments`
- `GET /api/tournaments/{id}`
- `POST /api/tournaments/{id}/register`
- `POST /api/tournaments` (authenticated; protect as admin in production)
- `PATCH /api/tournaments/{id}/status`
- `POST /api/tournaments/{id}/finish`
- `GET /api/matches/tournament/{id}`
- `POST /api/matches/{id}/result`
- `POST /api/matches/{id}/verify`
- `GET /api/leaderboard`
- `GET /api/health`

Before production, restrict admin-only endpoints with `ROLE_ADMIN` at the security layer and add object-level authorization for every mutation.
