# Coworking Booking System

## About the Project
This is a **simple console-based coworking booking system** written in Java.  
The main goal of this project is to practice **object-oriented programming**, work with **databases using JDBC**, and apply basic **design patterns**.

Users can register, log in, view available workspaces, book them, and see their booking history.

---

## Technologies
- Java
- PostgreSQL
- JDBC
- Object-Oriented Programming (OOP)

---

## Project Structure
The project is divided into logical layers to keep the code clean and easy to understand:

- `model` – classes that represent data (users, workspaces, bookings)
- `repository` – classes that work with the database
- `service` – business logic of the application
- `util` – helper classes (database connection)
- `MyApplication` – main class that runs the program

---

## How It Works
1. A user starts the application
2. Registers or logs in
3. Views available workspaces
4. Books a workspace
5. Checks booking history
6. Logs out or exits the system

All interaction is done through a **console menu**.

---

## Design Choices
- **Singleton Pattern** is used for database connection
- **Repository Pattern** separates SQL logic from the rest of the code
- Interfaces are used to reduce tight coupling between classes

---

## Database
The application uses a PostgreSQL database with three main tables:
- `auth_users`
- `workspaces`
- `bookings`

Relationships between tables are handled using foreign keys.

---

## How to Run
1. Create a PostgreSQL database
2. Update database credentials in `DatabaseConnection`
3. Run `MyApplication`
4. Follow the instructions in the console

---

## What Can Be Improved
This project can be extended in the future by:
- Adding a graphical or web interface
- Using Spring Framework
- Adding validation and tests
- Improving error handling

---

## Authors
Olzhas Sailau  
Mukhammed  
Zhaniya

Group: ST2507
