# Shop & Credit Management System
**Course:** Object-Oriented Programming (OOP)
**University:** Sukkur IBA University

## 1. Project Purpose
This application is a specialized Shop Management System designed to handle retail transactions and track customer credit. It allows a shopkeeper to manage inventory, record sales, and maintain a digital ledger of what customers owe (BORROW) and have repaid (PAY).

## 2. Group Members
* **Sanam Nawaz** | CMS ID: 023-25-0515 | Section: B
* **Kashish Kumari** | CMS ID: 023-25-0216 | Section: B

## 3. Core Modules (Project Structure)
The project is organized for modularity and clarity:
* **`/src`**: Contains all Java source code (Model and DAO classes).
* **`/db`**: Contains the `shop_db.sql` script to set up the MySQL tables.
* **`/lib`**: Contains the MySQL Connector JAR required for database connectivity.

## 4. Key OOP Features

* **Inheritance**: The `Customer` class inherits from the abstract `Person` class.
* **Abstraction**: Uses an `abstract class Person` to define the base structure of human entities in the system.
* **Encapsulation**: Data is protected using `private` and `protected` modifiers, with public getters and setters used to access fields.
* **Polymorphism**: The `getName()` method is overridden in the `Customer` class to provide specific behavior.
* **Data Persistence**: Uses JDBC to ensure all transaction data is saved in a relational database.


## Features
- Add and manage customers
- Store product details
- Record customer borrow transactions
- Record payment transactions
- Maintain customer credit ledger
- Save data using MySQL database
- Generate organized transaction records

## Technologies Used
- Java
- JDBC
- MySQL
- MySQL Workbench
- GitHub
- Object-Oriented Programming Concepts


## 5. How to Run

### Requirements
- JDK 17 or above
- MySQL Server
- MySQL Workbench

### Database Setup
1. Open MySQL Workbench.
2. Run the SQL script located in `/db/shop_db.sql`.
   - This script will automatically:
     - Create the `shop_db` database
     - Create all required tables
     - Insert any initial data (if included)
3. Ensure MySQL server is running before executing the script.
4. Update MySQL username and password in `DBConnection.java` according to your local setup.

### Dependencies
- Include `mysql-connector-j-9.7.0.jar` in the project classpath.

### Compile & Run
1. Open terminal in the project root folder.
2. Navigate to source folder:
   `cd src`

3. Compile:
   `javac -cp ".;../lib/mysql-connector-j-9.7.0.jar" *.java`

4. Run:
   `java -cp ".;../lib/mysql-connector-j-9.7.0.jar" MainApp`


## 6. Project Links
* **GitHub Repository**: https://github.com/sanam-nawaz/Shop-Credit-Management-System
* **YouTube Demo**: 

### 7. Project Structure
```text
Shop-Credit-Management-System/
├── db/
│   └── shop_db file.sql
├── lib/
│   └── mysql-connector-j-9.7.0.jar
├── src/
│   ├── Credit.java
│   ├── CreditDAO.java
│   ├── Customer.java
│   ├── CustomerDAO.java
│   ├── DBConnection.java
│   ├── MainApp.java
│   ├── Person.java
│   ├── Product.java
│   ├── ProductDAO.java
│   ├── Sale.java
│   └── SalesDAO.java
└── README.md
