# 🏨 Streams Hotel API

A modern Java project demonstrating the power of the **Streams API** in a real-world scenario: a **Hotel Management System**.

Built with:
- ☕ **Java 21**
- 🚀 **Spring Boot 3.4.0**
- 🧪 **JUnit 5 + AssertJ**

This project focuses on applying functional programming concepts to process collections such as **Lists, Arrays, and Maps** in a clean, declarative way.

---

## 🧠 Concepts Implemented

### 1. 🔍 Filtering (Predicate)
Filter available rooms using stream operations:
```java
rooms.stream()
     .filter(room -> !room.isOccupied())
     .toList();
````

---

### 2. 🔄 Mapping (Transformation)

Extract specific data (room numbers) from objects:

```java
rooms.stream()
     .filter(room -> room.type().equalsIgnoreCase("Deluxe"))
     .map(Room::number)
     .toList();
```

---

### 3. 📦 Arrays to Streams

Convert arrays into streams for aggregation:

```java
Arrays.stream(roomsArray)
      .mapToDouble(Room::price)
      .sum();
```

---

### 4. 🗂️ Collectors (Grouping)

Group rooms dynamically by type:

```java
rooms.stream()
     .collect(Collectors.groupingBy(Room::type));
```

---

## 💡 Example Usage

Here’s a practical example of how the Streams API is used in this project:

```java
List<Room> rooms = List.of(
    new Room("101", "Deluxe", 500.0, false),
    new Room("102", "Standard", 200.0, true),
    new Room("201", "Deluxe", 500.0, true),
    new Room("202", "Suite", 1000.0, false)
);

HotelStreamService service = new HotelStreamService();

// Filter available rooms
List<Room> availableRooms = service.filterAvailableRooms(rooms);

// Get room numbers by type
List<String> deluxeRooms = service.getRoomNumbersByType(rooms, "Deluxe");

// Calculate total value
double totalValue = service.calculateTotalValue(rooms.toArray(new Room[0]));

// Group rooms by type
Map<String, List<Room>> groupedRooms = service.groupRoomsByType(rooms);
```

---

## 🧱 Project Structure

```
streams-hotel-api
├── model
│   └── Room.java
├── service
│   └── HotelStreamService.java
├── StreamsHotelApplication.java
└── test
    └── HotelStreamTest.java
```

---

## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/pedrohk/java-resources.git
cd streams-hotel-api
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Tests

```bash
mvn test
```

---

## 🧪 Testing

The project includes unit tests validating all stream operations:

* Filtering available rooms
* Mapping room numbers
* Summing values from arrays
* Grouping data into maps

---

## ⚖️ Pros and Cons of Streams API

| ✅ Pros                                    | ❌ Cons                             |
| ----------------------------------------- | ---------------------------------- |
| Declarative and readable code             | Slight overhead for small datasets |
| Encourages immutability                   | Harder debugging inside chains     |
| Easy parallelization (`parallelStream()`) | Learning curve for beginners       |
| Less boilerplate compared to loops        | Can reduce readability if overused |

---

## 🚀 Future Improvements

* Add `Optional` usage to safely handle missing data
* Introduce `parallelStream()` for performance benchmarking
* Expose REST endpoints to interact with room data
* Integrate with a database (JPA or NoSQL)
