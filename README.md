## Supermarket ERP System

A Spring Boot based Supermarket Management System developed for Enterprise Application Development.

### Features

- Product Management
  - Add products
  - Update products
  - Delete products
  - Search products
  - Barcode support

- Supplier Management
  - Add suppliers
  - Update suppliers
  - Delete suppliers
  - Search suppliers

- Purchase Order Management
  - Create purchase orders
  - View purchase orders
  - Edit purchase orders
  - Delete purchase orders
  - Automatic total amount calculation
  - Purchase order status management

- Goods Received Note (GRN) Management
  - Create GRNs
  - View GRNs
  - Edit GRNs
  - Delete GRNs
  - Automatic total amount calculation

### MVC Architecture

The Purchase Order and Goods Received Note modules are implemented using the MVC architecture.

- **Model** - PurchaseOrder and GRN entities
- **View** - Thymeleaf HTML templates
- **Controller** - Handles HTTP requests and navigation
- **Service** - Contains business logic
- **Repository** - Handles database operations using Spring Data JPA

### Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- HTML
- CSS
- JavaScript
- Maven

### Main Modules

1. Product Management
2. Supplier Management
3. Purchase Order Management
4. Goods Received Note (GRN) Management

### Application Navigation

The main dashboard provides access to:

- Products
- Suppliers
- Purchase Orders
- GRN

### Running the Application

Run:

```bash
mvn spring-boot:run
