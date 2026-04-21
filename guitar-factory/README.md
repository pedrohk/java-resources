# 🎸 Guitar Factory

A simple and elegant Java-based simulation of a guitar manufacturing system, focused on inventory control, domain modeling, and factory logic.

This project demonstrates clean design principles using modern Java (records, enums), concurrent data structures, and unit testing with JUnit.

---

## 📦 Project Overview

The **Guitar Factory** simulates a real-world production pipeline:

1. Manage raw materials (wood, pickups, etc.)
2. Validate inventory before production
3. Consume components during manufacturing
4. Generate a finished guitar with a unique serial number

---

## 🧱 Architecture

```
           +----------------------+
           |   InventoryManager   |
           |----------------------|
           | + addStock()         |
           | + consume()          |
           | + getStockLevel()    |
           +----------+-----------+
                      |
                      v
           +----------------------+
           |    GuitarFactory     |
           |----------------------|
           | + buildGuitar()      |
           | - validateInventory()|
           | - consumeComponents()|
           +----------+-----------+
                      |
                      v
           +----------------------+
           |       Guitar         |
           |----------------------|
           | serialNumber         |
           | model                |
           | specs                |
           +----------------------+

           +----------------------+
           |        Specs         |
           |----------------------|
           | wood                 |
           | pickups              |
           | color                |
           | strings              |
           +----------------------+
```

---

## 🧠 Concepts Applied

* **Domain-Driven Design (DDD - Lite)**
  Clear separation between domain models and business logic.

* **Factory Pattern (Simplified)**
  Centralized object creation with validation and side effects.

* **Immutable Data Structures**
  Using Java `record` for safer and cleaner models.

* **Concurrency Safety**
  `ConcurrentHashMap` ensures thread-safe inventory operations.

* **Fail-Fast Validation**
  System immediately stops invalid builds (missing components).

---

## ⚙️ Tech Stack

* Java 25
* Maven
* JUnit 5
* Concurrent Collections (Java Standard Library)

---

## 🚀 How It Works

### 1. Add Inventory

```java
inventory.addStock("Mahogany", 5);
inventory.addStock("Humbucker", 5);
```

### 2. Build a Guitar

```java
Specs specs = new Specs("Mahogany", "Humbucker", "Gold Top", 6);
Guitar guitar = factory.buildGuitar(GuitarModel.LES_PAUL, specs);
```

### 3. What Happens Internally

* Inventory is validated
* Components are consumed
* A guitar is created with a UUID serial number

---

## 🧪 Testing

The project includes unit and integration-style tests covering:

* Successful guitar builds
* Failure scenarios (missing components)
* Inventory decrement behavior
* Stock exhaustion scenarios

Run tests with:

```bash
mvn test
```

---

## ⚖️ Design Trade-offs (Pros & Cons)

### ✅ Pros

**1. Simplicity & Clarity**
The design is minimal and easy to understand. Great for learning and extensibility.

**2. Thread-Safe Inventory**
Using `ConcurrentHashMap` allows safe concurrent access without external synchronization.

**3. Immutable Domain Models**
`record` ensures data integrity and reduces boilerplate.

**4. Fail-Fast Strategy**
Errors are detected early, preventing inconsistent states.

**5. Decoupled Components**
Inventory and factory logic are cleanly separated.

---

### ❌ Cons

**1. No Transaction Management**
Inventory validation and consumption are not atomic.
→ In concurrent scenarios, stock could change between validation and consumption.

**2. Limited Inventory Model**
Inventory is string-based (`"Mahogany"`, `"Humbucker"`), which:

* Lacks type safety
* Is prone to typos
* Makes refactoring harder

**3. No Partial Rollback**
If something fails mid-process, there is no recovery mechanism.

**4. Simplified Factory Logic**
Real-world factories would include:

* Assembly steps
* Quality control
* Production time
* Workers/machines

**5. No Persistence Layer**
Everything is in-memory. Data is lost when the application stops.

---

## 🔮 Possible Improvements

* Introduce a **Component enum** instead of raw strings
* Add **transactional behavior** (e.g., optimistic locking)
* Implement a **Quality Control (QC)** step with random failures
* Add **persistence** (database integration)
* Introduce **event-driven architecture** (e.g., build events)
* Support **batch production**

---

## 🧩 Future Idea: Quality Control (QC)

A natural evolution of this system would be adding a QC step:

* Randomly reject guitars
* Generate inspection reports
* Track defect rates
