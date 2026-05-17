# Mini ERP Management System

## 📌 Giới thiệu
Mini ERP Management System là hệ thống quản lý doanh nghiệp thu nhỏ được xây dựng bằng Spring Boot.

Hệ thống hỗ trợ:
- Quản lý sản phẩm
- Quản lý kho
- Quản lý đơn hàng
- Quản lý khách hàng
- Quản lý nhân viên
- Báo cáo doanh thu

---

## 🛠 Công nghệ sử dụng
- Java 21
- Spring Boot 4
- Spring Data JPA
- MySQL
- Maven
- Lombok
- Postman
- Git & GitHub

---

## 🏗 Kiến trúc hệ thống

Client → Controller → Service → Repository → Database  
Database → Repository → Service → Controller → Client (JSON)

---

## 📦 Chức năng chính

### Product Management
- CRUD sản phẩm
- SKU unique
- Search sản phẩm
- Quản lý giá nhập/xuất

### Inventory Management
- Nhập kho
- Xuất kho
- Lưu lịch sử kho
- Transaction rollback

### Customer Management
- CRUD khách hàng
- Loyalty Points
- Rank (NORMAL, SILVER, GOLD)

### Order Management
- Tạo đơn hàng
- Chi tiết đơn hàng
- Tự động trừ kho
- Tính tổng tiền

### Employee Management
- CRUD nhân viên
- Role Admin/Staff

### Reporting
- Thống kê doanh thu
- Dashboard

---

## 🗄 Database Tables
- products
- inventory_logs
- customers
- orders
- order_items
- employees

---

## 🚀 API Example

### Create Product
POST /api/products

### Import Stock
POST /api/inventory/import

### Create Order
POST /api/orders

