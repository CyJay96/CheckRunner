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
- Spring Framework 3.0.3:
  - Spring Boot Web
  - Spring Boot Data JPA
  - Spring Boot Validation
  - Spring Boot Test
- AOP AspectJ
- Lombok
- MapStruct
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

### **Cache Implementation**

- **GET** - search data in the cache and if there is no data there,  
            then get data from the DB, save it to the cache and return it.
- **POST** - save data in the DB and then save it in the cache.
- **PUT** - update/insert data in the DB and then update/insert it in the cache.
- **DELETE** - delete data from the DB and then delete it in the cache.
