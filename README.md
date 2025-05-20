
# Smart Inventory Management System - Backend (Microservices Architecture)

A secure, modular, and scalable backend system for managing inventory and orders, built using Java, Spring Boot, Kafka, and MySQL. This project uses microservices architecture and includes authentication and role-based access using JWT tokens.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot, Spring Data JPA, Spring Security
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token), BCrypt
- **Inter-Service Communication**: Apache Kafka
- **Testing**: Postman
- **Tools**: IntelliJ IDEA, Git

---

## 📦 Microservices Overview

### 🔐 Auth Service (`auth-service`)
- User registration and login.
- JWT-based authentication.
- Role-based access control (ADMIN, INVENTORY_MANAGER, CUSTOMER).
- Validates tokens in request headers using a custom `JwtFilter`.

### 📦 Inventory Service (`inventory-service`)
- CRUD operations on inventory items.
- Supports fields like name, quantity, location, category, supplier, price, dateAdded.
- Connected to Kafka to update stock when orders are placed.

### 🛒 Order Service (`order-service`)
- Accepts orders from customers.
- Validates stock availability from inventory.
- Assigns the optimal warehouse based on delivery address city.
- Sends order events via Kafka.

---

## 🔒 Authentication Flow

- `/api/auth/signup`: Register new users.
- `/api/auth/login`: Login with email and password to receive a JWT token.
- Use `Authorization: Bearer <JWT_TOKEN>` header to access secured endpoints in other services.

---

## 📑 Sample API Calls

### Register (Signup)

```http
POST /api/auth/signup
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "CUSTOMER"
}
````

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

### Secure Request (with JWT)

```http
GET /api/inventory/items
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
```

---

## 📂 Project Structure

```
smart-inventory-system/
├── auth-service/
├── inventory-service/
└── order-service/
```

Each service is an independent Spring Boot application with its own:

* `application.yml`
* `Controller`, `Service`, `Repository`, `Entity`, `DTO`, `Config`, `Util` layers

---

## ✅ Features

* Stateless JWT authentication
* Password hashing with BCrypt
* Role-based authorization
* MySQL database integration
* Kafka-based service-to-service communication
* Modular design using microservices

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/smart-inventory-system.git
cd smart-inventory-system
```

### 2. Set up MySQL Databases

Create three databases:

* `auth_db`
* `inventory_db`
* `order_db`

Update DB credentials in each service’s `application.yml`.

### 3. Start Kafka & Zookeeper (manually or via local install)

```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka
bin/kafka-server-start.sh config/server.properties
```

### 4. Run Each Service

```bash
# In separate terminals or IntelliJ run configs
cd auth-service
./mvnw spring-boot:run

cd inventory-service
./mvnw spring-boot:run

cd order-service
./mvnw spring-boot:run
```

---

## 🧪 Testing

Use **Postman** to:

* Register and login users
* Access inventory and order endpoints with JWT
* Simulate order placement and inventory updates via Kafka

---

## 📌 Note

* This is a backend-only project.
* No frontend UI, Docker, or cloud deployment is included.
* This project is still under active development.
* Features are being added and refined progressively. Expect updates, improvements, and possible changes to APIs or architecture.

---



