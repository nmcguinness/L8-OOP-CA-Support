---
title: "DAO + JDBC Demo"
author: "Niall McGuinness"
institute: "Dundalk Institute of Technology"
programme: "BSc (Hons) in Computing in Software Development / Games Development"
module_code: "COMP C8Z03"
module_title: "Object-Oriented Programming"
topic: "Database Connectivity and DAO"
version: "1.0"
generated_at: "2026-03-06"
---

# DAO + JDBC Demo

## Overview

This project is a simple Java demonstration of how to connect to a MySQL database using JDBC and how to apply the **DAO (Data Access Object)** pattern to perform CRUD operations.

It shows how to:

- define a simple domain class
- connect Java to a MySQL database
- separate SQL/database code from application logic
- implement CRUD operations through a DAO
- run a basic database connection test with JUnit

## Learning Goals

By working through this demo, you should be able to:

- explain the purpose of the DAO pattern
- create a simple Java domain class
- connect a Java application to MySQL using JDBC
- write SQL for create, read, update, and delete operations
- implement a DAO interface and JDBC-based DAO class
- write a basic integration test for a database connection

## Project Structure

```text
src/
├─ main/
│  └─ java/
│     └─ daoexample/
│        ├─ Main.java
│        ├─ domain/
│        │  └─ Task.java
│        ├─ dao/
│        │  ├─ TaskDao.java
│        │  └─ JdbcTaskDao.java
│        └─ db/
│           └─ DatabaseConnection.java
│
└─ test/
   └─ java/
      └─ daoexample/
         └─ db/
            └─ DatabaseConnectionTest.java
```

## Main Classes

### `Task`
A simple domain class representing a task stored in the database.

Example fields:

- `id`
- `title`
- `status`

### `TaskDao`
An interface defining the CRUD operations available for `Task` objects.

Typical methods:

- `insert(...)`
- `findById(...)`
- `findAll()`
- `updateStatus(...)`
- `deleteById(...)`

### `JdbcTaskDao`
A JDBC-based implementation of `TaskDao`.

This class:
- contains the SQL queries
- opens database connections
- uses `PreparedStatement`
- maps result rows to Java objects

### `DatabaseConnection`
A helper class responsible for creating JDBC connections to the MySQL database.

It also includes a simple smoke test approach using:

```sql
SELECT 1
```

### `Main`
A small console demo that shows the four CRUD operations through separate methods:

- create
- read
- update
- delete

## Database Setup

Create the database and sample data in phpMyAdmin or MySQL using the script below.

```sql
CREATE DATABASE IF NOT EXISTS taskhub;
USE taskhub;

CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(120) NOT NULL,
    status ENUM('TODO', 'DOING', 'DONE') NOT NULL DEFAULT 'TODO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX idx_tasks_status ON tasks(status);

INSERT INTO tasks (title, status) VALUES
('Finish lab exercise', 'TODO'),
('Prepare JDBC demo', 'DOING'),
('Submit assignment', 'DONE');
```

## JDBC Connection String

A typical local MySQL JDBC connection string is:

```java
jdbc:mysql://127.0.0.1:3306/taskhub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

You will also need:

- a MySQL username
- a MySQL password

Example:

```java
private static final String URL =
    "jdbc:mysql://127.0.0.1:3306/taskhub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

private static final String USER = "root";
private static final String PASS = "your_password";
```

## Maven Dependencies

This project uses:

- **MySQL Connector/J** for JDBC connectivity
- **JUnit 5** for testing

Example `pom.xml` dependencies section:

```xml
<dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.6.0</version>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Example Surefire plugin:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.5.5</version>
        </plugin>
    </plugins>
</build>
```

## Running the Demo

### 1. Create the database
Run the SQL script in phpMyAdmin or MySQL Workbench.

### 2. Update connection details
Edit `DatabaseConnection.java` and set:

- database URL
- username
- password

### 3. Build the project
Use Maven to download dependencies and compile:

```bash
mvn compile
```

### 4. Run the application
Run `Main.java` from IntelliJ or your IDE.

The demo will then perform:

- create a task
- read tasks
- update a task
- delete a task

## Example CRUD Flow

The `Main` class is structured so that each CRUD action is demonstrated in its own method.

Typical flow:

- `createTaskDemo(...)`
- `readTaskDemo(...)`
- `updateTaskDemo(...)`
- `deleteTaskDemo(...)`

This keeps the code easy to read and makes each database operation easier to explain in class.

## Testing

A basic JUnit test can be added for the database connection.

Example idea:

- open a connection
- run `SELECT 1`
- confirm the result is returned successfully

Example test responsibilities:

- verify the connection opens
- verify SQL can be executed
- verify a result can be read

## Important Note About Testing

The database test is not a pure unit test in the strict sense. It is better described as an **integration test**, because it depends on:

- MySQL being installed and running
- the database existing
- the username/password being correct
- the JDBC driver being present

This is still very useful for this topic because it proves that the Java application can actually talk to the database.

## Key DAO Ideas Demonstrated

This project demonstrates several important ideas from the DAO topic:

- the application works with Java objects instead of raw database rows
- SQL is isolated inside the DAO implementation
- the DAO interface hides database details from the rest of the program
- `PreparedStatement` is used to safely pass values into SQL
- result rows are mapped into domain objects

## Why Use a DAO?

Without a DAO, SQL code often gets scattered throughout the application.

With a DAO:

- the code is easier to maintain
- the SQL is easier to find
- the rest of the program does not need to know JDBC details
- testing and future refactoring become easier

## Common Errors

If the project does not work, check the following:

- MySQL is running
- the `taskhub` database exists
- the `tasks` table exists
- the username/password are correct
- the JDBC URL is correct
- the MySQL connector dependency is in the `pom.xml`
- Maven dependencies have been refreshed in the IDE

## Suggested Extension Tasks

Once the basic demo works, try extending it by:

- adding a `findByStatus(...)` method
- adding a `count()` method
- adding validation for allowed statuses
- adding a second domain class and DAO
- building a simple menu-driven console UI
- writing tests for DAO methods

## Summary

This demo provides a simple end-to-end example of:

- Java + JDBC
- MySQL connectivity
- DAO pattern
- CRUD operations
- basic database integration testing

It is designed as a foundation for learning how Java applications can interact with relational databases in a structured and object-oriented way.
