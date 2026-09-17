# Wanderly — Travel & Tour Agency Website

Full-stack resume project: **Spring Boot (backend)** + **HTML/CSS/JavaScript (frontend)**.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend Framework | Spring Boot 3.3 |
| Web | Spring Web (REST APIs) |
| Security | Spring Security 6 + JWT (jjwt) — role-based access (USER / ADMIN) |
| Database | Spring Data JPA + H2 (in-memory, zero setup) |
| Validation | Spring Boot Starter Validation |
| Frontend | Plain HTML, CSS, JavaScript (no framework — easy to explain in interviews) |

## Features

- Public: browse tour packages, view details, send contact message
- User: register, login (JWT), book a package, view "My Bookings"
- Admin: login, add/edit/delete packages, view all bookings & update status, view contact messages
- Spring Security protects admin-only endpoints (`POST/PUT/DELETE /api/packages`, `GET /api/bookings/all`, etc.) using JWT + role checks
- Passwords stored BCrypt-hashed, never in plain text
- Sample data (6 packages + 1 admin account) auto-seeded on first run

## Project Structure

```
wanderly-travel-agency/     <- open THIS folder in IntelliJ (pom.xml sits right here)
├── pom.xml
├── src/main/java/com/travelagency/
│   ├── config/       -> SecurityConfig, JwtUtil, JwtAuthFilter, DataSeeder
│   ├── controller/   -> AuthController, PackageController, BookingController, ContactController
│   ├── model/        -> User, TourPackage, Booking, ContactMessage, Role, BookingStatus
│   ├── repository/   -> Spring Data JPA repositories
│   ├── dto/          -> Request/response objects
│   └── exception/    -> Global validation error handler
├── src/main/resources/application.properties
└── frontend/         # Plain HTML/CSS/JS (not part of the Maven build)
    ├── index.html, packages.html, booking.html, my-bookings.html
    ├── login.html, register.html, contact.html, admin.html
    ├── css/style.css
    └── js/api.js
```

## Opening in IntelliJ IDEA

1. Unzip the file.
2. In IntelliJ: **File → Open** → select the `wanderly-travel-agency` folder (the one containing `pom.xml`).
3. IntelliJ will detect it as a Maven project and prompt to load Maven changes — click **"Load Maven Project"** (or it may auto-import). It will then download all dependencies (needs internet).
4. Once indexed, open `TravelAgencyApplication.java` and click the green ▶ Run button.
5. The `frontend/` folder is just static files sitting alongside the backend — right-click `frontend/index.html` in the IntelliJ project tree → **Open in Browser**.

## How to Run (command line, alternative)

Requirements: **Java 17+** and **Maven** installed.

```bash
cd wanderly-travel-agency
mvn spring-boot:run
```

The API will start at **http://localhost:8080**.

On first run it auto-creates:
- Admin login: `admin@travelagency.com` / `admin123`
- 6 sample tour packages

H2 database console (optional, for viewing data): http://localhost:8080/h2-console
(JDBC URL: `jdbc:h2:mem:travelagencydb`, username: `sa`, no password)

### 2. Frontend

The frontend is plain HTML/CSS/JS, so you just need to open it in a browser or serve it with any static server.

**Easiest way** — just double-click `frontend/index.html` to open it in your browser.

**Recommended way** (avoids some browser quirks) — serve it locally:
```bash
cd frontend
python3 -m http.server 5500
```
Then open http://localhost:5500 in your browser.

> Make sure the backend (step 1) is running first — the frontend calls it at `http://localhost:8080/api`.
> If you deploy the backend elsewhere, update `API_BASE_URL` in `frontend/js/api.js`.

## API Overview

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/packages` | Public |
| GET | `/api/packages/{id}` | Public |
| POST | `/api/packages` | Admin |
| PUT | `/api/packages/{id}` | Admin |
| DELETE | `/api/packages/{id}` | Admin |
| POST | `/api/bookings` | Authenticated user |
| GET | `/api/bookings/my` | Authenticated user |
| GET | `/api/bookings/all` | Admin |
| PUT | `/api/bookings/{id}/status` | Admin |
| POST | `/api/contact` | Public |
| GET | `/api/contact` | Admin |

## Resume Bullet Point (suggestion)

> Built a full-stack travel booking website using Spring Boot, Spring Security (JWT-based role authentication), Spring Data JPA, and a vanilla HTML/CSS/JavaScript frontend, implementing user/admin roles, package management, and a booking workflow.

## Notes

- This project was generated in a sandboxed environment without internet access, so it hasn't been Maven-built here — dependency versions are pinned to stable releases. Run `mvn spring-boot:run` on your own machine (with internet, for Maven to download dependencies) to build and start it. If you hit any compile error, paste it back and it can be fixed quickly.
- For a real production deployment you'd want to swap H2 for MySQL/PostgreSQL — just update `application.properties` and add the relevant driver dependency.
