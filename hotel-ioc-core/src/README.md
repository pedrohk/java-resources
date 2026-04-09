# 🏨 Spring Core IoC - Hotel Management System

This project demonstrates the concept of **Inversion of Control (IoC)** and **Dependency Injection (DI)** using **Spring Boot 3.4.0** in a hotel management scenario.

Instead of manually creating objects, the **Spring Container acts as a Resource Manager**, controlling the lifecycle and injection of dependencies across the system.

---

## 🧠 Understanding IoC (Inversion of Control)

In a traditional approach, your application is responsible for creating its dependencies:

```java
Database db = new Database();
```

With **IoC**, you simply declare what you need:

> “I need a Database”

And **Spring provides it automatically**.

💡 The control of object creation is transferred from your code to the **Spring Container**.

---

## 🏨 Hotel Analogy

| Spring Concept       | Hotel Equivalent                                           |
| -------------------- | ---------------------------------------------------------- |
| Bean                 | Employee / Resource (Receptionist, Cleaning Staff, System) |
| ApplicationContext   | Hotel Headquarters                                         |
| Dependency Injection | Resource Delivery System                                   |

---

## ⚙️ Core Concepts

### 1. 🧩 Beans (Managed Components)

Any object managed by Spring is called a **Bean**.

```java
@Service
public class InventoryService {
    public boolean isRoomAvailable(int number) {
        return number > 0 && number < 100;
    }
}
```

---

### 2. 🏢 ApplicationContext (Central Office)

The **ApplicationContext** stores and manages all Beans.

```java
@Autowired
private ApplicationContext context;
```

You can retrieve Beans dynamically:

```java
CheckInService service = context.getBean(CheckInService.class);
```

---

### 3. 🔌 Dependency Injection (DI)

Spring automatically injects dependencies:

```java
@Service
public class CheckInService {

    private final InventoryService inventory;

    public CheckInService(InventoryService inventory) {
        this.inventory = inventory;
    }
}
```

✔ No `new` keyword
✔ Fully decoupled architecture

---

## 📦 Project Structure

```
com.pedrohk
├── HotelIocApplication
├── repository
│   └── RoomRepository
├── service
│   ├── CheckInService
│   └── NotificationService
└── test
    └── HotelIocTest
```

---

## 🚀 Technologies

* Java 21
* Spring Boot 3.4.0
* Spring Core (IoC Container)
* JUnit 5
* AssertJ

---

## 📄 Maven Configuration

```xml
<groupId>com.pedrohk</groupId>
<artifactId>hotel-ioc-master</artifactId>
<version>1.0.0</version>
```

Dependencies:

* spring-boot-starter
* spring-boot-starter-test

---

## 🧪 Testing Strategy

This project goes beyond simple unit tests and validates **Spring behavior itself**.

### ✅ What is Tested

#### 1. Context Integrity

```java
assertThat(context.containsBean("checkInService")).isTrue();
```

✔ Ensures Spring correctly registers Beans

---

#### 2. Dependency Injection Consistency

```java
assertThat(serviceFromContext).isSameAs(checkInService);
```

✔ Confirms that Spring injects the same instance

---

#### 3. Business Logic + State Validation

```java
assertThat(roomRepository.isAvailable(101)).isFalse();
```

✔ Verifies side effects and state changes

---

#### 4. Singleton Scope Behavior

```java
assertThat(roomRepository).isSameAs(anotherRepoRef);
```

✔ Ensures default Singleton scope

---

## 🏨 Example Flow

```java
boolean result = checkInService.performCheckIn(101, "Pedro Silva");
```

### Internally:

1. Spring injects `RoomRepository`
2. Spring injects `NotificationService`
3. Service checks availability
4. Updates room state
5. Sends notification

---

## 📊 Pros & Cons of IoC

| Pros                                      | Cons                                |
| ----------------------------------------- | ----------------------------------- |
| 🔓 Decoupling between classes             | ⚙️ Requires configuration knowledge |
| 🧪 Easy testing with mocks                | 🧙 Hidden complexity in large apps  |
| 🔄 Lifecycle management handled by Spring | ⏳ Slightly slower startup           |
| ♻️ Reusable components                    |                                     |

---

## 🔥 Key Takeaways

* You **don’t create objects anymore** → Spring does
* You focus on **business logic**, not infrastructure
* Your system becomes **modular, testable, and scalable**

---

## ▶️ Running the Application

```bash
mvn spring-boot:run
```

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📌 Advanced Topics (Next Steps)

* 🔀 `@Qualifier` for multiple implementations
* 🌍 `@Profile` for environment-based beans
* 🧪 Mocking Beans with `@MockBean`
* 🔄 Bean scopes (Prototype vs Singleton)
