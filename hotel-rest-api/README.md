# 🏨 Hotel REST API

A **Spring Boot** project demonstrating how a system can act both as a **REST Server (Host)** and a **REST Client (Messenger)**.

Using a hotel analogy, this project shows how your application can:

* 📥 **Receive requests** from external systems (like Booking.com)
* 📤 **Call external services** (like exchange rate APIs)

---

## 🚀 Concepts Explained

### 🏨 REST Support — *The Host*

Spring Boot turns your application into a server capable of receiving external requests.

* Uses annotations like `@RestController` and `@PostMapping`
* Automatically converts JSON into Java objects
* Exposes endpoints like:

  ```
  POST /api/hotel/bookings
  ```

👉 In this project:
External systems can send booking data, and your API processes it.

---

### 📬 RestTemplate — *The Messenger*

`RestTemplate` allows your application to communicate with other services.

* Performs HTTP requests to external APIs
* Simple and synchronous (blocking)

👉 In this project:
The system calls an external exchange rate service to convert **BRL → USD**.

---

## 🧠 Architecture Overview

```
Client (HTTP Request)
        ↓
BookingController (REST API)
        ↓
CurrencyService (RestTemplate Client)
        ↓
External API (Exchange Rates)
        ↓
Response واپس to Client (JSON)
```

---

## 📦 Project Structure

```
com.pedrohk
├── controller
│   └── BookingController.java
├── model
│   └── Booking.java
├── service
│   └── CurrencyService.java
└── HotelRestApplication.java
```

---

## ⚙️ Technologies

* Java 21
* Spring Boot 3
* Spring Web
* JUnit 5
* TestRestTemplate
* Maven

---

## 🔄 API Example

### ➤ Create Booking

**Request**

```json
POST /api/hotel/bookings

{
  "guestName": "Pedro",
  "priceInBRL": 500.0,
  "priceInUSD": 0.0
}
```

**Response**

```json
{
  "id": "generated-uuid",
  "guestName": "Pedro",
  "priceInBRL": 500.0,
  "priceInUSD": 100.0
}
```

---

## 🧪 Testing

This project uses **`TestRestTemplate`** to simulate real HTTP calls and validate the full flow:

* Controller receives request
* Service calls external API
* Response is returned with converted value

Run tests with:

```bash
mvn clean test
```

---

## ✅ Pros & Cons

| Feature      | Pros                                         | Cons                                              |
| ------------ | -------------------------------------------- | ------------------------------------------------- |
| REST Support | High productivity with minimal configuration | Risk of exposing sensitive data if not secured    |
| RestTemplate | Simple and widely used                       | Blocking (synchronous)                            |
| RestTemplate | Stable and battle-tested                     | In maintenance mode (Spring recommends WebClient) |

---

## 📚 Key Takeaways

* Use **REST Support** when your application needs to **receive requests**
* Use **RestTemplate** when your application needs to **call external services**
* Understand the difference between:

  * 🏨 Server (receives requests)
  * 📬 Client (sends requests)

---

## ▶️ How to Run

```bash
mvn spring-boot:run
```
