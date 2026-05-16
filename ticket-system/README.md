# 🎟️ Ticket System

A simple, thread-safe **ticket purchasing system** built in Java, designed to simulate real-world seat booking scenarios with **zone capacity control**, **seat uniqueness**, and **concurrent access handling**.

---

## 📌 Overview

This project demonstrates how to design a **concurrent ticketing system** where:

* Each show has multiple seating zones (VIP, PREMIUM, etc.)
* Each zone has a **limited capacity**
* Each seat can only be sold **once**
* Multiple users can attempt to buy tickets at the same time

The system ensures **data consistency and integrity** even under concurrent operations.

---

## 🧱 Project Structure

```
ticket-system/
├── model/
│   ├── Zone.java
│   ├── Seat.java
│   ├── Show.java
│   └── Ticket.java
├── service/
│   └── TicketService.java
└── test/
    └── TicketServiceTest.java
```

---

## 🧩 Domain Model

### Zone

Defines seating categories:

* VIP
* PREMIUM
* GENERAL
* BALCONY

---

### Seat

Represents a seat with:

* Seat number
* Associated zone

---

### Show

Represents an event:

* Unique ID
* Name
* Date
* Capacity per zone

---

### Ticket

Represents a purchased ticket:

* Auto-generated ID (UUID)
* Associated show
* Assigned seat

---

## ⚙️ Core Service

### `TicketService`

Handles all business logic:

#### Features:

* Create shows
* Purchase tickets
* Validate seat availability
* Enforce zone capacity limits
* Ensure thread safety

---

### 🔒 Concurrency Strategy

The system uses:

* `ConcurrentHashMap` → thread-safe storage
* `CopyOnWriteArrayList` → safe iteration under concurrency
* `synchronized` block → atomic ticket purchase per show

This ensures:

* No overselling
* No duplicated seat bookings

---

## 🧪 Testing

The project includes **deep unit tests** using JUnit 5:

### Covered scenarios:

* ✅ Successful ticket purchase
* ❌ Zone capacity exceeded
* ❌ Duplicate seat booking
* 🔀 Multiple shows handled independently

---

## 🚀 How to Run

### Requirements

* Java 25
* Maven

### Run tests

```bash
mvn clean test
```

---

## ⚖️ Design Trade-offs

### ✅ Pros

* **Thread-safe by design**
  Prevents race conditions during ticket purchases

* **Simple and readable architecture**
  Easy to understand and extend

* **Strong domain modeling**
  Clear separation between `Show`, `Seat`, and `Ticket`

* **Deterministic behavior**
  No inconsistent states (no overselling or duplicate seats)

---

### ❌ Cons

* **Synchronized bottleneck per show**
  Limits scalability under very high concurrency

* **CopyOnWriteArrayList overhead**
  Expensive for write-heavy workloads (like frequent ticket sales)

* **No persistence layer**
  Data is stored in memory only

* **No reservation system**
  Seats are instantly sold, not temporarily held

---

## 🔮 Possible Improvements

* Add **reservation with timeout (e.g., 10 minutes hold)**
* Introduce **database persistence (PostgreSQL, MongoDB)**
* Replace locking with **optimistic concurrency control**
* Add **REST API layer (Spring Boot)**
* Implement **distributed locking (Redis, etc.)** for scaling

---

## 💡 Key Concepts Demonstrated

* Concurrency control in Java
* Thread-safe collections
* Domain-driven design basics
* Defensive programming with validation
* Unit testing with JUnit 5
