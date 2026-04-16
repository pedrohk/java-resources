# ☕ Hotel Caffeine Cache API

High-performance caching in Java using **Caffeine** and **Spring Boot**.

This project demonstrates how to drastically improve application performance by using an in-memory cache, replacing repeated database access with lightning-fast RAM lookups.

---

## 🏨 Concept (Hotel Analogy)

In the context of a hotel, **Caffeine** works like a **“Quick Keys Desk”** at the reception.

Instead of the receptionist going to the basement (database) every time someone asks if room 101 is available, the most frequent answers are kept right at the front desk.

➡️ Result: instant responses, happier guests, and a faster system.

---

## ⚡ What is Caffeine?

Caffeine is a **high-performance in-memory cache for Java**.

It stores frequently accessed data in RAM so your application can avoid expensive and repetitive operations like database queries or external API calls.

---

## 🧠 Use Cases (Hotel Scenario)

### 1. Room Availability

Cache room availability for short periods (e.g., 1 minute) to handle high traffic from booking platforms.

### 2. Pricing Table

Daily rates don’t change frequently—cache them to reduce database load.

### 3. User Sessions

Keep active guest data in memory while they interact with the system.

---

## ⚙️ How Caffeine Works

* **⏱ Time-based Expiration**
  Automatically removes data after a defined duration
  → e.g., expire after 10 minutes

* **📦 Size-based Eviction**
  Limits cache size and removes least recently used entries (LRU)

* **🔄 Automatic Loading**
  Fetches data when not present (Cache-aside / Read-through pattern)

---

## ✅ Pros & ❌ Cons

| Feature        | Pros                                       | Cons                                          |
| -------------- | ------------------------------------------ | --------------------------------------------- |
| 🚀 Performance | Extremely fast (near-native speed)         | Cache is local (not shared across servers)    |
| 🧩 Simplicity  | Easy setup, no external service required   | Uses application memory (RAM)                 |
| 📊 Metrics     | Provides cache statistics (hit rate, etc.) | Risk of stale data if not configured properly |

---

## 🧱 Tech Stack

* Java 21
* Spring Boot 3
* Spring Cache Abstraction
* Caffeine Cache
* JUnit 5

---

## 📦 Project Structure

```
com.pedrohk
├── config
│   └── CacheConfig.java
├── service
│   └── RoomAvailabilityService.java
├── HotelCacheApplication.java
└── HotelCachePerformanceTest.java
```

---

## ⚙️ Configuration

### Cache Setup

```java
@Bean
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("roomAvailability");
    cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100));
    return cacheManager;
}
```

---

## 🧩 Service Layer

```java
@Cacheable(value = "roomAvailability")
public String checkAvailability(int roomNumber) {
    Thread.sleep(2000); // Simulated delay
    return "Room " + roomNumber + " is available";
}
```

---

## 🧪 Performance Test

This test proves the effectiveness of caching:

* First call → slow (simulates database access)
* Second call → instant (served from cache)

```bash
mvn clean test
```

### Expected Output

```
First call: ~2000ms
Second call: <100ms
```

---

## 📈 Why This Project Matters

* Demonstrates **real-world performance optimization**
* Uses **Spring Cache abstraction correctly**
* Shows **clear measurable impact (2000ms → ~0ms)**
* Clean and production-ready configuration

---

## 🧠 Key Takeaway

Caffeine is the **#1 choice for ultra-fast local caching in Java applications** when:

* You don’t need distributed caching
* You want simplicity and performance
* You want to reduce database load drastically

---

## 🚀 Next Steps

Once you master local caching, the next evolution is:

➡️ Distributed caching with Redis (shared across multiple servers)

---

## 📌 Final Thought

Using RAM to avoid unnecessary trips to the database is one of the **simplest and most powerful optimizations** you can apply.

