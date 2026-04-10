# 🏨 Hotel Value API

A practical **Spring Boot 3.4** project demonstrating how to use the `@Value` annotation to externalize configuration in a clean and flexible way.

This project uses a **hotel management analogy** to make Spring concepts easier to understand, showing how configuration values are injected into your application just like a manager distributes rules and settings across a hotel.

---

## 🧠 Concept Overview

In this project, the `@Value` annotation acts like a **Configuration Board** or **Manager’s Rulebook** in a hotel.

Instead of hardcoding values (like hotel name, pricing, or rules), they are stored externally in `application.properties`, and `@Value` acts as a bridge to inject them into your Spring Beans.

---

## 🏨 Use Cases in the Hotel Context

### 1. Simple Configuration Injection

Inject basic values directly from properties.

**Example:**

* Hotel name
* Base room price
* Support email

---

### 2. Default Values (Fallback Plan)

Provide a fallback when a property is missing.

**Example:**

* If no discount is configured → default to `5%`

---

### 3. SPEL (Spring Expression Language)

Perform calculations or logic during injection.

**Example:**

* Automatically calculate final price with tax:

  ```
  basePrice * (1 + taxRate)
  ```

---

### 4. Environment Variables

Read system-level properties.

**Example:**

* Access the server directory where room images are stored

---

## ⚙️ Tech Stack

* **Java 21**
* **Spring Boot 3.4.0**
* **Maven**
* **JUnit 5 + AssertJ**

---

## 📁 Project Structure

```
hotel-value-api
├── src/main/java/com/pedrohk
│   ├── HotelValueApplication.java
│   └── service/
│       └── HotelConfigService.java
├── src/main/resources/
│   └── application.properties
├── src/test/java/com/pedrohk/
│   └── HotelValueTest.java
└── pom.xml
```

---

## 🔧 Configuration Example

`application.properties`

```properties
hotel.name=Pedro Palace Hotel
hotel.base-price=250.0
hotel.tax-rate=0.15
hotel.available-floors=1,2,3,4,5
```

---

## 💡 Core Implementation

### Service Using `@Value`

```java
@Service
public class HotelConfigService {

    @Value("${hotel.name}")
    private String hotelName;

    @Value("${hotel.base-price}")
    private double basePrice;

    @Value("${hotel.discount-rate:0.05}")
    private double discountRate;

    @Value("#{${hotel.base-price} * (1 + ${hotel.tax-rate})}")
    private double finalPriceWithTax;

    @Value("${hotel.available-floors}")
    private List<Integer> availableFloors;

    @Value("#{systemProperties['user.home']}")
    private String userHomeFolder;    
}
```

---

## 🧪 Testing Strategy

This project includes **integration tests** validating all injection scenarios:

* ✅ Simple property injection
* ✅ Default value fallback
* ✅ SPEL calculation
* ✅ List parsing (`String → List<Integer>`)
* ✅ System property injection

Run tests with:

```bash
mvn clean test
```

---

## 📊 Pros & Cons

| Pros                                         | Cons                                   |
| -------------------------------------------- | -------------------------------------- |
| Flexible configuration without recompilation | Harder refactoring (string-based keys) |
| Supports environment-based configs           | Can clutter code if overused           |
| Enables secure external configs (env vars)   | Type conversion requires attention     |
| Works great for small, dynamic values        | Not ideal for large config structures  |

---

## 🎯 Key Takeaways

* `@Value` is ideal for **small and dynamic configurations**
* Keeps your code **clean and decoupled**
* Great for **environment-based setups**
* For larger configurations, consider using `@ConfigurationProperties`

---

## 🚀 Next Steps

Want to level up?

👉 Explore `@ConfigurationProperties` for:

* Type-safe configuration
* Better organization
* Scalable config management
