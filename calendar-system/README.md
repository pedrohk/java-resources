# 📅 Calendar System

A simple and thread-safe **calendar scheduling system** built in Java.  
This project demonstrates core backend concepts such as conflict detection, time-slot suggestion, and concurrent data handling.

---

## 🚀 Features

- 📌 Create meetings with multiple participants
- ⚠️ Conflict detection to prevent overlapping meetings
- 📖 List meetings per user (sorted by start time)
- ❌ Remove meetings
- 🤝 Suggest best available time slot between two users
- 🧵 Thread-safe in-memory storage using `ConcurrentHashMap`
- 🧪 Fully tested with JUnit 5

---

## 🏗️ Project Structure

```

calendar-system/
├── model/
│   └── Meeting.java
├── service/
│   └── CalendarService.java
└── test/
└── CalendarServiceTest.java

````

---

## 🧠 Core Concepts

### 1. Meeting Model

The `Meeting` is implemented as a **Java Record**, ensuring immutability and simplicity.

Key behavior:
- Detects overlapping meetings using:
```java
return start.isBefore(otherEnd) && otherStart.isBefore(end);
````

---

### 2. Calendar Service

The `CalendarService` is responsible for all business logic:

#### ✔️ Booking Meetings

* Validates time range
* Checks conflicts for all participants
* Stores meetings per user

#### ❌ Conflict Handling

If any participant already has a meeting in the same time slot:

```
IllegalStateException: Conflict for participant
```

#### 📋 Listing Meetings

* Returns meetings sorted by start time

#### 🧠 Suggesting Best Time

* Iterates over time slots (30-minute steps)
* Finds the first available slot for both users

---

## 🧪 Testing

The project uses **JUnit 5** to validate behavior.

### Covered Scenarios:

* Successful meeting booking
* Conflict detection
* Meeting removal
* Time suggestion between users
* No available slot scenario

Example:

```java
assertEquals(searchStart.plusHours(2), suggestion.get());
```

---

## ⚙️ Tech Stack

* Java 25
* Maven
* JUnit 5

---

## 🔄 Trade-offs

### ✅ Pros

* Simple and clean design
* Easy to understand and extend
* Thread-safe implementation
* Fast in-memory operations
* Uses modern Java features (Records, Streams, Optional)

### ⚠️ Cons

* Data is stored in-memory (no persistence)
* Not optimized for large-scale usage
* Limited to basic scheduling (no recurring events)
* Suggestion algorithm is linear (can be inefficient at scale)

---

## 🐞 Bug Fix Note

A test issue was identified in:

```
shouldBookMeetingSuccessfully
```

### Problem:

The test expected **2 meetings** for a user, but only **1 was actually created**.

### Fix:

Updated assertion:

```java
assertEquals(1, service.listMeetings("user1").size());
```

---

## 🔮 Future Improvements

* 🔁 Recurring meetings (e.g., every Monday)
* 🗄️ Persistent storage (database integration)
* 👥 Support for more than two users in time suggestion
* ⚡ Optimized scheduling algorithm
* 🌐 REST API layer (Spring Boot)

---

## 📦 How to Run

```bash
mvn clean install
```

Run tests:

```bash
mvn test
