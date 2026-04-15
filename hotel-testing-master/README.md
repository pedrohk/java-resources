# 🧪 Hotel Testing API

A **Spring Boot** project demonstrating a complete testing ecosystem using:

* ✅ Spring Testing (Core)
* 🌍 Environment & Profiles
* 🚀 WebTestClient (Modern API Testing)

Using a hotel analogy, this project ensures all the **“house rules” are validated before opening the doors to guests**.

---

## 🧠 Concepts Explained

### 🧪 Spring Testing — *The Inspector*

Spring allows you to test isolated parts of your application without starting the entire system.

* Load only required beans
* Use **JUnit 5** and **AssertJ**
* Fast and reliable unit/integration testing

👉 In this project:
We validate business rules and application behavior safely before deployment.

---

### 🌍 Environment & Profiles — *The Hotel Location*

Applications behave differently depending on where they run.

* Managed via `Environment`
* Controlled with `@ActiveProfiles`

👉 In this project:

* `test` profile is activated
* Custom properties are loaded from:

  ```
  application-test.properties
  ```

---

### 🚀 WebTestClient — *The Mystery Guest*

A modern and fluent API testing tool that simulates real HTTP requests.

* Performs end-to-end (E2E) tests
* Verifies HTTP status and JSON responses
* Cleaner and more expressive than `MockMvc`

👉 In this project:
WebTestClient acts as a **real client**, validating your API behavior.

---

## 🧠 Architecture Overview

```id="arch1"
Test Suite (WebTestClient)
        ↓
Spring Boot Context (RANDOM_PORT)
        ↓
BookingController (REST API)
        ↓
Environment (Profiles & Properties)
        ↓
Response Validation (Assertions)
```

---

## 📦 Project Structure

```id="arch2"
com.pedrohk
├── controller
│   └── BookingController.java
├── model
│   └── Booking.java
├── HotelTestingApplication.java
└── test    
    └── HotelIntegrationTest.java
```

---

## ⚙️ Technologies

* Java 21
* Spring Boot 3
* Spring Web
* Spring WebFlux (for WebTestClient)
* JUnit 5
* AssertJ
* Maven

---

## 🔄 API Example

### ➤ Create Booking

**Request**

```json id="req1"
POST /api/v1/bookings

{
  "guestName": "Pedro Henrique",
  "roomType": "Presidential Suite",
  "price": 1500.0
}
```

**Response**

```json id="res1"
{
  "id": "generated-uuid",
  "guestName": "Pedro Henrique",
  "roomType": "Presidential Suite",
  "price": 1500.0
}
```

---

### ➤ Health Check

```bash id="health1"
GET /api/v1/bookings/health
```

**Response**

```
OK
```

---

## 🧪 Testing Strategy

### ✅ API Integration Test

* Uses `WebTestClient`
* Tests full request/response cycle
* Validates JSON structure and business rules

### ✅ Validation Scenarios

* ✔️ Successful booking creation
* ❌ Invalid booking (missing guest name → 400 Bad Request)
* ❤️ Health check endpoint validation

---

## ▶️ Run Tests

```bash id="run1"
mvn clean test
```

---

## ✅ Pros & Cons

| Feature        | Pros                                       | Cons                               |
| -------------- | ------------------------------------------ | ---------------------------------- |
| Spring Testing | High confidence before deployment          | Slow tests if poorly designed      |
| Environment    | Flexible configuration across environments | Risk of using wrong profile        |
| WebTestClient  | Clean, fluent, and powerful API testing    | Requires web context configuration |

---

## 📚 Key Takeaways

* Use **Spring Testing** to validate logic early
* Use **Environment & Profiles** to control behavior per environment
* Use **WebTestClient** for modern, readable API testing
* Combine everything for **robust integration testing**

