# 🏨 Hotel Bean Scopes - Spring Core

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen)
![Build](https://img.shields.io/badge/Build-Maven-red)
![Tests](https://img.shields.io/badge/Tests-JUnit%205-success)

> A practical demonstration of **Spring Bean Scopes (Singleton vs Prototype)** using a real-world **Hotel Management System analogy**.

---

## 🧠 Concept Overview

In Spring, **Bean Scopes define how objects are created, shared, and managed** by the container.

### 🏨 Hotel Analogy

| Scope     | Real-World Example                          |
| --------- | ------------------------------------------- |
| Singleton | Hotel Reception (shared resource)           |
| Prototype | Room Key / Guest Session (unique per guest) |

---

## 🏗️ Architecture Diagram

```text
                   +----------------------+
                   |  ApplicationContext  |
                   |  (Spring Container)  |
                   +----------+-----------+
                              |
        -----------------------------------------
        |                                       |
+--------------------+              +----------------------+
|   HotelDesk        |              |   GuestSession       |
|  (Singleton)       |              |  (Prototype)         |
|--------------------|              |----------------------|
| requestCounter     |              | sessionId (UUID)     |
| shared state       |              | guestName            |
+---------+----------+              +----------+-----------+
          |                                    |
          | Shared Instance                    | New Instance Every Request
          |                                    |
   +------+-------+                     +-------+-------+
   |  Service A   |                     |   Request 1   |
   |  Service B   |                     |   Request 2   |
   +--------------+                     +---------------+
```

---

## ⚙️ Core Concepts

### 🟢 Singleton Scope (Default)

* One instance for the entire application
* Created at startup
* Shared across all components

📌 **Use case:** Stateless services and shared resources

```java
@Service
public class HotelDesk {

    private final AtomicInteger requestCounter = new AtomicInteger(0);

    public int incrementAndGetRequests() {
        return requestCounter.incrementAndGet();
    }
}
```

---

### 🔵 Prototype Scope

* New instance every time it is requested
* Not managed after creation
* Ideal for temporary/stateful data

📌 **Use case:** Per-request or per-user data

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GuestSession {

    private final String sessionId = UUID.randomUUID().toString();
    private String guestName;

}
```

---

## 🧪 Testing Strategy

This project includes **deep tests** to validate Spring behavior beyond simple logic.

### ✅ Singleton Behavior

```java
assertThat(deskA).isSameAs(deskB);
```

✔ Same instance injected
✔ Shared state across calls

---

### ✅ Prototype Behavior

```java
assertThat(session1).isNotSameAs(session2);
```

✔ Different instances
✔ Unique identity per request

---

### ✅ State Isolation

```java
session1.setGuestName("Pedro");
session2.setGuestName("Henrique");
```

✔ No shared state between instances

---

### ✅ Global State Consistency

```java
assertThat(counter).isEqualTo(previous + 1);
```

✔ Singleton maintains consistent global state

---

## 📦 Project Structure

```
com.pedrohk
├── HotelScopesApplication
├── service
│   ├── HotelDesk        (Singleton)
│   └── GuestSession     (Prototype)
└── test
    └── HotelScopesTest
```

---

## ⚖️ Pros & Cons

| Scope     | Pros                               | Cons                        |
| --------- | ---------------------------------- | --------------------------- |
| Singleton | High performance, low memory usage | Shared mutable state risks  |
| Prototype | Full isolation between instances   | Higher memory and CPU usage |

---

## 🧩 Golden Rule

> **Stateless → Singleton**
> **Stateful → Prototype**

---

## 🚀 Running the Application

```bash
mvn spring-boot:run
```

---

## 🧪 Running Tests

```bash
mvn clean test
```

---

## 🔥 Key Insights

* Spring controls the **lifecycle of objects**
* Singleton acts as a **shared system resource**
* Prototype ensures **data isolation per execution**
* Choosing the wrong scope can lead to:

  * Hidden bugs
  * Memory issues
  * Concurrency problems
