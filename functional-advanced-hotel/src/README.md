# 🏨 Functional Advanced Hotel API

A modern Java project demonstrating the use of **Advanced Functional Interfaces** in a real-world **Hotel Management System**.

Built with:
- ☕ **Java 21**
- 🚀 **Spring Boot 3.4.0**
- 🧪 **JUnit 5 + AssertJ**

This project focuses on applying **functional programming principles** using specialized interfaces like `Supplier`, `Consumer`, `UnaryOperator`, and `BinaryOperator` to create clean, reusable, and expressive business logic.

---

## 🧠 Key Concepts Implemented

### 1. 🆔 Supplier (Object Creation)

The `Supplier<T>` interface is used to generate values without input.  
In this project, it is used to generate unique room IDs.

```java
Room room = service.createRoom(UUID::randomUUID, "Suite", 1000.0);
````

✔ Generates a new `UUID` every time a room is created.

---

### 2. 🔄 UnaryOperator (Transformation)

The `UnaryOperator<T>` is a specialization of `Function<T, T>` where input and output are the same type.

Used here to apply price adjustments:

```java
List<Room> updatedRooms = service.applyPriceAdjustment(
    rooms,
    price -> price * 1.10 // +10% tax
);
```

✔ Transforms room prices without changing the structure of the object.

---

### 3. ➕ BinaryOperator (Aggregation)

The `BinaryOperator<T>` is used to combine two values of the same type.

Used here to calculate total revenue:

```java
double totalRevenue = service.calculateTotalRevenue(
    rooms,
    Double::sum
);
```

✔ Reduces a stream of values into a single result.

---

### 4. 📋 Consumer (Side Effects)

The `Consumer<T>` interface performs actions without returning values.

Used for auditing/logging:

```java
service.auditRooms(rooms, room ->
    System.out.println("Auditing room: " + room.type())
);
```

✔ Executes side effects such as logging or monitoring.

---

## 💡 Full Example

```java
List<Room> rooms = List.of(
    service.createRoom(UUID::randomUUID, "Suite", 1000.0),
    service.createRoom(UUID::randomUUID, "Standard", 500.0)
);

// Apply 10% price increase
List<Room> adjusted = service.applyPriceAdjustment(rooms, p -> p * 1.10);

// Calculate total revenue
double total = service.calculateTotalRevenue(adjusted, Double::sum);

// Audit rooms
service.auditRooms(adjusted, r -> System.out.println("Auditing: " + r.type()));
```

---

## 🧱 Project Structure

```
functional-advanced-hotel
├── model
│   └── Room.java
├── service
│   └── HotelOperatorService.java
├── FunctionalAdvancedApplication.java
└── test
    └── HotelOperatorTest.java
```

---

## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/pedrohk/functional-advanced-hotel.git
cd functional-advanced-hotel
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Tests

```bash
mvn test
```

---

## 🧪 Testing

The test suite validates all functional interfaces:

* ✅ Supplier → Room creation with dynamic IDs
* ✅ UnaryOperator → Price transformation
* ✅ BinaryOperator → Revenue aggregation
* ✅ Consumer → Audit logging

---

## ⚖️ Pros and Cons

| ✅ Pros                                   | ❌ Cons                                        |
| ---------------------------------------- | --------------------------------------------- |
| Clear intent with specialized interfaces | Can feel over-engineered for simple logic     |
| Highly reusable functional blocks        | Requires knowledge of functional programming  |
| Seamless integration with Streams API    | Lambdas don’t handle checked exceptions well  |
| Cleaner and more declarative code        | Slight performance overhead in small datasets |

---

## 🚀 Future Improvements

* Implement a **Discount Pipeline System** using chained operators
* Combine multiple `UnaryOperator` rules dynamically
* Add REST endpoints for real API interaction
* Integrate persistence (JPA / NoSQL)
* Introduce `Optional` for safer null handling
