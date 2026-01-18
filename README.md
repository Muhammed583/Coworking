# Coworking Management System (Java)

## 📌 Project Description
This project is a simple Java-based coworking management system that demonstrates:
- work with a relational database (PostgreSQL)
- JOIN queries
- basic application of SOLID principles
- design patterns (Singleton, Repository)
- data validation and business logic separation

The system allows storing users, calculating booking prices, and retrieving booking history from multiple related tables.

---

## 🛠 Technologies Used
- Java
- JDBC
- PostgreSQL
- IntelliJ IDEA

---

## 🗂 Project Structure
Coworking
 ├── src
 │   ├── model
 │   │   ├── User.java
 │   │   ├── Workspace.java
 │   │   └── Booking.java
 │   │
 │   ├── repository
 │   │   ├── UserRepository.java
 │   │   ├── WorkspaceRepository.java
 │   │   └── BookingRepository.java
 │   │
 │   ├── service
 │   │   └── BookingService.java
 │   │
 │   ├── util
 │   │   └── DatabaseConnection.java
 │   │
 │   └── Main.java
 │
 ├── resources
 │   └── application.properties
 │
 └── README.md
