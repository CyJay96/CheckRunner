## **CheckRunner API**

### **Description**

This is an Application that implements the functionality of creating a receipt in the shop.

### **The stack of technologies used**
**API Technologies:**
- SOLID
- OOP
- DI
- REST

**Backend technologies:**
- Java 17
- Spring Framework 3.0.0:
  - Spring Boot Web
  - Spring Boot Data JPA
  - Spring Boot Validation
  - Spring Boot Test
- Lombok
- MapStruct

**Build Tool:**
- Gradle 7.5

**DataBase:**
- PostgreSQL
- H2
- LiquiBase

**Testing:**
- Junit 5
- Mockito

**Containerization:**
- Docker

### **Get Started**
Run CheckRunner Application

Download the latest version of CheckRunner Application from Docker Hub:

    $ docker pull cyjay96/check-runner-api

Run CheckRunner Application using Docker Compose:

    $ docker-compose up

### **Endpoints**

**Receipts**

| **HTTP METHOD** |            **ENDPOINT**            |       **DECRIPTION**       |
|:---------------:|:----------------------------------:|:--------------------------:|
|    **POST**     |         `/api/v0/receipt`          |     Create new Receipt     |
|     **GET**     |         `/api/v0/receipt`          |      Get all Receipts      |
|     **GET**     |       `/api/v0/receipt/{id}`       |     Get Receipt by ID      |
|     **PUT**     |       `/api/v0/receipt/{id}`       |    Update Receipt by ID    |
|   **DELETE**    |       `/api/v0/receipt/{id}`       |    Delete Receipt by ID    |
|     **GET**     | `/api/v0/receipt/createfile/{id} ` | Save Receipt into the File |

**Products**

| **HTTP METHOD** |            **ENDPOINT**            |    **DECRIPTION**    |
|:---------------:|:----------------------------------:|:--------------------:|
|    **POST**     |         `/api/v0/product`          |  Create new Product  |
|     **GET**     |         `/api/v0/product`          |   Get all Products   |
|     **GET**     |       `/api/v0/product/{id}`       |  Get Product by ID   |
|     **PUT**     |       `/api/v0/product/{id}`       | Update Product by ID |
|   **DELETE**    |       `/api/v0/product/{id}`       | Delete Product by ID |

**Discount Cards**

| **HTTP METHOD** |        **ENDPOINT**         |       **DECRIPTION**        |
|:---------------:|:---------------------------:|:---------------------------:|
|    **POST**     |   `/api/v0/discountcard`    |  Create new Discount Card   |
|     **GET**     |   `/api/v0/discountcard`    |   Get all Discount Cards    |
|     **GET**     | `/api/v0/discountcard/{id}` |   Get Discount Card by ID   |
|     **PUT**     | `/api/v0/discountcard/{id}` | Update Discount Card by ID  |
|   **DELETE**    | `/api/v0/discountcard/{id}` | Delete Discount Card by ID  |
