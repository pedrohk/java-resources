# 🏨 Hotel Injection API

A practical **Spring Boot 3.4** project demonstrating the difference between **Constructor Injection** and **Setter Injection** using a real-world-inspired **Hotel Management System**.

This Proof of Concept (PoC) shows how dependency injection works internally in Spring, using clean architecture principles and test-driven validation.

---

## 📌 Overview

In this project, dependency injection is explained through a **hotel analogy**:

* **Constructor Injection** → Employee receives all required tools at hiring time
* **Setter Injection** → Employee receives optional tools during the job

This helps visualize how Spring manages object creation and dependency delivery.

---

## 🧠 Concepts Demonstrated

### 🔹 Constructor Injection (Mandatory Dependencies)

* Dependencies are provided **at object creation**
* Ensures the object is always in a **valid state**
* Enables use of `final` fields (immutability)
* Recommended by Spring

**Example in project:**

```java
public FrontDeskService(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
}
```

✔ Used for:

* Core business services
* Critical dependencies

---

### 🔹 Setter Injection (Optional Dependencies)

* Dependencies are provided **after object creation**
* Allows optional or changeable components
* Useful for avoiding circular dependencies

**Example in project:**

```java
@Autowired
public void setMarketingService(MarketingService marketingService) {
    this.marketingService = marketingService;
}
```

✔ Used for:

* Optional features
* External integrations
* Configurable behaviors

---

## ⚖️ Pros & Cons

| Feature               | Constructor Injection | Setter Injection     |
| --------------------- | --------------------- | -------------------- |
| Immutability          | ✅ Yes (`final`)       | ❌ No                 |
| Object Safety         | ✅ Always valid        | ⚠️ May be incomplete |
| Spring Recommendation | ⭐ Preferred           | ⚠️ Secondary         |
| Testability           | ✅ Easy (constructor)  | ⚠️ Requires setters  |
| Flexibility           | ❌ Less flexible       | ✅ More flexible      |

---

## 🏗️ Project Structure

```
com.pedrohk
├── HotelInjectionApplication
├── service
│   ├── FrontDeskService
│   ├── InventoryService
│   └── MarketingService
└── test
    └── HotelInjectionTest
```

---

## 🔧 Tech Stack

* Java 21
* Spring Boot 3.4
* JUnit 5
* AssertJ
* Maven

---

## 🚀 How It Works

### Flow:

1. Spring creates `InventoryService`
2. Injects it via **constructor** into `FrontDeskService`
3. Creates `MarketingService`
4. Injects it via **setter method**
5. Business logic runs combining both dependencies

---

## 💡 Business Example

```java
public String registerGuest(int room) {
    if (inventoryService.checkAvailability(room)) {
        String msg = "Guest registered in room " + room;

        if (marketingService != null) {
            msg += " - Message: " + marketingService.getNewsletter();
        }

        return msg;
    }
    return "No rooms available";
}
```

---

## 🧪 Test Coverage

This project includes **advanced validation tests**:

### ✔ Constructor Injection যাচ

* Verifies field is `final`
* Ensures dependency is never null

### ✔ Setter Injection যাচ

* Confirms optional dependency is injected
* Validates correct type

### ✔ Business Logic

* Ensates combined behavior works correctly

### ✔ Context Validation

* Confirms Spring Boot loaded all beans

Run tests:

```bash
mvn clean test
```

---

## 🧩 Key Learning Takeaways

* Use **Constructor Injection** for required dependencies
* Use **Setter Injection** for optional features
* Prefer immutability for safer and cleaner code
* Spring handles lifecycle and wiring automatically

---

## 🧠 Real-World Analogy Recap

| Scenario                           | Injection Type |
| ---------------------------------- | -------------- |
| Employee needs tools to start job  | Constructor    |
| Employee receives extra tool later | Setter         |

---

## 📈 Next Steps

You can extend this project to explore:

* Circular Dependency handling
* Field Injection (and why to avoid it)
* Profiles & Conditional Beans
* Advanced Bean Scopes
