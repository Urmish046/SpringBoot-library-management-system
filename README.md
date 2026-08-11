# Spring Boot Library Management System

A robust and fully functional RESTful backend API for managing library operations, built from scratch using Java 21, Spring Boot, and Spring Data JPA. 

This project goes beyond simple CRUD operations by implementing complex business rules, DTO-pattern for clean API responses, and strict relational database mapping.

## Learning Journey & Concepts Mastered

As a foundational backend project, I used this as a hands-on opportunity to dive deep into Spring Boot and modern web development architecture. Throughout this project, I successfully learned and implemented the following core concepts:

*   **3-Tier Architecture:** Structured the application cleanly into Controllers (API routing), Services (Business Logic), and Repositories (Database interactions).
*   **Object-Relational Mapping (ORM):** Mastered database relationships like @ManyToOne and @OneToMany using Hibernate and Spring Data JPA.
*   **The DTO Pattern:** Learned how to resolve circular dependencies and prevent infinite recursion by mapping internal Entities to Data Transfer Objects (DTOs) before sending JSON responses.
*   **Custom Business Logic:** Wrote complex logic from scratch, such as tracking maximum borrowing limits (max 3 books per member) using Java Collections and custom JPA derived queries.
*   **Date & Time Manipulation:** Handled real-world scenarios like calculating overdue books using Java's LocalDate API.
*   **Version Control & Security:** Managed the project using Git/GitHub, specifically learning how to handle Git histories, amend commits, and resolve GitHub Push Protections (Secret Scanning) for database credentials.

---

## Tech Stack
*   **Backend Framework:** Spring Boot 3.x
*   **Language:** Java 21
*   **Database:** MySQL (Hosted on Aiven Cloud)
*   **ORM:** Spring Data JPA / Hibernate
*   **Boilerplate Reduction:** Lombok
*   **Build Tool:** Maven

---

## Core Business Logic Features

One of the main highlights of this project is the custom business logic implemented in the Service layer to ensure real-world library constraints:

1.  **Borrowing Limit Check:** A strict rule ensuring that one member can only borrow a maximum of 3 books at a time. If a 4th book is attempted, the system throws an exception.
2.  **Duplicate Borrowing Prevention:** The system checks if a specific book is already currently borrowed by someone else (i.e., returnedDate is null) before allowing a new borrow record.
3.  **Smart Overdue Tracking (Stretch Goal):** Automated date calculation checking if the current date is 14 days past the borrowedDate. If the book hasn't been returned, it is dynamically flagged as "overdue: true" in the JSON response.

---

## Project Modules Breakdown

The architecture is divided into logical modules for clean code and scalability:

*   **Module 1: Entities & Relations** 
    *   Designed models (Book, Member, Category, BorrowRecord).
    *   Established database relationships.
*   **Module 2: Data Access (Repositories)**
    *   Created JPA Repositories for all entities.
    *   Wrote custom derived query methods like findByMemberIdAndReturnedDateIsNull(id).
*   **Module 3: Business Logic (Services)**
    *   Centralized all core logic within Service classes.
*   **Module 4: DTOs & Mapping**
    *   Implemented Request and Response DTOs (e.g., BorrowResponseDto).
*   **Module 5: REST Controllers**
    *   Exposed clean and semantic API endpoints following REST best practices.

---

## Key API Endpoints

### Books & Categories
*   GET /books - Retrieve all books
*   POST /books - Add a new book
*   GET /categories - Retrieve all categories

### Members
*   GET /members - Retrieve all registered library members
*   POST /members - Register a new member

### Borrowing Operations
*   POST /borrowRecord/borrow/member/{id}/book/{bookId} 
    *   Issues a book to a member after running all business checks (Limit <= 3, Availability, etc).
*   PUT /borrowRecord/return/{recordId}
    *   Updates the returnedDate and marks the book as available again.

---

## How to Run Locally

1. Clone the repository:
   ```bash
   git clone [https://github.com/Urmish046/SpringBoot-library-management-system.git](https://github.com/Urmish046/SpringBoot-library-management-system.git)
