# 🏨 Hotel GoF System – Design Patterns in Practice

A robust implementation of **GoF Design Patterns** applied to a **Hotel Management System**, built with **Java 21** and **Spring Boot 3.4**.

This project demonstrates how classic design patterns can be used to build a **scalable, maintainable, and extensible architecture** in real-world scenarios.

---

## 🎯 Purpose

In complex systems like hotel management, we deal with:

* Booking creation flows
* Dynamic pricing strategies
* Room feature composition
* State-dependent behaviors
* Centralized system coordination

This project shows how to solve these problems using **well-structured design patterns**.

---

## 🧱 Project Architecture

The project is organized into three main categories of GoF patterns:

```text
com.pedrohk.patterns
 ├── creational
 ├── structural
 └── behavioral
```

---

## 🏗️ Creational Patterns

### 🔹 Singleton – `HotelInventory`

Ensures a **single shared instance** across the system.

```java
HotelInventory inventory1 = HotelInventory.getInstance();
HotelInventory inventory2 = HotelInventory.getInstance();
```

✔ Guarantees consistency
✔ Thread-safe access

---

### 🔹 Builder – `Booking`

Constructs complex booking objects step-by-step.

```java
Booking booking = new Booking.Builder()
        .guestName("Pedro")
        .withWifi()
        .withBreakfast()
        .build();
```

✔ Readable object creation
✔ Flexible configuration

---

## 🧩 Structural Patterns

### 🔹 Decorator – Room Customization

Dynamically adds features to a room.

```java
RoomService room = new MiniBarDecorator(new BaseRoom());
double cost = room.getCost(); // 130.0
```

✔ Extensible without modifying base classes
✔ Runtime feature composition

---

### 🔹 Facade – `FrontDeskFacade`

Simplifies complex operations behind a single interface.

```java
FrontDeskFacade facade = new FrontDeskFacade();
facade.checkIn("Pedro");
```

✔ Reduces system complexity
✔ Provides a clean entry point

---

## 🔄 Behavioral Patterns

### 🔹 Strategy – Pricing Engine

Encapsulates pricing algorithms.

```java
PricingStrategy strategy = new HighSeasonStrategy();
double finalPrice = strategy.applyPrice(100.0); // 180.0
```

✔ Easily switch pricing rules
✔ Open for extension, closed for modification

---

### 🔹 State – Room Status

Represents behavior based on state.

```java
RoomStatus status = new OccupiedStatus();
status.showStatus();
```

✔ Cleaner state transitions
✔ Avoids complex conditionals

---

## 🧪 Testing

All patterns are validated using **JUnit 5 + AssertJ**.

```bash
mvn clean test
```

### ✔ What is tested:

* Singleton instance uniqueness
* Builder object creation
* Decorator cost composition
* Strategy pricing logic
* State behavior execution

---

## 📦 Tech Stack

* **Java 21**
* **Spring Boot 3.4**
* **JUnit 5**
* **AssertJ**
* **Maven**

---

## 💡 Key Takeaways

* ✅ Design patterns improve **code organization and readability**
* ✅ Promote **low coupling and high cohesion**
* ✅ Enable **easy extension without breaking existing code**
* ✅ Reflect real-world system design challenges

---

## 📈 Real-World Mapping

| Hotel Scenario                | Pattern Used |
| ----------------------------- | ------------ |
| Shared inventory system       | Singleton    |
| Booking creation              | Builder      |
| Room add-ons (Wi-Fi, minibar) | Decorator    |
| Front desk operations         | Facade       |
| Seasonal pricing              | Strategy     |
| Room occupancy status         | State        |

---

## 🚀 How to Run

```bash
mvn clean test
```

