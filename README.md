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
