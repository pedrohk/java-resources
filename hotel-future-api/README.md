# ☕ Java Resources - Concurrency & Advanced Concepts

A curated collection of **practical Java examples** focused on modern backend development concepts, using a **Hotel Management System** as a real-world analogy.

This repository demonstrates how to apply **Java 21+ features**, **concurrency patterns**, and **clean architecture principles** in a simple and didactic way.

---

## 🏨 Project Context

All examples are based on a **hotel environment**, making complex concepts easier to understand.

Think of the system like this:

* Guests request services (tasks)
* Staff processes them asynchronously
* The system must remain responsive at all times

---

## ⚡ Callable & Future – Asynchronous Kitchen Example

Imagine a guest orders a **complex room service meal**.

The receptionist **cannot wait** for the dish to be ready before helping the next guest.

This is where `Callable` and `Future` come in.

---

### 🧾 Callable (The Order Ticket)

`Callable` represents a task that:

* Runs asynchronously
* Returns a result
* Can throw exceptions

**Hotel analogy:**

> “Prepare dish X and return the final plate.”

```java
Callable<String> kitchenTask = () -> {
    Thread.sleep(3000);
    return "Dinner is ready!";
};
```

---

### 📟 Future (The Pager)

`Future` represents a **pending result**.

* You can check if it's ready → `isDone()`
* Or wait for it → `get()`

**Hotel analogy:**

> The guest receives a vibrating pager while waiting.

```java
Future<String> pager = waiter.submit(kitchenTask);

System.out.println("Reception is free to help others...");

String meal = pager.get();
System.out.println(meal);
```

---

## 🔄 How It Works Together

1. The order is created (`Callable`)
2. The kitchen starts preparing it (`ExecutorService`)
3. The guest gets a pager (`Future`)
4. The system continues working
5. The result is retrieved when needed (`get()`)

---

## 📦 Project Structure

```
java-resources/
 ├── hotel-future-api/
 │   ├── KitchenService.java
 │   ├── HotelFutureApplication.java
 │   └── KitchenServiceTest.java
 └── pom.xml
```

---

## 🧪 Example: Kitchen Service

```java
public Future<String> prepareOrder(String dishName, int preparationTime) {
    Callable<String> task = () -> {
        Thread.sleep(preparationTime);
        return "Dish " + dishName + " is ready!";
    };
    return kitchenStaff.submit(task);
}
```

---

## ✅ Test Example

```java
@Test
void shouldReturnResultFromKitchenTask() throws Exception {
    Future<String> pager = service.prepareOrder("Pasta", 500);

    assertThat(pager.isDone()).isFalse();

    String result = pager.get(1, TimeUnit.SECONDS);
    assertThat(result).isEqualTo("Dish Pasta is ready!");
}
```

---

## ⚖️ Pros & Cons

### ✅ Pros

* **Return Values**: Retrieve results safely from background threads
* **Exception Handling**: Supports checked exceptions
* **Task Control**: إمكانية cancel tasks via `Future`

### ❌ Cons

* **Blocking Calls**: `future.get()` can block execution
* **Complexity**: Managing many Futures can get messy
* **Timeout Handling**: Required to avoid indefinite waiting

---

## 🚀 Running the Project

```bash
mvn clean test
```

---

## 🧠 Concepts Covered

* `Callable`
* `Future`
* `ExecutorService`
* Thread pools
* Asynchronous task execution
* Basic concurrency testing

---

## 🔜 Next Steps

This project is a foundation for more advanced async patterns.

👉 A natural evolution is:

* **CompletableFuture**

  * Non-blocking pipelines
  * Task chaining
  * Parallel composition

Example flow:

```
Prepare dish → Plate dish → Notify waiter → Deliver to guest
```
