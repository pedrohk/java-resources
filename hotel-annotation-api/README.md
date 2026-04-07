# 🏷️ Hotel Annotation API – Custom Annotations with Reflection

A practical Java project demonstrating how to build and use **custom annotations** combined with **Reflection** to implement dynamic behavior at runtime.

Built with **Java 21** and **Spring Boot 3.4**, this project uses a **Hotel Management System** analogy to simplify advanced concepts.

---

## 🏨 Concept Overview

In this project, annotations act like **smart labels**.

Instead of hardcoding rules inside methods, you:

* Tag methods with annotations
* Use Reflection to **read and enforce rules dynamically**

---

## 🎯 Problem Scenario

Imagine a hotel where:

* Some rooms are **VIP-only**
* The system must automatically enforce access rules

Instead of writing repetitive `if/else` checks, we use a custom annotation:

```java
@PremiumOnly
```

This allows the system to **intercept method calls and validate access dynamically**.

---

## 🧠 Key Concepts

### 🏷️ Custom Annotation (`@PremiumOnly`)

Defines metadata that can be read at runtime.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PremiumOnly {
    String message() default "Access denied: Premium status required.";
}
```

### ✅ Why it matters

* Declarative programming style
* Cleaner business logic
* Reusable rules

---

### 🔍 Reflection Processor (Security Engine)

The engine that scans and enforces annotation rules.

```java
public static Object process(Object target, String methodName, boolean isVip) throws Exception {
    Method method = target.getClass().getMethod(methodName);

    if (method.isAnnotationPresent(PremiumOnly.class)) {
        if (!isVip) {
            PremiumOnly annotation = method.getAnnotation(PremiumOnly.class);
            throw new SecurityException(annotation.message());
        }
    }

    return method.invoke(target);
}
```

### ✅ What it does

* Inspects methods at runtime
* Detects annotations
* Applies access control dynamically

---

## 🏨 Service Example

```java
public class BookingService {

    @PremiumOnly(message = "Attention: Only VIP guests can book the Presidential Suite!")
    public String bookPresidentialSuite() {
        return "Presidential Suite booked";
    }

    public String bookStandardRoom() {
        return "Standard room booked";
    }
}
```

---

## 🔄 Execution Flow

1. Method is called via `SecurityProcessor`
2. Reflection checks for `@PremiumOnly`
3. If present:

   * Validate VIP access
   * Throw exception if unauthorized
4. If valid → method is executed

---

## 📦 Project Structure

```bash
hotel-annotation-api/
 ├── annotation/
 │   └── PremiumOnly.java
 ├── processor/
 │   └── SecurityProcessor.java
 ├── service/
 │   └── BookingService.java
 ├── HotelAnnotationApplication.java
 ├── AnnotationTest.java
 └── pom.xml
```

---

## 🧪 Test Scenarios

### ❌ Non-VIP Access (Blocked)

```java
assertThatThrownBy(() ->
    SecurityProcessor.process(service, "bookPresidentialSuite", false)
).isInstanceOf(SecurityException.class);
```

### ✅ VIP Access (Allowed)

```java
Object result = SecurityProcessor.process(service, "bookPresidentialSuite", true);
```

### ✅ Public Access (No Annotation)

```java
Object result = SecurityProcessor.process(service, "bookStandardRoom", false);
```

---

## ⚖️ Pros & Cons

| Pros                                      | Cons                                      |
| ----------------------------------------- | ----------------------------------------- |
| Cleaner code (no repetitive conditionals) | Hidden complexity (logic outside methods) |
| Reusable and standardized rules           | Reflection has performance cost           |
| Strong separation of concerns             | Errors only appear at runtime             |

---

## 🚀 Running the Project

```bash
mvn clean test
```

---

## 🧠 Concepts Covered

* Custom annotations (`@interface`)
* Reflection API
* Runtime metadata processing
* Dynamic method invocation
* Separation of concerns
* Test-driven validation

---

## 🔥 Why This Matters

This pattern is widely used in real frameworks:

* Spring (`@Transactional`, `@PreAuthorize`)
* Jakarta EE
* Hibernate

You're essentially building a **mini-framework behavior from scratch**.

---

## 🔜 Next Steps

To evolve this project further:

### 👉 Add Annotation Parameters

* Roles (`@RoleAllowed`)
* Logging (`@Audit`)
* Rate limiting

### 👉 Combine with AOP (Spring)

* Replace manual Reflection with **Aspect-Oriented Programming**
