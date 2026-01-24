Coworking Space Booking System
Project description

This project is a simple console-based coworking space booking system written in Java.
The main idea of the project is to practice working with OOP, basic database logic, and layered architecture (entities, services, repositories).

The system allows users to register, book a workspace, and view existing bookings.
All data is stored in a database, and the application interacts with it through repositories.

This project was created as part of a university assignment.

Main features

User (client) registration

Workspace booking

Viewing all bookings

Data validation for user input

Separation of logic into layers (entity, repository, service, controller)

Basic authorization logic

Technologies used

Java

JDBC

PostgreSQL

Console input/output

Project structure

The project is divided into logical packages:

entity — contains entity classes (Booking, Guest, Room, etc.)

repository — classes responsible for working with the database

service — business logic of the application

controller — connects user input with services

app / main — application entry point

This structure helps keep the code readable and easier to maintain.

Database

The project uses PostgreSQL as a database.

Basic tables:

guests

rooms

bookings

Tables are connected using foreign keys.
For example, bookings references guests and rooms.

How to run the project

Install PostgreSQL

Create a database for the project

Configure database connection settings in the project

Run the Main (or MyApplication) class

Use the console menu to interact with the system

Notes

The project is intentionally kept simple

Focus was made on understanding OOP and database interaction

No graphical interface is used

Error handling is basic and done through console messages
