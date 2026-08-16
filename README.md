# Supermarket ERP System

A Spring Boot based **Supermarket Enterprise Resource Planning (ERP) System** developed for Enterprise Application Development.

The system manages the complete supermarket purchasing and sales workflow, including products, suppliers, purchase orders, goods receiving, inventory, sales receipts, and payments using a relational MySQL/MariaDB database.

---

## Features

### Product Management

- Add products
- Update products
- Delete products
- Search products
- Barcode support
- Product category and brand management
- Purchase and selling price management
- Product status management

### Supplier Management

- Add suppliers
- Update suppliers
- Delete suppliers
- Search suppliers
- Contact person management
- Supplier contact information
- Bank details
- Supplier status management

### Purchase Order Management

- Create purchase orders
- View purchase orders
- Edit purchase orders
- Delete purchase orders
- Select suppliers and products
- Manage purchase order items
- Automatic subtotal calculation
- Automatic total amount calculation
- Purchase order status management

### Goods Received Note (GRN) Management

- Create GRNs
- View GRNs
- Edit GRNs
- Delete GRNs
- Link GRNs with purchase orders
- Record ordered and received quantities
- Automatic subtotal calculation
- Update inventory based on received goods
- GRN status management

### Inventory Management

- Track product stock
- Track inventory by location
- Display quantity on hand
- Automatically increase stock after receiving goods
- Automatically decrease stock after sales
- Display inventory status
- Track last inventory update time

### Sales Management

- Create sales receipts
- Select cashier
- Select products
- Record quantities
- Automatic selling price retrieval
- Automatic subtotal calculation
- Automatic total calculation
- Discount support
- Cash and card payment methods
- Automatic inventory stock reduction
- Prevent sales when stock is insufficient
- Sales receipt status management

### Payment Management

- Create payments for sales receipts
- Link payments with sales receipts
- Automatic payment amount retrieval
- Automatic payment method retrieval
- Payment reference number support
- Payment status management
- View payment history

### User and Role Management

- User entity management
- Role-based database structure
- User and role relationships
- Cashier association with sales receipts
- User association with purchase orders and GRNs

---

## Complete ERP Workflow

The system supports the following integrated workflow:

```text
Supplier
   ↓
Product
   ↓
Purchase Order
   ↓
Goods Received Note (GRN)
   ↓
Inventory Increase
   ↓
Sales Receipt
   ↓
Inventory Decrease
   ↓
Payment
```

This allows purchasing, stock management, sales, and payment information to remain connected through the database.

---

## MVC Architecture

The application follows the **Model-View-Controller (MVC)** architecture with an additional Service and Repository layer.

### Model

JPA entities represent the database structure and relationships.

Main entities include:

- Product
- Supplier
- PurchaseOrder
- PurchaseOrderItem
- GRN
- GRNItem
- Inventory
- Location
- SalesReceipt
- SalesReceiptItem
- Payment
- User
- Role

### View

The user interface is implemented using:

- Thymeleaf
- HTML
- CSS
- JavaScript

### Controller

Spring MVC controllers handle:

- HTTP requests
- Form submissions
- Page navigation
- Communication between the UI and service layer

### Service

The service layer contains business logic including:

- Purchase order total calculation
- GRN processing
- Inventory updates
- Sales processing
- Stock validation
- Sales total calculation
- Payment processing

### Repository

Spring Data JPA repositories provide database access and CRUD operations.

---

## Database

The application uses a **MySQL/MariaDB relational database**.

Database name:

```text
supermarket_erp
```

### Main Tables

```text
products
suppliers
purchase_orders
purchase_order_items
goods_receipts
goods_receipt_items
inventory
locations
sales_receipts
sales_receipt_items
payments
users
roles
```

### Main Relationships

```text
Supplier
   └── Purchase Orders

Purchase Order
   ├── Purchase Order Items
   └── Goods Receipts

Product
   ├── Purchase Order Items
   ├── Goods Receipt Items
   ├── Inventory
   └── Sales Receipt Items

Goods Receipt
   └── Goods Receipt Items

Location
   └── Inventory

Sales Receipt
   ├── Sales Receipt Items
   └── Payments

Role
   └── Users

User
   ├── Purchase Orders
   ├── Goods Receipts
   └── Sales Receipts
```

Foreign key relationships are used to maintain referential integrity between the tables.

---

## Technologies Used

- Java 21
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- Hibernate
- Thymeleaf
- MySQL / MariaDB
- HTML5
- CSS3
- JavaScript
- Lombok
- Maven
- XAMPP / phpMyAdmin

---

## Main Modules

1. Product Management
2. Supplier Management
3. Purchase Order Management
4. Goods Received Note (GRN) Management
5. Inventory Management
6. Sales Management
7. Payment Management
8. User and Role Management

---

## Application Navigation

The main dashboard provides access to:

- Products
- Suppliers
- Purchase Orders
- GRN
- Inventory
- Sales
- Payments

---

## Database Configuration

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=supermarket-erp

spring.datasource.url=jdbc:mysql://localhost:3306/supermarket_erp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8082

spring.thymeleaf.cache=false
```

Create the database before running the application:

```sql
CREATE DATABASE supermarket_erp;
```

Hibernate automatically creates or updates the required tables according to the JPA entities.

---

## Running the Application

### 1. Start MySQL/MariaDB

Start the MySQL/MariaDB server using XAMPP or another database server.

### 2. Build the Project

```bash
mvn clean package
```

The project should complete with:

```text
BUILD SUCCESS
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Alternatively, run the generated JAR:

```bash
java -jar target/supermarket-erp-0.0.1-SNAPSHOT.jar
```

### 4. Open the Application

Open:

```text
http://localhost:8082/
```

---

## Module URLs

| Module | URL |
|---|---|
| Home Dashboard | `http://localhost:8082/` |
| Purchase Orders | `http://localhost:8082/purchase-orders` |
| Goods Received Notes | `http://localhost:8082/grn` |
| Inventory | `http://localhost:8082/inventory` |
| Sales | `http://localhost:8082/sales` |
| Payments | `http://localhost:8082/payments` |

Products and Suppliers are available directly from the main dashboard.

---

## Inventory Automation

Inventory quantities are automatically updated according to business transactions.

### Goods Received

When products are received through a GRN:

```text
Current Stock + Received Quantity = Updated Stock
```

### Product Sale

When products are sold:

```text
Current Stock - Sold Quantity = Updated Stock
```

The system also checks available inventory before completing a sale to prevent insufficient-stock transactions.

---

## Build and Testing

The project can be tested and packaged using:

```bash
mvn clean package
```

Current build status:

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

## Project Structure

```text
src/
├── main/
│   ├── java/com/bci/productcrud/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   │
│   └── resources/
│       ├── static/
│       │   ├── index.html
│       │   ├── style.css
│       │   └── app.js
│       │
│       ├── templates/
│       │   ├── purchase-order/
│       │   ├── grn/
│       │   ├── inventory/
│       │   ├── sales/
│       │   └── payments/
│       │
│       └── application.properties
│
└── test/
```

---

## Project Status

**Completed**

The core ERP workflow has been implemented and tested successfully:

```text
Supplier
→ Product
→ Purchase Order
→ GRN
→ Inventory
→ Sales
→ Payment
```

The application successfully connects to the MySQL/MariaDB database, maintains relational data using JPA, automatically updates inventory quantities, processes sales and payments, and passes the Spring Boot application context test.

---

## Academic Project

Developed for **Enterprise Application Development** using Spring Boot MVC, Spring Data JPA, Thymeleaf, and MySQL/MariaDB.
