# 📸 Photo Social App

A simple in-memory **photo sharing social platform** built in pure Java, designed to demonstrate clean domain modeling, concurrent data structures, and integration-style testing.

---

## 🚀 Overview

The **Photo Social App** simulates a minimal social network where users can:

* Publish photo posts
* Add comments to posts
* View a global timeline
* Filter posts by tags
* Enforce ownership-based deletion

The system is intentionally lightweight and uses **in-memory storage** to highlight business logic and design decisions.

---

## 🧱 Tech Stack

* Java 25
* Maven
* JUnit 5
* Concurrent Collections (Thread-safe)

---

## 📦 Project Structure

```
com.pedrohk.social
├── model
│   ├── Comment.java
│   └── PhotoPost.java
├── service
│   └── SocialService.java
└── test
    └── SocialServiceTest.java
```

---

## 🧠 Domain Model

### `Comment`

Represents a user comment on a post.

* Immutable (`record`)
* Auto-generates:

  * `UUID`
  * Timestamp

### `PhotoPost`

Represents a photo post.

* Contains:

  * Owner
  * Image URL
  * Tags (Set)
  * Comments (List)
  * Creation timestamp

* Uses:

  * `Set` → avoids duplicate tags
  * `List` → preserves comment order

---

## ⚙️ Core Service

### `SocialService`

Handles all application logic using an in-memory store:

```java
private final Map<UUID, PhotoPost> posts = new ConcurrentHashMap<>();
```

### Features:

* ✅ Publish photo posts
* 💬 Add comments
* 📰 Retrieve timeline (sorted by newest first)
* 🔍 Filter posts by tag
* 🔐 Secure deletion (only owner can delete)

---

## 🧪 Testing Strategy

The project uses **deep integration-style tests** instead of isolated unit tests.

### Why?

Because the goal is to validate:

* Real object interactions
* Data consistency
* Business rules
* Ordering and filtering logic

### Covered Scenarios:

* Publishing and retrieving posts
* Adding comments
* Filtering by tags
* Security enforcement on deletion
* Timeline ordering by date

---

## 🔍 Example Usage

```java
SocialService service = new SocialService();

var post = service.publishPhoto("pedro", "http://image.com", Set.of("java", "backend"));

service.addComment(post.id(), "user123", "Nice picture!");

var timeline = service.getTimeline();
```

---

## ⚖️ Design Trade-offs

### ✅ Pros

* **Simple and clean design**

  * Easy to understand and extend

* **Thread-safe storage**

  * `ConcurrentHashMap` allows safe concurrent access

* **Immutable domain models**

  * Reduces side effects and improves predictability

* **No external dependencies**

  * Focuses purely on Java fundamentals

* **Fast execution**

  * In-memory operations are extremely fast

---

### ❌ Cons

* **Not persistent**

  * All data is lost when the application stops

* **Limited scalability**

  * Not suitable for large-scale systems

* **Mutable internal state**

  * `comments()` list is mutable, which can break immutability guarantees

* **No validation layer**

  * Inputs are not validated (e.g., empty text, invalid URLs)

* **No authentication system**

  * Ownership is based only on string comparison

---

## 🔮 Possible Improvements

* Add a **Follower System** to personalize timelines
* Introduce **persistent storage** (e.g., database)
* Implement **authentication & authorization**
* Add **validation layer**
* Improve immutability (defensive copies)
* Paginate timeline results
