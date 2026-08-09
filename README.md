# Supermarket ERP - Assignment 02

Java 25 + Spring Boot 4.0.7 + H2 in-memory default configuration.

## Setup
1. Run the application with the default in-memory database:
   `mvn clean spring-boot:run`
2. Open `http://localhost:8082`
3. If you want to use a MySQL instance instead, update `src/main/resources/application.properties` to the MySQL JDBC settings and run the schema setup from `database_setup.sql`.

Features: Product CRUD, barcode lookup, Supplier CRUD, validation, Product-Supplier integration, local in-memory persistence by default.
