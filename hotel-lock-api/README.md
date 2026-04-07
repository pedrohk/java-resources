# 🔐 Hotel Lock API – Concurrency with Locks & Atomic Variables

A practical Java project demonstrating **thread safety and concurrency control** using:

* `AtomicInteger`
* `ReentrantLock`

Built with **Java 21** and **Spring Boot 3.4**, this project simulates real-world race conditions in a **Hotel Management System**.

---

## 🏨 Problem Scenario

Imagine two receptionists trying to reserve the **last available room at the same time**.

Without proper synchronization, the system could:

❌ Double-book the same room
❌ Produce inconsistent guest counts

This project shows how to solve these issues using **Atomic Variables** and **Locks**.

---

## ⚡ Key Concepts

### 🔢 Atomic Variables (Fast Counter)

Atomic variables ensure that simple operations happen **atomically (all-or-nothing)** without thread interference.

**Hotel analogy:**

> A breakfast counter tracking guests entering simultaneously.

```java
private final AtomicInteger guestCounter = new AtomicInteger(0);

public void incrementGuestCount() {
    guestCounter.incrementAndGet();
}
```

### ✅ Why use Atomic?

* High performance (CPU-level CAS)
* Non-blocking
* Thread-safe increments

---

### 🔑 Locks (Room Key Control)

`ReentrantLock` provides explicit control over critical sections.

**Hotel analogy:**

> Only one receptionist can "hold the key" to Room 101 while booking it.

```java
private final Lock reservationLock = new ReentrantLock();

public boolean reserveRoom101() {
    if (reservationLock.tryLock()) {
        try {
            if (!isRoom101Reserved) {
                Thread.sleep(50);
                isRoom101Reserved = true;
                return true;
            }
        } finally {
            reservationLock.unlock();
        }
    }
    return false;
}
```

### ✅ Why use Locks?

* Fine-grained control
* Try-lock with timeout capability
* Safer for complex operations

---

## 🔄 How It Works

1. Multiple threads simulate hotel activity
2. Guests are counted using `AtomicInteger`
3. Room reservation is protected by `ReentrantLock`
4. Only **one successful reservation** is allowed

---

## 📦 Project Structure

```
hotel-lock-api/
 ├── src/main/java/com/pedrohk/
 │   ├── HotelLockApplication.java
 │   └── service/HotelManagerService.java
 ├── src/test/java/com/pedrohk/
 │   └── HotelLockTest.java
 └── pom.xml
```

---

## 🧪 Concurrency Test

This test simulates **high contention**:

* 100 concurrent guest entries
* Multiple simultaneous reservation attempts

```java
ExecutorService executor = Executors.newFixedThreadPool(10);

for (int i = 0; i < 100; i++) {
    executor.execute(service::incrementGuestCount);
    executor.execute(() -> {
        if (service.reserveRoom101()) {
            successReservations.incrementAndGet();
        }
    });
}
```

### ✅ Expected Results

* Guest count = **100**
* Successful reservations = **1**

---

## ⚖️ Pros & Cons

| Feature                   | Pros                                | Cons                         |
| ------------------------- | ----------------------------------- | ---------------------------- |
| **Atomic Variables**      | High performance; Non-blocking      | Limited to simple operations |
| **Locks (ReentrantLock)** | Full control over critical sections | Risk of deadlocks if misused |

---

## 🚀 Running the Project

```bash
mvn clean test
```

---

## 🧠 Concepts Covered

* Thread safety
* Race conditions
* Atomic operations (CAS)
* Lock-based synchronization
* ExecutorService
* Concurrent testing

---

## 🔜 Next Steps

To further improve concurrency performance, consider:

### 👉 `ReadWriteLock`

* Multiple threads can **read simultaneously**
* Only one thread can **write (reserve)**

**Hotel analogy:**

> Many guests can check if a room is available, but only one can book it.

