# Spring Security Template

A reusable Spring Boot backend template implementing secure authentication and authorization using Spring Security, JWT, HttpOnly cookies, and role-based access control.

This project is designed as a starting point for developers building REST APIs with Spring Boot.

## Features

- User registration and login
- JWT-based authentication
- JWT stored in HttpOnly cookies
- Stateless session management
- Password encryption using BCrypt
- Role-based authorization
- Custom UserDetails implementation
- Custom authentication entry point
- Custom access-denied handler
- Global exception handling
- Request validation
- MySQL database integration
- Centralized JWT configuration
- Reusable authentication utility

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.2 | Backend framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database operations |
| MySQL 8 | Relational database |
| JJWT 0.12.6 | JWT generation and validation |
| Lombok | Reduce boilerplate code |
| Maven | Dependency management |

## Project Structure

```text
src/main/java/com/example/springsecuritytemplate/
│
├── config/
│   └── DataInitializer.java
│
├── controller/
│   ├── AuthController.java
│   ├── RoleTestController.java
│   └── TestController.java
│
├── dto/
│   ├── LoginRequest.java
│   ├── SignupRequest.java
│   ├── MessageResponse.java
│   └── UserInfoResponse.java
│
├── exception/
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceAlreadyExistsException.java
│
├── model/
│   ├── AppRole.java
│   ├── Role.java
│   └── User.java
│
├── repository/
│   ├── RoleRepository.java
│   └── UserRepository.java
│
├── security/
│   ├── AuthEntryPointJwt.java
│   ├── AuthTokenFilter.java
│   ├── CustomAccessDeniedHandler.java
│   ├── CustomUserDetails.java
│   ├── CustomUserDetailsService.java
│   ├── JwtProperties.java
│   ├── JwtUtils.java
│   └── SecurityConfig.java
│
├── service/
│   └── AuthService.java
│
└── util/
    └── AuthUtil.java
```

## Authentication Flow

1. A user registers using the signup endpoint.
2. The application stores the user with an encrypted password.
3. The user logs in using the signin endpoint.
4. Spring Security authenticates the credentials.
5. The application generates a JWT.
6. The JWT is stored in an HttpOnly cookie.
7. The authentication filter validates the JWT on subsequent requests.
8. Spring Security uses the authenticated user's roles to authorize requests.

## Role-Based Authorization

The application supports three roles:

- `ROLE_USER`
- `ROLE_ADMIN`
- `ROLE_SELLER`

New users are assigned the default `ROLE_USER` role during registration.

## Getting Started

### Prerequisites

Install the following:

- Java 21 or later
- MySQL 8 or later
- Maven
- IntelliJ IDEA or another Java IDE

### 1. Clone the repository

```powershell
git clone https://github.com/sankpatil/spring-security-template.git
cd spring-security-template
```

### 2. Create the MySQL database

```sql
CREATE DATABASE spring_security_template;
```

### 3. Configure environment variables

Set your database credentials and JWT secret in PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
$env:JWT_SECRET="your_base64_encoded_secret"
```

Generate a secure Base64 JWT secret using PowerShell:

```powershell
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$bytes = New-Object byte[] 64
$rng.GetBytes($bytes)
$env:JWT_SECRET = [Convert]::ToBase64String($bytes)
$rng.Dispose()
```

Keep your actual credentials and JWT secret private. Never commit them to GitHub.

### 4. Run the application

```powershell
mvn spring-boot:run
```

The application runs at:

```text
http://localhost:8080
```

## Authentication Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/signup` | Register a new user |
| POST | `/api/auth/signin` | Authenticate a user |

Authentication uses an HttpOnly cookie named `springSecurityTemplate`.

## Security Configuration

The application follows these security practices:

- Stateless authentication
- BCrypt password hashing
- JWT signature validation
- HttpOnly authentication cookies
- Configurable JWT expiration
- Configurable cookie settings
- Role-based endpoint protection
- Custom handling of unauthorized and forbidden requests

## Configuration

JWT and cookie settings are managed through `application.properties`.

Sensitive values are supplied through environment variables instead of being hardcoded into the repository.

## Future Improvements

Possible extensions for projects built using this template:

- Refresh token implementation
- Email verification
- Password reset functionality
- OAuth2 authentication
- Swagger / OpenAPI documentation
- Production deployment configuration

## Author

**Sanket Patil**

GitHub: [@sankpatil](https://github.com/sankpatil)

---

If this template helps you build your Spring Boot application, feel free to fork it and customize it for your requirements.

**Happy Coding!**
