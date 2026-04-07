---

# Reflection Hotel API - Dynamic Field Processor

This project demonstrates the power of the **Java Reflection API** within a **Spring Boot 3.4.0** environment. It showcases how to inspect, invoke, and modify class members at runtime, bypassing standard Java encapsulation (private modifiers).

---

## 🧠 Reflection Concepts

Reflection is a feature in Java that allows an executing program to examine or *introspect* itself. In this project, we focus on three core operations:

### 1. Read (Introspection)

**Concept:**
The ability to inspect a class and retrieve its fields, methods, and current values — even if they are marked as `private`.

* **Where in code:** `ReflectionEngine.java` → `getPrivateField()`
* **Usage:** Uses `getDeclaredField(fieldName)` to access field metadata.

---

### 2. Modify (Manipulation)

**Concept:**
Changing the state of an object by directly setting field values, bypassing setters. This is how frameworks like ORM tools populate entities.

* **Where in code:** `ReflectionEngine.java` → `setPrivateField()`
* **Usage:**

  * `field.setAccessible(true)` disables access checks
  * `field.set(obj, value)` updates the value

---

### 3. Invoke (Execution)

**Concept:**
Executing a method dynamically using its name as a string, enabling runtime behavior not explicitly defined at compile-time.

* **Where in code:** `ReflectionEngine.java` → `callMethod()`
* **Usage:**

  * `getDeclaredMethod(methodName)`
  * `method.invoke(obj)`

---

## ⚖️ Pros and Cons of Reflection

| Pros                                                                               | Cons                                                   |
| ---------------------------------------------------------------------------------- | ------------------------------------------------------ |
| **Extreme Flexibility:** Build generic tools that work with any class.             | **Performance Overhead:** Slower than direct access.   |
| **Decoupling:** Frameworks (Spring/Jackson) don’t need prior knowledge of classes. | **Security Risks:** Breaks encapsulation.              |
| **Automation:** Useful for mapping and dynamic processing.                         | **Fragility:** Breaks at runtime if structure changes. |

---

## 🛠️ Tech Stack

* **Java:** 21 (LTS)
* **Framework:** Spring Boot 3.4.0
* **Build Tool:** Maven
* **Testing:** JUnit 5 & AssertJ

---

## 🚀 How to Run

### 1. Clone the repository

```bash
git clone https://github.com/pedrohk/java-resources.git
cd reflection-hotel-api
```

### 2. Build the project

Make sure you have Maven installed:

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

### 4. Run the tests

```bash
mvn test
```

---

## 📁 Project Structure

```
src/main/java/com/pedrohk/
├── ReflectionHotelApplication.java   # Application entry point
├── model/
│   └── HotelBooking.java             # POJO with private fields
└── reflection/
    └── ReflectionEngine.java         # Reflection logic (Read, Modify, Invoke)

src/test/java/com/pedrohk/
└── ReflectionTest.java               # Integration tests
```

---

## 💡 Notes

* Reflection is powerful but should be used with caution in production environments.
* It is widely used internally by frameworks like Spring, Hibernate, and Jackson.

---

## ⚠️ Troubleshooting (Java 21+ / 25)

If you run this project on newer Java versions (e.g., Java 25), you may encounter access restrictions due to the module system.

### Possible fix:

```bash
--add-opens java.base/java.lang=ALL-UNNAMED
```

You can add this JVM argument when running the application if needed.

---

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/4b27e981-3d2c-4023-8474-af4514d54225" />

