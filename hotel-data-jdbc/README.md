# 🏨 Spring Data JDBC — The "Straightforward Receptionist"

This project demonstrates **Spring Data JDBC** using a simple and intuitive **hotel management analogy**.

> Think of Spring Data JDBC as a **direct and objective receptionist**:
> it takes guest information and stores it exactly where you tell it — no magic, no hidden behavior.

Unlike **Spring Data JPA (Hibernate)** — which acts like a sophisticated manager automating everything — JDBC is designed for **clarity, control, and predictability**.

---

## 📌 Core Concept: Straight to the Point

Spring Data JDBC follows a **"what you see is what you get"** approach:

* No **Persistence Context**
* No **Lazy Loading**
* No hidden state transitions

### 🏨 Hotel Analogy

* A `Booking` object maps **directly** to the `BOOKING` table
* When you fetch data → it runs a `SELECT`
* When you save → it runs an `INSERT` or `UPDATE`

There are **no magic states** like:

* Detached
* Managed

---

## 🧩 Aggregates (The Heart of JDBC)

Spring Data JDBC is built around **Aggregates**.

### 🏨 Hotel Example

* A `Booking` **owns** multiple `ServiceItem`s
* The `Booking` is the **Aggregate Root**

✔ When you save a booking → its items are saved automatically
✔ When you delete a booking → its items are deleted too

👉 Clear ownership. No ambiguity.

---

## 📦 Repository Layer

Just like JPA, you define repositories using interfaces:

```java
@Repository
public interface BookingRepository extends ListCrudRepository<Booking, Long> {
}
```

✔ No implementation needed
✔ Lightweight and fast
✔ Less abstraction, more control

---

## 🧱 Project Structure

```
com.pedrohk
├── model
│   ├── Booking.java
│   └── ServiceItem.java
├── repository
│   └── BookingRepository.java
└── HotelJdbcApplication.java
```

---

## 🗄️ Database Schema (H2)

```sql
CREATE TABLE booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255),
    room_number VARCHAR(10)
);

CREATE TABLE service_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking BIGINT,
    description VARCHAR(255),
    price DOUBLE
);
```

---

## 🧪 Testing the Persistence Layer

The project includes a working integration test:

```java
@SpringBootTest
class BookingRepositoryTest {
    @Autowired
    private BookingRepository repository;

    @Test
    void shouldSaveAndLoadBookingWithItems() {
        ServiceItem item1 = new ServiceItem(null, "Breakfast", 30.0);
        ServiceItem item2 = new ServiceItem(null, "Soda", 5.0);

        Booking booking = new Booking(null, "Pedro", "101", Set.of(item1, item2));

        Booking saved = repository.save(booking);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.items()).hasSize(2);
    }
}
```

---

## ⚠️ Common Pitfalls (Important!)

### 1. Java Records & Immutability

Spring Data JDBC works with immutable structures, but:

* You must pass collections **fully initialized**
* No incremental mutation after creation

✔ Correct approach:

```java
new Booking(null, "Pedro", "101", Set.of(item1, item2));
```

---

### 2. Naming Strategy Matters

Spring Data JDBC is **strict** with naming:

* Table names must match exactly
* Foreign key column must match parent reference

✔ Example:

```sql
booking BIGINT  -- references BOOKING table
```

💡 Tip: Use **UPPERCASE** to avoid H2 inconsistencies.

---

## ⚖️ Pros & Cons

| Feature        | Pros                           | Cons                              |
| -------------- | ------------------------------ | --------------------------------- |
| Simplicity     | Easy to understand and debug   | More manual relationship handling |
| Performance    | Fast startup, low memory usage | No caching                        |
| Predictability | Full control over SQL          | No automatic dirty checking       |

---

## 🚀 Why Use This Approach?

Use Spring Data JDBC if:

✔ You feel JPA/Hibernate is too "magical"
✔ You want full control over SQL execution
✔ You prefer simplicity over abstraction

👉 It’s the perfect balance between:

* **Raw JDBC (too manual)**
* **JPA (too automatic)**

---

## ▶️ Running the Project

```bash
mvn clean test
```
