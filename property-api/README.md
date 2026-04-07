---

# Property API - Hotel Accommodation System

A modern, high-performance REST API for managing hotel accommodations, built with **Java 21/25** and **Spring Boot 3.4.0**.

This project demonstrates a clean architecture for Property Management Systems (PMS), utilizing **Apache Cassandra** for scalable data storage and **MockitoBean** for modern testing patterns.

---

## 🚀 Features

* **CRUD Operations**: Create, Read, and Store hotel room data
* **Modern Java Patterns**: Uses Java **Records** for immutable data modeling
* **Cassandra Persistence**: Designed for high availability and horizontal scaling
* **Spring Boot 3.4+**: Uses the new `Bean Overriding API` for tests
* **Clean Architecture**: Clear separation between Models, Repositories, and Controllers

---

## 🛠️ Tech Stack

* **Language:** Java 21 (LTS) / Java 25
* **Framework:** Spring Boot 3.4.0
* **Database:** Apache Cassandra
* **Build Tool:** Maven
* **Testing:** JUnit 5, AssertJ, Mockito (`@MockitoBean`)
* **Infrastructure:** Docker & Docker Compose

---

## 📋 Prerequisites

* **JDK 21** or higher
* **Maven 3.9+**
* **Docker Desktop** (for running Cassandra)

---

## ⚙️ Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/pedrohk/java-resources.git
cd property-api
```

### 2. Spin up Cassandra using Docker Compose

```bash
docker-compose up -d
```

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

---

## 🧪 Testing

The project includes integration tests that mock the database layer to ensure the REST endpoints behave correctly without requiring a live Cassandra instance during the build phase.

Run tests with:

```bash
mvn test
```

---

## 🛰️ API Endpoints

### ➕ Create Accommodation

**POST** `/properties`

```json
{
  "hotelName": "Grand Plaza",
  "roomNumber": "101",
  "pricePerNight": 250.00,
  "checkIn": "2026-05-10",
  "checkOut": "2026-05-15"
}
```

---

### 📄 Read All

**GET** `/properties`

---

### 🔍 Read One

**GET** `/properties/{uuid}`

---

## 📁 Project Structure

```bash
src/main/java/com/pedrohk/
├── PropertyApiApplication.java        # Main application
├── controller/
│   └── AccommodationController.java  # REST API
├── model/
│   └── Accommodation.java            # Data Records
└── repository/
    └── AccommodationRepository.java # Cassandra interface
```
