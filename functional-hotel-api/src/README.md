# 🏨 Functional Hotel API

A demonstration of **Java Functional Interfaces** (`Predicate`, `Consumer`, `Function`, `Supplier`) applied to a Hotel Booking system using **Java 21+** and **Spring Boot 3.4.0**.

---

## 🚀 Overview

This project showcases how to use Java's functional programming features to process hotel bookings in a **clean, flexible, and dynamic way**.

Instead of rigid logic, behavior is passed as parameters using functional interfaces.

---

## 🧱 The 4 Pillars of Functional Interfaces

### 1. 🟢 Supplier (Creation)

Used to dynamically provide values.

```java
Booking booking = service.createBooking(UUID::randomUUID, "Pedro", 500.0);
```

---

### 2. 🔵 Predicate (Filtering)

Used to define conditions.

```java
b -> b.price() > 1000
```

---

### 3. 🟣 Function (Transformation)

Used to map objects into different representations.

```java
b -> b.guestName().toUpperCase()
```

---

### 4. 🟠 Consumer (Action)

Used to perform actions (side effects).

```java
b -> count.getAndIncrement()
```

---

## 📦 Project Structure

```
com.pedrohk
├── model
│   └── Booking.java
├── service
│   └── BookingService.java
├── FunctionalHotelApplication.java
└── FunctionalBookingTest.java
```

---

## 🧠 Core Example

### Booking Model

```java
public record Booking(UUID id, String guestName, double price, boolean isPaid) {}
```


## 🧪 Tests

This project includes integration tests demonstrating all functional interfaces:

```bash
mvn clean test
```

---

## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/pedrohk/functional-hotel-api.git
cd functional-hotel-api
```

### 2. Build and run tests

```bash
mvn clean test
```

---

## ⚖️ Pros and Cons

| Pros                                                    | Cons                                                    |
| ------------------------------------------------------- | ------------------------------------------------------- |
| ✅ Conciseness: Reduces boilerplate code                 | ⚠️ Readability: Complex lambdas can be harder to debug  |
| ⚡ Flexibility: Behavior can be injected dynamically     | ⚠️ Debugging: Stack traces may be less intuitive        |
| 🔄 Reusability: Functions can be reused across contexts | ⚠️ Overuse: Can make simple logic unnecessarily complex |
| 🚀 Parallelism: Easy to use with Streams API            | ⚠️ State: Works best with immutable data                |

---

## ☕ Compatibility

This project uses:

* `record` (Java 16+)
* Functional Interfaces (Java 8+)

✅ Fully compatible with **Java 21** and **Java 25**

---
