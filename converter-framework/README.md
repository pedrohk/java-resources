# 🔄 Converter Framework

A lightweight and extensible **type conversion framework** built in pure Java, designed to demonstrate generic programming, runtime type resolution, and pluggable architecture.

---

## 🚀 Overview

The **Converter Framework** allows you to register and execute conversions between different types in a simple and type-safe way.

It follows a **strategy-based design**, where each conversion is implemented as an independent component and dynamically resolved at runtime.

---

## 🧱 Tech Stack

* Java 25
* Maven
* JUnit 5
* Concurrent Collections

---

## 📦 Project Structure

```
com.pedrohk.converter
├── core
│   ├── TypeConverter.java
│   └── ConversionService.java
├── example
│   ├── UserEntity.java
│   ├── UserDTO.java
│   └── UserConverter.java
└── test
    └── ConversionServiceTest.java
```

---

## 🧠 Core Concepts

### `TypeConverter<S, T>`

Defines a contract for converting one type into another.

```java
T convert(S source);
Class<S> getSourceType();
Class<T> getTargetType();
```

Each implementation is responsible for a **single conversion pair**, promoting separation of concerns.

---

### `ConversionService`

Acts as the **conversion engine**.

* Registers converters dynamically
* Resolves converters at runtime
* Executes type-safe conversions

```java
private final Map<String, TypeConverter<?, ?>> converters = new ConcurrentHashMap<>();
```

### Features:

* 🔌 Pluggable converter registration
* ⚡ Fast lookup using type-based keys
* 🧩 Supports custom and complex conversions
* 🛑 Fails fast when no converter is found
* 🧼 Null-safe (returns null when source is null)

---

## 🔍 Example Usage

```java
ConversionService service = new ConversionService();

service.register(new UserConverter());

UserEntity entity = new UserEntity(1L, "Pedro", "Henrique", "pedro@test.com");

UserDTO dto = service.convert(entity, UserDTO.class);
```

---

## 🧪 Testing Strategy

The project uses **deep tests** to validate real-world usage instead of isolated unit tests.

### Covered Scenarios:

* Successful conversion between types
* Exception when converter is missing
* Null handling
* Custom inline converters
* Complex type transformations

---

## 🧩 Example: Entity → DTO

### Input

```java
UserEntity(Long id, String firstName, String lastName, String email)
```

### Output

```java
UserDTO(String fullName, String contact)
```

### Conversion Logic

```java
return new UserDTO(
    source.firstName() + " " + source.lastName(),
    source.email()
);
```

---

## ⚖️ Design Trade-offs

### ✅ Pros

* **Highly extensible**

  * New converters can be added without modifying existing code

* **Simple architecture**

  * Easy to understand and maintain

* **Thread-safe registry**

  * Uses `ConcurrentHashMap` for safe concurrent access

* **Decoupled design**

  * Conversion logic is isolated per type pair

* **No external dependencies**

  * Pure Java solution

---

### ❌ Cons

* **Manual registration required**

  * No auto-discovery of converters

* **No inheritance support**

  * Exact type matching is required (no polymorphic lookup)

* **No conversion chaining**

  * Cannot automatically convert A → B → C

* **String-based key**

  * Less type-safe than more advanced registry designs

* **No validation layer**

  * Conversion errors must be handled manually

---

## 🔮 Possible Improvements

* Add **automatic chain conversion** (A → B → C)
* Support **polymorphic lookup** (interfaces and inheritance)
* Introduce **converter caching**
* Add **annotation-based registration**
* Improve key structure (e.g., using composite objects instead of strings)
* Provide **Spring-style integration**
