# Employee Leave Management System

This is a Spring Boot-based RESTful web application that allows management of employee leave requests. The system supports CRUD operations on employees and provides functionality to submit, approve, reject, and view leave requests.

## Features

- Add, update, delete, and get employee details.
- Apply for leave and view leave status.
- Approve or reject leave requests.
- Fetch leave records by employee ID or leave ID.
- Search employees by department or position.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Postman (API Testing)
- IntelliJ IDEA

## Project Structure

employee-leave-management-system/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com.employee.leave/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── exception/
│ │ │ ├── model/
│ │ │ ├── repository/
│ │ │ └── service/
│ │ └── resources/
│ │ ├── application.properties
│
├── pom.xml

markdown
Copy
Edit

## API Endpoints

### Employee Endpoints

- `POST /api/employees` – Create a new employee
- `GET /api/employees` – Get all employees
- `GET /api/employees/{id}` – Get employee by ID
- `PUT /api/employees/{id}` – Update employee by ID
- `DELETE /api/employees/{id}` – Delete employee by ID
- `GET /api/employees/department/{department}` – Get employees by department
- `GET /api/employees/position/{position}` – Get employees by position

### Leave Endpoints

- `POST /api/leaves/{employeeId}` – Apply for leave
- `GET /api/leaves` – Get all leave requests
- `GET /api/leaves/{id}` – Get leave by ID
- `GET /api/leaves/employee/{employeeId}` – Get leave by employee ID
- `PUT /api/leaves/approve/{id}` – Approve leave
- `PUT /api/leaves/reject/{id}` – Reject leave

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/sukumar2002/employee-leave-management-system.git
Open the project in IntelliJ IDEA or your preferred IDE.

Configure your MySQL database and update the credentials in application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=sukumar2002
Run the application:

bash
Copy
Edit
mvn spring-boot:run
Screenshots

Author
Sukumar Savarapu
