# 🏨 Hotel BPP Master

A deep dive into **Spring Bean Lifecycle** using **Bean Post Processor (BPP)**, built with **Spring Boot 3.4** and Java 21.

This project demonstrates how to **intercept, inspect, and modify beans globally** during their lifecycle — using a real-world-inspired **Hotel Quality Inspection System**.

---

## 📌 Overview

In this project, the **Bean Post Processor (BPP)** is modeled as a:

> 🕵️ **Hotel Quality Inspector**

While:

* IoC creates the employee (Bean)
* DI provides the tools (Dependencies)

👉 The **BPP inspects and customizes employees before they start working**

---

## 🧠 Core Concept

A **Bean Post Processor** allows you to:

* Intercept beans **after instantiation**
* Apply **custom logic globally**
* Modify or wrap beans **without changing their source code**

---

## 🔄 Bean Lifecycle Interception

The BPP operates in two critical phases:

### 🔹 Before Initialization

* Runs after dependency injection
* Before `@PostConstruct`

```java
postProcessBeforeInitialization(...)
```

✔ Use cases:

* Pre-validation
* Logging
* Metadata inspection

---

### 🔹 After Initialization

* Runs after the bean is fully initialized

```java
postProcessAfterInitialization(...)
```

✔ Use cases:

* Proxy creation
* Dynamic modification
* Performance tracking

---

## 🏨 Real-World Analogy

| Stage                | Hotel Scenario             |
| -------------------- | -------------------------- |
| Bean Creation        | Employee is hired          |
| Dependency Injection | Tools are assigned         |
| Before Init          | Inspector checks uniform   |
| After Init           | Final approval before work |

---

## ⚙️ Key Features

* 🔍 **Lifecycle Interception**
* 🧩 **Custom Annotation Processing (`@AuditLog`)**
* ⚡ **Dynamic Bean Modification**
* 🧪 **Deep Lifecycle Testing**
* 🏗️ **Clean Spring Architecture**

---

## 🏗️ Project Structure

```
com.pedrohk
├── HotelBppApplication
├── annotation
│   └── AuditLog
├── processor
│   └── HotelQualityProcessor
├── service
│   └── CleaningService
└── test
    └── HotelBppDeepTest
```

---

## 🧩 How It Works

### 1. Custom Annotation

```java
@AuditLog
public class CleaningService { ... }
```

✔ Marks beans to be inspected by the BPP

---

### 2. Bean Post Processor

```java
@Component
public class HotelQualityProcessor implements BeanPostProcessor {
```

✔ Intercepts only annotated beans

---

### 3. Dynamic Behavior Injection

```java
if (bean instanceof CleaningService cleaningService) {
    cleaningService.setInspected(true);
}
```

✔ Modifies internal state during lifecycle

---

## 💡 Business Example

```java
public String performCleaning() {
    return "Room is clean";
}
```

After BPP execution:

✔ Bean is marked as inspected
✔ Behavior remains unchanged
✔ State is enhanced dynamically

---

## 🧪 Test Coverage

This project includes **advanced lifecycle validation tests**:

### ✔ BPP State Injection

* Ensures `inspected = true` after initialization

### ✔ Execution Order

* Validates:

```
BEFORE:cleaningService
AFTER:cleaningService
```

### ✔ Selective Processing

* Only beans with `@AuditLog` are processed

### ✔ Functional Integrity

* Confirms business logic is not broken

---

## 🚀 Running the Project

```bash
mvn clean test
```

---

## ⚖️ Pros & Cons

### ✅ Advantages

* Global customization of beans
* Decoupled cross-cutting concerns
* Powerful extension point in Spring
* Enables AOP-like behavior

---

### ⚠️ Trade-offs

* Can impact startup performance
* Harder debugging if misused
* Execution order matters with multiple BPPs
* Risk of breaking bean contracts

---

## 🧠 Key Takeaways

* BPP is a **low-level Spring extension mechanism**
* Ideal for:

  * Logging
  * Security validation
  * Proxies
  * Framework-level features
* Works behind the scenes — **transparent to business code**

---

## 🔥 Advanced Use Cases

You can extend this project to implement:

* ⏱️ Performance monitoring (execution time tracking)
* 🔐 Security enforcement (auto-validation)
* 🧵 Dynamic proxies (AOP alternative)
* 🧠 Custom annotations with runtime behavior
* 📊 Metrics collection system

---

## 📈 Next Steps

Explore more advanced Spring internals:

* `BeanFactoryPostProcessor`
* Spring AOP
* Custom Starter Libraries
* ApplicationContext customization
  
