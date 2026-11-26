# Auth Service (Spring Security & JWT)

Authentication and JWT token management service built with **Java 17**, **Spring Boot 3**, **Spring Security 6**, and **JJWT**.

---

## 📌 Features

1. **Spring Security 6 & Stateless Architecture**:
   - Session management set to `STATELESS`.
   - CSRF disabled.
   - Global 401 Unauthorized handling via `JwtAuthenticationEntryPoint`.
2. **JJWT Utility**:
   - HMAC-SHA256 signature algorithm.
   - Encodes username, granted roles/authorities, `issuedAt`, and `expiration`.
   - Validates signature integrity, expiry, and structure.
3. **Custom OncePerRequestFilter**:
   - Parses the `Authorization: Bearer <token>` header on every request.
   - Populates `SecurityContextHolder` with authenticated user details and granted authorities.
4. **Pre-configured Demo Credentials**:
   - `admin` / `admin123` $\rightarrow$ Roles: `ROLE_ADMIN`, `ROLE_USER`
   - `user` / `user123` $\rightarrow$ Role: `ROLE_USER`
5. **Endpoints**:
   - `POST /api/auth/login`: Authenticates credentials and returns a JWT.
   - `GET /api/auth/profile`: Protected endpoint requiring a valid Bearer token.

---

## 🏗️ Architecture & Package Layout

```
src/main/java/com/example/authservice/
├── AuthServiceApplication.java
├── config/
│   └── SecurityConfig.java
├── controller/
│   └── AuthController.java
├── dto/
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   └── UserProfileResponse.java
└── security/
    ├── JwtAuthenticationEntryPoint.java
    ├── JwtAuthenticationFilter.java
    └── JwtTokenProvider.java
```

---

## ⚙️ Configuration Properties

Configured in `src/main/resources/application.properties`:

| Property | Description | Default Value |
|----------|-------------|---------------|
| `server.port` | Server port | `8091` (override via `SERVER_PORT`) |
| `jwt.secret` | 256-bit Hex secret key for HMAC-SHA256 | Required via `JWT_SECRET` |
| `jwt.expiration-ms` | Token expiration time in milliseconds | `86400000` (24h) |
| `eureka.client.service-url.defaultZone` | Eureka registry URL | `http://localhost:8761/eureka/` |

---

Set `JWT_SECRET` to a unique 256-bit key before starting the service. Do not commit the key to source control.

## 🚀 Building & Running

```bash
# Navigate to auth-service folder
cd auth-service

# Build the project
mvn clean install

# Run the Auth Service
mvn spring-boot:run
```

---

## 📡 Testing the Auth Endpoints

### 1. Authenticate and Generate JWT
```bash
curl -X POST http://localhost:8091/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```
**Sample Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsIn...",
  "tokenType": "Bearer",
  "username": "admin",
  "roles": ["ROLE_ADMIN", "ROLE_USER"]
}
```

---

### 2. Access Protected Endpoint with Bearer Token
```bash
curl -X GET http://localhost:8091/api/auth/profile \
  -H "Authorization: Bearer <TOKEN_RECEIVED_ABOVE>"
```
**Sample Response:**
```json
{
  "username": "admin",
  "authorities": [
    {"authority": "ROLE_ADMIN"},
    {"authority": "ROLE_USER"}
  ],
  "message": "Access granted to protected resource! Authentication successful."
}
```

---

### 3. Attempt Access Without Token (Unauthorized Check)
```bash
curl -X GET http://localhost:8091/api/auth/profile
```
**Returns HTTP 401 Unauthorized:**
```json
{
  "timestamp": "2026-11-20T21:35:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/api/auth/profile"
}
```
