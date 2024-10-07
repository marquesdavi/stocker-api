# Stocker API - Inventory Management System

**Stocker** is a comprehensive inventory management system developed during the "Database with OOP" and "Web Services with Rest" courses of the Bachelor's degree in Information Systems at Unifacisa.

### Tools:
- Java 21
- Spring Boot
- Spring Data MongoDB
- Spring Data Redis (For Cache)
- Spring Security 6.3
- Jakarta Validation
- Lombok
- Documentation with Swagger (springdoc-openapi-starter-webmvc-ui)
- Docker

### Authentication Module
- `POST /api/auth/login`: Authenticates the user and returns a JWT token.

---

### Customer Management Module
- `POST /api/customers/`: Creates a new customer.
- `GET /api/customers/`: Lists all customers.
- `GET /api/customers/{id}`: Retrieves a customer by ID.
- `PATCH /api/customers/{id}`: Partially updates an existing customer.
- `DELETE /api/customers/{id}`: Deletes a customer.

---

### Movement Management Module
- `POST /api/movements/`: Creates a new movement.
- `GET /api/movements/`: Lists all movements.
- `GET /api/movements/{id}`: Retrieves a movement by ID.
- `PATCH /api/movements/{id}`: Updates a movement by ID.
- `DELETE /api/movements/{id}`: Deletes a movement.

---

### Product Management Module
- `POST /api/products/`: Creates a new product.
- `GET /api/products/`: Lists all products.
- `GET /api/products/{id}`: Retrieves a product by ID.
- `PATCH /api/products/{id}`: Partially updates an existing product.
- `DELETE /api/products/{id}`: Deletes a product.

---

### User Management Module
- `POST /api/user/`: Creates a new user.
- `PATCH /api/user/upload/{id}`: Updates a user's profile image by ID.
- `GET /api/user/`: Lists all users.
- `GET /api/user/{id}`: Retrieves a user by ID.
- `PATCH /api/user/{id}`: Updates a user by ID.
- `DELETE /api/user/{id}`: Deletes a user.
- `GET /api/user/current`: Returns the details of the currently logged-in user.