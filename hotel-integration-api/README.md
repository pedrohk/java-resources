# 🏨 Hotel Integration Testing with REST (Spring Boot)

A complete **Integration Testing (End-to-End)** example using **Spring Boot**, simulating a real hotel system where all layers work together, from HTTP requests to database persistence.

---

## 📖 Concept

In the hotel analogy, **Integration Testing with REST** is the **final rehearsal before the grand opening**.

While unit tests verify isolated components (like checking if a room’s faucet works), integration tests ensure the **entire system behaves correctly**:

> When the guest opens the faucet → hot water flows → the heater activates → and the usage is recorded in the system.

This project demonstrates a **realistic end-to-end flow**, where:

* A real HTTP request is sent
* The application processes it through all layers
* Data is persisted in the database
* The response is validated

---

## 🚀 Key Features

### 🔗 End-to-End Testing

* Full Spring Boot context is loaded
* Real HTTP server is started
* No mocks — everything runs as in production

### 🌐 Real HTTP Communication

* Uses `TestRestTemplate` to simulate real clients
* Tests actual REST endpoints

### 🗄️ In-Memory Database

* Uses **H2 Database**
* Fast, isolated, and perfect for testing environments

### 🎯 Random Port Execution

* Runs with `RANDOM_PORT` to avoid conflicts
* Ideal for CI/CD pipelines

---

## 🧱 Tech Stack

* Java 21
* Spring Boot 3
* Spring Web
* Spring Data JPA
* H2 Database
* JUnit 5

---

## 📂 Project Structure

```
src
 ├── controller
 │    └── RoomController.java
 ├── model
 │    └── Room.java
 ├── repository
 │    └── RoomRepository.java
 ├── HotelIntegrationApplication.java
 └── test
      └── RoomIntegrationTest.java
```

---

## 🔄 API Endpoints

| Method | Endpoint                 | Description           |
| ------ | ------------------------ | --------------------- |
| POST   | `/api/rooms`             | Create a room         |
| GET    | `/api/rooms`             | List all rooms        |
| PATCH  | `/api/rooms/{id}/occupy` | Mark room as occupied |

---

## 🧪 Integration Test Overview

The integration test validates the **complete workflow**:

### ✔️ Create and Retrieve Room

* Sends a POST request
* Verifies persistence in database
* Retrieves data via GET endpoint

### ✔️ Update Room Status

* Updates room using PATCH
* Confirms change in response
* Verifies persistence in database

---

## ⚙️ Running the Tests

```bash
mvn clean test
```

---

## 🧠 Testing Concepts Applied

### 🧩 Full Context Loading

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

### 🌐 REST Client for Tests

```java
@Autowired
private TestRestTemplate restTemplate;
```

### 🔄 Clean State Before Tests

```java
@BeforeEach
void setup() {
    repository.deleteAll();
}
```

---

## ⚖️ Pros & Cons

| Feature       | Pros                                         | Cons                                               |
| ------------- | -------------------------------------------- | -------------------------------------------------- |
| ✅ Reliability | Ensures all layers work together             | Slower than unit tests                             |
| 🌍 Realism    | Detects JSON, DB, and config issues          | Can fail environment issues (DB, ports, etc.) |
| 🤖 Automation | Acts as living documentation of API behavior | More complex setup                                 |

---

## 🧾 Summary

Integration testing with REST is what gives you **confidence before deployment**.

If these tests pass, you know that:

* Network communication works
* Data is correctly transformed
* Persistence layer behaves as expected

👉 It’s heavier than unit testing, but **far more reliable**.

---

## 📌 Next Steps

* Add **Spring Data JPA advanced queries**
* Implement **pagination & filtering**
* Introduce **WebTestClient** for reactive testing
* Integrate with **Testcontainers** for real databases
