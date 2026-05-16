# 🏨 Hotel Transaction API

A practical demonstration of **Transaction Management with Spring Boot and JPA**, focusing on **data integrity**, **ACID principles**, and **automatic rollback**.

---

## 🧠 Concept (Hotel Analogy)

In a hotel system, **Transaction Management** is the guardian that ensures no operation is left halfway.

### 🧾 Check-out Scenario

When a guest checks out, the system must:

1. Mark the room as **AVAILABLE**
2. Register the **payment**
3. Generate the **invoice**

If the system crashes after step 1 but before step 2, you lose money.

➡️ Transactions guarantee:
**Either everything succeeds, or nothing happens.**

---

## 🔐 Core Concepts

### 1. ACID Principles (The Foundation)

Every transaction follows four essential rules:

* **Atomicity** → All or nothing (commit or rollback)
* **Consistency** → The system moves from one valid state to another
* **Isolation** → Concurrent transactions don’t interfere with each other
* **Durability** → Once committed, data is permanently saved

---

### 2. `@Transactional` (The Manager)

In Spring, the `@Transactional` annotation tells the framework:

> “Start a transaction here and only finish when the method completes.”

* If everything succeeds → **Commit**
* If any exception occurs → **Rollback**

---

### 3. Rollback (The Panic Button)

If something fails (e.g., payment is declined), the system:

➡️ Reverts all previous changes
➡️ Restores the original state

As if nothing ever happened.

---

## ⚖️ Pros & Cons

| Feature           | Pros                                         | Cons                                                            |
| ----------------- | -------------------------------------------- | --------------------------------------------------------------- |
| 🔐 Data Integrity | Prevents inconsistent or partial data        | Adds overhead (transaction management cost)                     |
| ⚡ Productivity    | Spring handles commit/rollback automatically | Long transactions may cause database locks                      |
| 💥 Error Handling | Automatic rollback on failure                | Misconfiguration (e.g., self-invocation) can break transactions |

---

## 🧱 Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* H2 Database (in-memory)
* JUnit 5

---

## 📦 Project Structure

```id="5u6kq1"
com.pedrohk
├── model
│   └── Room.java
├── repository
│   └── RoomRepository.java
├── service
│   └── CheckOutService.java
├── HotelTransactionApplication.java
└── HotelTransactionTest.java
```

---

## ⚙️ How It Works

### Transactional Service

```java id="1g8m9x"
@Transactional
public void performCheckOut(Integer roomNumber, boolean simulateFailure) {
    Room room = repository.findById(roomNumber)
            .orElseThrow(() -> new RuntimeException("Room not found"));

    room.setStatus("AVAILABLE");
    repository.save(room);

    if (simulateFailure) {
        throw new RuntimeException("Payment system failed! Triggering rollback...");
    }
}
```

---

## 🧪 Tests (Commit vs Rollback)

This project includes **deep transactional testing** to validate real behavior.

### ✅ Commit Scenario

* No exception occurs
* Transaction is committed
* Room becomes `AVAILABLE`

---

### ❌ Rollback Scenario

* Exception is thrown intentionally
* Transaction is rolled back
* Room remains `OCCUPIED`

---

## ▶️ Run Tests

```bash id="lq9p2x"
mvn clean test
```

---

## 📊 Expected Behavior

| Scenario | Result                             |
| -------- | ---------------------------------- |
| Success  | Room status changes to `AVAILABLE` |
| Failure  | Room status remains `OCCUPIED`     |

---

## 🔍 Why This Project Matters

This project proves that:

* Even after calling `save()`, data is **NOT persisted** if a failure occurs
* Spring’s transaction system **protects your database automatically**
* You can simulate and validate **real rollback scenarios**

---

## 🧠 Key Takeaway

Transaction Management turns Spring into the **guarantor of your system’s integrity**.

Without it, a high-concurrency system (like a busy hotel) would quickly become:

❌ inconsistent
❌ unreliable
❌ financially risky

---

## 🚀 Next Steps

Want to go deeper?

* 🔁 **Propagation Levels** (REQUIRED, REQUIRES_NEW, etc.)
* 🔒 **Isolation Levels** (prevent dirty reads & race conditions)
* 🌐 **Distributed Transactions**

---

## 📌 Final Thought

The idea of **“all or nothing”** is one of the most critical principles in backend systems.

Mastering transactions means building systems that are **safe, predictable, and production-ready**.
