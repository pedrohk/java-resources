# 🏨 Hotel Time API – Modern Java Date & Time Handling

A practical project demonstrating **modern date and time management** in a hotel system using **Java 21** and **Spring Boot 3.4**.

Handling time is at the core of any hotel business:
**check-ins, check-outs, breakfast schedules, and international guest time zones.**

This project showcases how to properly work with these scenarios using the **Java Time API (`java.time`)**, the modern replacement for legacy date handling in Java.

---

## 📚 Evolution of Date Handling in Java

### ⏳ Joda-Time (The Past)

Before Java 8, developers struggled with:

* `Date` and `Calendar` being confusing and error-prone
* Months starting at **0**
* Lack of thread safety (bad for concurrency)

**Joda-Time** emerged as the industry standard to fix these issues.

👉 In a hotel system:
You would use Joda-Time to safely calculate the number of days between check-in and check-out.

---

### 🚀 Java Time API / JSR-310 (The Present)

Starting from **Java 8** (and now standard in Java 21+), Java introduced `java.time`, inspired by Joda-Time.

✅ Today, **you no longer need Joda-Time**.

---

## 🧱 Core Concepts Used in This Project

| Class           | Purpose                                      |
| --------------- | -------------------------------------------- |
| `LocalDate`     | Represents a date (e.g., booking date)       |
| `LocalTime`     | Represents time (e.g., check-in hour)        |
| `LocalDateTime` | Combines date and time                       |
| `ZonedDateTime` | Handles time zones (international guests 🌍) |
| `Period`        | Calculates differences in days/months        |
| `Duration`      | Calculates differences in hours/minutes      |

---

## ⚖️ Pros & Cons

| Feature       | Pros                                        | Cons                                 |
| ------------- | ------------------------------------------- | ------------------------------------ |
| Java Time API | Immutable → Thread-safe (Concurrency ready) | Learning curve if coming from `Date` |
| Java Time API | Fluent API (`plusDays`, `minusHours`)       | Legacy APIs may still require `Date` |
| Joda-Time     | Pioneer and inspiration                     | Deprecated (migration recommended)   |

---

## 💡 Practical Example

```java
LocalDate checkIn = LocalDate.now();
LocalDate checkOut = checkIn.plusDays(3);

long stayDuration = ChronoUnit.DAYS.between(checkIn, checkOut);

System.out.println("Total nights: " + stayDuration);
```

✅ No loops
✅ No millisecond calculations
✅ Clean and readable

---

## 🏗️ Project Structure

```
src
 ├── model
 │    └── BookingDuration.java
 ├── service
 │    └── TimeService.java
 ├── HotelTimeApplication.java
 └── test
      └── TimeApiTest.java
```

---

## 📦 Key Components

### 🧾 BookingDuration (Model)

```java
public record BookingDuration(LocalDate checkIn, LocalDate checkOut) {
    public long getTotalNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }
}
```

✔ Encapsulates stay duration logic
✔ Uses `ChronoUnit` for accurate calculation

---

### ⚙️ TimeService (Service)

```java
public String formatArrival(LocalDateTime arrival) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return arrival.format(formatter);
}

public double calculateStayPrice(BookingDuration duration, double pricePerNight) {
    return duration.getTotalNights() * pricePerNight;
}
```

✔ Formats dates for real-world usage
✔ Calculates total stay cost

---

### 🧪 Tests (JUnit 5)

```java
assertThat(duration.getTotalNights()).isEqualTo(5);
assertThat(totalPrice).isEqualTo(1000.0);
```

✔ Validates business logic
✔ Ensures correct date calculations

---

## 🚀 Running the Project

### Run Tests

```bash
mvn clean test
```

---

## 🧠 Key Takeaways

* ✅ Use `java.time` instead of legacy `Date`/`Calendar`
* ✅ Prefer immutable objects for thread safety
* ✅ Use `ChronoUnit` for clean date calculations
* ✅ Avoid manual time calculations (no milliseconds math!)

---

## 📈 Why This Matters

In real-world systems like hotel management:

* Incorrect date handling = **financial loss 💸**
* Time zone issues = **bad user experience 🌍**
* Poor design = **hard-to-maintain code**

This project demonstrates how to solve all of these using **modern Java best practices**.
