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
- Spring Framework 3.0.4:
  - Spring Boot Web
  - Spring Boot Data JPA
  - Spring Boot Validation
  - Spring Boot Test
- AOP AspectJ
- Lombok
- Converter
- LRU & LFU cache implementation

**Build Tool:**
- Gradle 7.5.1

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

| **HTTP METHOD** |            **ENDPOINT**             |        **DECRIPTION**        |
|:---------------:|:-----------------------------------:|:----------------------------:|
|    **POST**     |         `/api/v0/receipts`          |      Create new Receipt      |
|     **GET**     |         `/api/v0/receipts`          |       Get all Receipts       |
|     **GET**     |       `/api/v0/receipts/{id}`       |      Get Receipt by ID       |
|     **PUT**     |       `/api/v0/receipts/{id}`       |     Update Receipt by ID     |
|    **PATCH**    |       `/api/v0/receipts/{id}`       | Partial Update Receipt by ID |
|   **DELETE**    |       `/api/v0/receipts/{id}`       |     Delete Receipt by ID     |
|     **GET**     | `/api/v0/receipts/createFile/{id} ` |  Save Receipt into the File  |

**Products**

| **HTTP METHOD** |      **ENDPOINT**       |        **DECRIPTION**        |
|:---------------:|:-----------------------:|:----------------------------:|
|    **POST**     |   `/api/v0/products`    |      Create new Product      |
|     **GET**     |   `/api/v0/products`    |       Get all Products       |
|     **GET**     | `/api/v0/products/{id}` |      Get Product by ID       |
|     **PUT**     | `/api/v0/products/{id}` |     Update Product by ID     |
|    **PATCH**    | `/api/v0/products/{id}` | Partial Update Product by ID |
|   **DELETE**    | `/api/v0/products/{id}` |     Delete Product by ID     |

**Discount Cards**

| **HTTP METHOD** |         **ENDPOINT**         |           **DECRIPTION**           |
|:---------------:|:----------------------------:|:----------------------------------:|
|    **POST**     |   `/api/v0/discountCards`    |      Create new Discount Card      |
|     **GET**     |   `/api/v0/discountCards`    |       Get all Discount Cards       |
|     **GET**     | `/api/v0/discountCards/{id}` |      Get Discount Card by ID       |
|     **PUT**     | `/api/v0/discountCards/{id}` |     Update Discount Card by ID     |
|    **PATCH**    | `/api/v0/discountCards/{id}` | Partial Update Discount Card by ID |
|   **DELETE**    | `/api/v0/discountCards/{id}` |     Delete Discount Card by ID     |

### **Cache Implementation**

- **GET** - search data in the cache and if there is no data there,  
            then get data from the DB, save it to the cache and return it.
- **POST** - save data in the DB and then save it in the cache.
- **PUT/PATCH** - update/insert data in the DB and then update/insert it in the cache.
- **DELETE** - delete data from the DB and then delete it in the cache.
