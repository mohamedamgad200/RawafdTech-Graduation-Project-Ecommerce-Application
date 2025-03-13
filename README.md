# 🛒 eCommerce Application

A full-featured eCommerce application built using **Spring Boot**, **Spring Data JPA**, **Spring Security**, **JavaMail (Gmail SMTP)**, and **Logger** for tracking email errors.

## ✨ Features

- **User Authentication & Authorization** (JWT-based security)
- **Product Management** (CRUD operations)
- **Shopping Cart** (Add, remove, and update items)
- **Order Processing** (Checkout, payment, order tracking)
- **Email Notifications** (JavaMail for order confirmations)
- **Logging & Error Tracking** (Logs email failures and system errors)
- **User Reviews & Comments** (Users can review and comment on products)
  
## 📸 Screenshots
### **Swagger Documentation **
![swagger-images](swagger-images/1.PNG)
![swagger-images](swagger-images/2.PNG)
![swagger-images](swagger-images/3.PNG)
### **Database Schema**
![database-schema](swagger-images/ecommerceschema.png)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/ecommerce.git
   cd ecommerce
   ```

2. **Configure the application**
   - Set up your database (MySQL, PostgreSQL, or H2)
   - Configure `application.properties` with your database and email credentials.

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   ```
   http://localhost:8080/api
   ```

## 📌 API Endpoints

### Authentication
- `POST /auth/register` – Register a new user
- `POST /auth/login` – Authenticate and get JWT token

### Product Management
- `GET /products` – Get all products
- `GET /products/{id}` – Get product by ID
- `POST /products` – Add a new product
- `PUT /products/{id}` – Update product details
- `DELETE /products/{id}` – Delete a product

### Cart Management
- `GET /cart` – View cart
- `POST /cart/add` – Add product to cart
- `DELETE /cart/remove/{id}` – Remove product from cart

### Order Management
- `POST /orders` – Place an order
- `GET /orders/{id}` – Get order details
- `GET /orders/user/{userId}` – Get all orders for a user

### Reviews & Comments
- `POST /reviews` – Add a review
- `GET /reviews/{productId}` – Get reviews for a product

## 🛠 Technologies Used

- **Spring Boot** – Backend framework
- **Spring Security** – Authentication & Authorization
- **Spring Data JPA** – ORM & database management
- **JavaMail** – Email notifications
- **Logger** – Logging and error tracking
  
🚀 Developed by **Mohamed Amgad**
