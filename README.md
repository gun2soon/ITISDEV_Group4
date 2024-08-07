ITISDEV S13 TERM 3 AY 2023-2024

Specialized Point-of-Sales (POS) and Inventory Management System (IMS) to track coffee sales and inventory for Ready Coffee PH

Professor: Randi Julio Haboc

Business: Coffee Shop
Business Type: Partnership

Members:
AGUSTIN, Lance
CHEN, Shawn
ORENCIA, Sophia
ROSALES, Kaneesha
SALGADO, Allyssa
VALDEZ, Clarissa


This repository contains the code for a Point of Sale (POS) and Inventory Management System, built using Java Swing for the user interface, and JDBC with MySQL for database management.

 Features
- POS: Manage sales transactions and customer purchases.
- Inventory Management: Track inventory levels and update stock. 
- Transaction Summary: Record and display summaries of all transactions such as total sales, total profit, and total cups sold.

Prerequisites
Ensure you have the following installed on your system:
- Java Development Kit (JDK) 8 or higher
- MySQL Server
- JDBC MySQL Connector

 Setup Instructions
 Step 1: Download JAR Files
1. Download the jdatepicker and mysql connector JAR files from the repository. 

 Step 2: Configure the Project
1. Open the Project: Open the Java project in your preferred IDE (we recommend Visual Studio Code)
2. Add JAR Files to Project:
  - In your IDE, navigate to the project's build path or library settings.
  - Add the downloaded JAR files to the project's referenced libraries.
3. Configure Database Connection:
 - Locate and open the `inventory management’ java file and ‘transaction summary’ java file in the project.
  - Update the file with your MySQL database connection details:
db.url=jdbc:mysql://localhost:3306/inventory
db.user=yourusername
db.password=yourpassword

 Step 3: Run the Program
1. Open the Driver Java File:
   - Locate the main driver Java file (`Driver.java`) in the project.
2. Run the Program:
   - Right-click on the driver Java file and select "Run Java" or "Run as Java Application" from the context menu.


