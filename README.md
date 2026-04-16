# Ridesharing_system_OOAD
# 🚗 PoolU – Carpooling System (OOAD Project)

## 📌 Overview

PoolU is a carpooling system that allows users to share rides efficiently. Drivers can create ride pools, and poolers can join them based on their routes. The system manages the complete ride lifecycle, including pool creation, joining, ride execution, pricing, and user reviews.

The project demonstrates the application of **Object-Oriented Design Principles (GRASP & SOLID)** and **Design Patterns** to build a modular and scalable system.

---

## 🚀 How to Run the Project

### 1. Run the application:

```bash
./mvnw spring-boot:run
```

### 2. Open in browser:

http://localhost:8081

### 3. Stop server:

Ctrl + C

---

## ⚙️ Requirements

* Java 17+ (tested with Java 25)
* Maven (wrapper included)

### ✔ Verified with:

```bash
./mvnw test
```

Result:
BUILD SUCCESS

---

## 🌐 Main UI Pages

| Feature           | URL                                   |
| ----------------- | ------------------------------------- |
| Home              | http://localhost:8081/                |
| Register Driver   | http://localhost:8081/register-driver |
| Register Pooler   | http://localhost:8081/register-pooler |
| Create Pool       | http://localhost:8081/create-pool     |
| View / Join Pools | http://localhost:8081/pools           |
| Pricing           | http://localhost:8081/pricing         |
| Add Review        | http://localhost:8081/add-review      |

---

## 🗄️ Database (H2 Console)

Open:
http://localhost:8081/h2-console

Use:

* JDBC URL: jdbc:h2:mem:pooldb
* User: sa
* Password: (leave blank)

### ⚠️ Note

* Database is **in-memory**
* Resets on every restart
* Tables recreated using:

```
spring.jpa.hibernate.ddl-auto=create-drop
```

---

## 📊 Useful SQL Queries

```sql
-- Users + Drivers
SELECT u.USER_ID, u.NAME, u.EMAIL, u.PHONE, u.KARMA_SCORE, d.LICENSE_NUMBER
FROM USERS u JOIN DRIVERS d ON u.USER_ID = d.USER_ID;

-- Users + Poolers
SELECT u.USER_ID, u.NAME, u.EMAIL, p.PAYMENT_METHOD
FROM USERS u JOIN POOLERS p ON u.USER_ID = p.USER_ID;

-- Pool + Driver details
SELECT p.POOL_ID, p.STATUS, u.NAME
FROM POOLS p 
JOIN DRIVERS d ON p.DRIVER_ID = d.USER_ID
JOIN USERS u ON d.USER_ID = u.USER_ID;

-- View all tables
SELECT * FROM POOLS;
SELECT * FROM REVIEWS;
SELECT * FROM RIDES;
SELECT * FROM USERS;
```

---

## 🌱 Seed Data (Auto-generated)

On startup, the system creates:

* Driver: Rahul (Honda City, MG Road)
* Poolers: Ananya, Arjun
* Pool: BTM Layout → Whitefield
* Review: 5-star rating for driver

**Note:** IDs are generated dynamically (UUIDs).

---

## 🔗 API Endpoints

### Authentication

```
POST /api/auth/drivers/register
POST /api/auth/poolers/register
POST /api/auth/login
```

### Pool Management

```
POST /api/pools
POST /api/pools/join
POST /api/pools/{poolId}/start
POST /api/pools/{poolId}/complete
GET  /api/pools
GET  /api/pools/{poolId}
```

### Reviews

```
POST /api/reviews
GET  /api/reviews/user/{userId}
GET  /api/reviews/user/{userId}/karma
```

### Pricing

```
GET /api/pricing/{poolId}/fare
GET /api/pricing/{poolId}/split
GET /api/pricing/{poolId}/breakdown
```

---

## 🔄 Recommended Demo Flow

1. Run the application
2. Open Home page
3. View existing pool
4. Open H2 console
5. Fetch user IDs
6. Create new users
7. Create a pool
8. Join pool
9. Start ride → Complete ride
10. Submit review

---

## 🏗️ Project Structure

```
src/main/java/com/poolu/poolu
```

### Backend

* controller → APIs & web pages
* model → Entities (User, Pool, Ride, etc.)
* repository → JPA repositories
* service → Business logic
* config → Seed data & configuration

### Frontend

* templates → HTML pages
* static/css → Styles
* static/js → Scripts

---

## 🧠 Design Concepts Used

### Design Patterns

* Factory Pattern
* Builder Pattern
* Facade Pattern
* Command Pattern
* Adapter Pattern

### Design Principles

* SRP (Single Responsibility Principle)
* OCP (Open-Closed Principle)
* DIP (Dependency Inversion Principle)
* Low Coupling (GRASP)
* Pure Fabrication & Indirection (GRASP)

---

## ✨ Key Features

* Pool creation and joining
* Ride lifecycle tracking
* Pricing and fare splitting
* Review and karma system
* Multi-user interaction
* Modular architecture

---

## 👥 Team Members

* Udaya Pragna G
* Pidapa Indira
* Sai Hemanth M
* Sai Jaswanth Akula

---

## 📌 Notes

* Uses in-memory database (resets on restart)
* Designed for OOAD demonstration
* Can be extended with real-time features

---

## 🎯 Conclusion

PoolU demonstrates how object-oriented design principles and patterns can be applied to build a scalable, maintainable, and well-structured system.
