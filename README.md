# User Management API

REST API for user management built with Java 21, Spring Boot 3.5, Gradle and H2.

## Requirements

- JDK 21+
- Internet access for Gradle dependencies

## Run

```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

H2 console:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:userdb`
- User: `sa`
- Password: empty

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | List users |
| GET | `/api/users/{id}` | Find a user |
| POST | `/api/users` | Create a user |
| PUT | `/api/users/{id}` | Replace a user's details |
| DELETE | `/api/users/{id}` | Delete a user |

Create or update body:

```json
{
  "name": "Chrystian",
  "email": "chrystian@example.com"
}
```

## Tests

```bash
./gradlew test
```
