# ContactUs API

Simple Spring Boot service for receiving and listing contact messages per project.

## Stack

- Java 21
- Spring Boot 4
- Spring Web + Validation
- Spring Data JPA
- PostgreSQL
- Flyway

## What It Does

- Stores contact messages in PostgreSQL
- Protects message endpoints with a project token header
- Scopes reads and writes to the project linked to that token

## Auth Model

Requests to `/api/messages/**` require:

- Header: `X-Project-Token`

The token must:

- exist in `token.token_encoded`
- have `status = ACTIVE`
- have `expires_at` in the future

Requests to `/api/tokens/**` require:

- Header: `X-Admin-Key`

## Endpoints

### Public API

#### `POST /api/messages`

Creates a message under the token's project. Requires project token.

Body:

```json
{
  "message": "Need pricing details for the pro plan.",
  "subject": "Pricing request",
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phone": "+1-555-1000",
  "type": "sales"
}
```

Example:

```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -H "X-Project-Token: dev-token-123" \
  -d '{"message":"Need pricing details","subject":"Pricing","name":"Jane Doe","email":"jane@example.com"}'
```

### Admin API

#### `GET /api/admin/messages`

Returns messages for a specific project. Requires admin key.

Example:

```bash
curl "http://localhost:8080/api/admin/messages?projectId=1" \
  -H "X-Admin-Key: your-admin-key"
```

#### `POST /api/tokens`

Creates a token for a project. Requires admin key.

```bash
curl -X POST http://localhost:8080/api/tokens \
  -H "Content-Type: application/json" \
  -H "X-Admin-Key: your-admin-key" \
  -d '{"projectId":1,"expiresInDays":365}'
```

#### `POST /api/tokens/rotate`

Revokes an existing token and issues a new one for the same project. Requires admin key.

```bash
curl -X POST http://localhost:8080/api/tokens/rotate \
  -H "Content-Type: application/json" \
  -H "X-Admin-Key: your-admin-key" \
  -d '{"tokenEncoded":"dev-token-123","expiresInDays":365}'
```

#### `POST /api/tokens/revoke`

Revokes a token. Requires admin key.

```bash
curl -X POST http://localhost:8080/api/tokens/revoke \
  -H "Content-Type: application/json" \
  -H "X-Admin-Key: your-admin-key" \
  -d '{"tokenEncoded":"dev-token-123"}'
```

## Local Setup

1. Create PostgreSQL database (example: `contactus`).
2. Create local config file:
   - copy `src/main/resources/application-local.properties.example`
   - save as `src/main/resources/application-local.properties`
3. Set DB credentials in that local file.
4. Start app:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Flyway runs on startup and applies migrations from:

- `src/main/resources/db/migration`

## Required Configuration

Base config uses env vars in `src/main/resources/application.properties`:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

CORS allowed origins:

- `contactus.cors.allowed-origins`
- supports comma-separated values, for example:
  - `http://localhost:3000,http://localhost:5173`

Admin key (for token management endpoints):

- `ADMIN_API_KEY` (required - generate with: `openssl rand -base64 32`)

Abuse protection settings:

- `contactus.abuse.max-requests`
- `contactus.abuse.window-seconds`

## Quick Token Seed (Dev)

If you need a token for local testing:

```sql
INSERT INTO token (project_id, token_encoded, expires_at, status)
VALUES (1, 'dev-token-123', '2099-01-01 00:00:00', 'ACTIVE');
```
