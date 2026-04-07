# 🏨 Hotel Concurrency API - ExecutorService

A modern **Spring Boot 3.4.0** project demonstrating how to handle concurrent operations in a hotel system using **Java 21** and **ExecutorService**.

This project simulates real-world hotel operations such as **asynchronous check-ins** and **background cleaning tasks**, showcasing practical concurrency patterns in Java.

---

## 🚀 Features

* ⚡ Asynchronous check-in processing using `Callable` and `Future`
* 🧹 Background cleaning tasks using `Runnable`
* 🧵 Thread pool management with `ExecutorService`
* 📦 Batch processing with `invokeAll`
* ✅ Concurrency-focused unit tests

---

## 🧱 Tech Stack

* Java 21
* Spring Boot 3.4.0
* Maven
* JUnit 5 + AssertJ

---

## 📁 Project Structure

```
com.pedrohk
├── model
│   └── HotelTask.java
├── service
│   └── FrontDeskService.java
├── ConcurrencyHotelApplication.java
└── HotelConcurrencyTest.java
```

---

## 🧠 Concurrency Concepts Implemented

### 1. Fixed Thread Pool

A pool of 4 worker threads simulates hotel staff handling tasks concurrently.

```java
ExecutorService executor = Executors.newFixedThreadPool(4);
```

---

### 2. Callable & Future (Check-in with Response)

Used when a result is required (e.g., check-in confirmation):

```java
Future<String> future = executor.submit(() -> {
    Thread.sleep(task.durationMillis());
    return "Check-in completed for: " + task.description();
});
```

---

### 3. Runnable (Fire-and-Forget Tasks)

Used for background operations like cleaning:

```java
executor.execute(() -> {
    Thread.sleep(task.durationMillis());
});
```

---

### 4. Batch Processing with invokeAll

Process multiple guests concurrently:

```java
List<String> results = executor.invokeAll(callables)
    .stream()
    .map(Future::get)
    .toList();
```

---

## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/pedrohk/java-resources.git
cd hotel-concurrency-api
```

### 2. Run the application

```bash
mvn spring-boot:run
```

### 3. Run tests

```bash
mvn clean test
```

---

## 🧪 Test Highlights

### ✅ Async Check-in Test

Ensures that the task runs asynchronously and returns the expected result:

```java
Future<String> future = service.executeCheckIn(task);
assertThat(future.isDone()).isFalse();
```

---

### ⚡ Concurrency Performance Test

Validates that multiple tasks run in parallel:

* 3 tasks × 200ms each
* Expected total time **< 500ms** (not 600ms)

```java
assertThat(end - start).isLessThan(500);
```

💡 This proves true parallel execution using threads.

---

## ⚖️ Pros and Cons

| Pros                                              | Cons                                       |
| ------------------------------------------------- | ------------------------------------------ |
| 🚀 Non-blocking operations improve responsiveness | ⚠️ Requires careful thread-safety handling |
| ♻️ Thread reuse improves performance              | 🧠 Harder debugging in concurrent flows    |
| ⚡ Parallel processing increases throughput        | 🔧 Manual shutdown required                |

---

## 🔒 Best Practices

* Always call `shutdown()` to avoid memory leaks
* Handle `InterruptedException` properly
* Avoid shared mutable state without synchronization
* Use timeouts when working with `Future.get()`

---

## 📌 Example Use Case

This project simulates:

* 🏨 Guests checking in concurrently at the front desk
* 🧹 Cleaning staff working in the background
* ⏱️ Reduced waiting time through parallel processing

---

## 📈 Possible Improvements

* Add `ScheduledExecutorService` for recurring tasks (e.g., daily reports)
* Integrate with a real database (PostgreSQL, Cassandra)
* Add REST endpoints to trigger tasks
* Implement monitoring (Micrometer / Actuator)
