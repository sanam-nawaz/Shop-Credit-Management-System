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
1.  **Database**: Execute the script in `/db/shop_db.sql` using MySQL Workbench.
2. **Connection**: Ensure your MySQL password in `DBConnection.java` matches your local setup.
3. **Dependencies**: Include the `mysql-connector-j` JAR file in your project classpath.
4. **Compile & Run**:
* `javac *.java`
* `java MainApp`


## 6. Project Links
* **GitHub Repository**: https://github.com/sanam-nawaz/Shop-Credit-Management-System
* **YouTube Demo**: 

Shop-Credit-Management-System/
├── db/
│   └── shop_db file.sql       # Database schema and initial data
├── lib/
│   └── mysql-connector-j-9.7.0.jar  # JDBC Driver for MySQL connection
├── src/
│   ├── Credit.java            # Model class for Credit records
│   ├── CreditDAO.java         # Data Access Object for Credit table
│   ├── Customer.java          # Model class (Extends Person)
│   ├── CustomerDAO.java       # Data Access Object for Customer table
│   ├── DBConnection.java      # Singleton class for Database connectivity
│   ├── MainApp.java           # Main Entry Point & GUI (Java Swing)
│   ├── Person.java            # Abstract Base Class (OOP Abstraction)
│   ├── Product.java           # Model class for Inventory items
│   ├── ProductDAO.java        # Data Access Object for Product table
│   ├── Sale.java              # Model class for Sales transactions
│   └── SalesDAO.java          # Data Access Object for Sales table
└── README.md                  # Project documentation and instructions
