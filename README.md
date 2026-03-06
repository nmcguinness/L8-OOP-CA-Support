---
title: "DAO + JDBC Demo"
author: "Niall McGuinness"
institute: "Dundalk Institute of Technology"
programme: "BSc (Hons) in Computing in Software Development / Games Development"
module_code: "COMP C8Z03"
module_title: "Object-Oriented Programming"
topic: "Database Connectivity and DAO"
version: "1.1"
generated_at: "2026-03-06"
---

# DAO + JDBC Demo

## Overview

This project is a simple Java demonstration of how to connect to a MySQL database using JDBC and how to apply the **DAO (Data Access Object)** pattern to perform CRUD operations.

This demo provides a simple end-to-end example of:

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

The SQL setup script is stored in the **`sql`** folder at the root of the project.

```text
N_Tier_System/
├─ .idea/
├─ .mvn/
├─ sql/
│  └─ create_db.sql
└─ src/
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
   └─ test/
      └─ java/
         └─ daoexample/
            └─ db/
               └─ DatabaseConnectionTest.java
```

## Where to Find the SQL

Use the file below to create and populate the database:

```text
sql/create_db.sql
```

This file should:
- create the `taskhub` database
- create the `tasks` table
- insert a few sample rows

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

## Step 1: Start MySQL / phpMyAdmin

Make sure XAMPP (Apache and PHPMyAdmin server) is running.

## Step 2: Run the SQL Script

In phpMyAdmin:

1. open **phpMyAdmin**
2. click the **SQL** tab, or use **Import**
3. open the file:

```text
sql/create_db.sql
```

4. run the script

This should create:

- the `taskhub` database
- the `tasks` table
- some sample data

## Step 3: Check Your Database Username and Password

Open `DatabaseConnection.java` and update the connection details to match your local MySQL setup.

Example:

```java
private static final String URL =
    "jdbc:mysql://127.0.0.1:3306/taskhub";

private static final String USER = "root";
private static final String PASS = "";
```

Things to check carefully:

- **database name** is `taskhub`
- **host** is usually `127.0.0.1` or `localhost`
- **port** is usually `3306`
- **username** is often `root` on local student machines
- **password** may be blank, `root`, or a custom password depending on your setup

## Step 4: Open the Project in IntelliJ

Open the Maven project in IntelliJ IDEA.

When IntelliJ loads the project, make sure:

- Maven is recognised
- the `pom.xml` file is detected
- the project folders appear correctly

## Step 5: Reload the Maven POM

After opening the project, reload Maven so IntelliJ downloads the required libraries.

In IntelliJ, do one of the following:

- click the Maven refresh / reload button
- right-click `pom.xml` and choose **Reload Maven Project**
- open the Maven tool window and refresh the project

This is important because the project depends on:

- MySQL Connector/J
- JUnit 5

If you do not reload the POM, the JDBC and test imports may show as missing.

## Step 6: Ensure the JDK / SDK is Installed and Selected

This project uses Java through IntelliJ, so a suitable JDK must be installed on your machine.

In IntelliJ, check:

- **File > Project Structure**
- confirm that a **Project SDK** is selected
- confirm that the **language level** is appropriate for the project

If no SDK is selected:

1. install a JDK on your machine
2. return to IntelliJ
3. set that JDK as the project SDK

If the SDK is missing or incorrect, the project may not compile even if the code is correct.

## Step 7: Maven Dependencies

Click on the `M` for Maven icon on the right hand side and reload all dependencies. This will normally eliminate any `RED` errors or java files with a **red** and not **blue** file icon.

## Step 8: Run the Database Connection Test

Before running the full demo, run the database test to confirm:

- the JDBC driver is working
- the database is reachable
- the connection string is correct
- the username and password are correct

Example test class:

```text
src/test/java/daoexample/db/DatabaseConnectionTest.java
```

## Step 9: Run the Demo

Run `Main.java` from IntelliJ.

The demo should then perform:

- create a task
- read tasks
- update a task
- delete a task

## Example SQL Script

A typical setup script looks like this:

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
jdbc:mysql://127.0.0.1:3306/taskhub
```

You will also need:

- a MySQL username
- a MySQL password

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

## Common Errors

If the project does not work, check the following:

- MySQL is running
- the `taskhub` database exists
- the `tasks` table exists
- the username/password are correct
- the JDBC URL is correct
- the MySQL connector dependency is in the `pom.xml`
- Maven dependencies have been refreshed in the IDE
- a valid JDK / SDK is selected in IntelliJ

